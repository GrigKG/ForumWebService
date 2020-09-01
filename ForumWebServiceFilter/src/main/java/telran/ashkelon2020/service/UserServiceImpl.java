package telran.ashkelon2020.service;

import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.dao.UserRepositoryMongoDB;
import telran.ashkelon2020.forum.dto.exception.UserExistsException;
import telran.ashkelon2020.forum.dto.exception.UserNotFoundException;
import telran.ashkelon2020.forum.dto.user.RoleResponseDTO;
import telran.ashkelon2020.forum.dto.user.UserRegisterDTO;
import telran.ashkelon2020.forum.dto.user.UserAccountResponseDTO;
import telran.ashkelon2020.forum.dto.user.UserUpdateDTO;
import telran.ashkelon2020.model.user.UserAccount;

@Service
@ManagedResource
public class UserServiceImpl implements AccountService {

	@Autowired
	UserRepositoryMongoDB userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Value("${expdate.value}")
	long period;

	@ManagedAttribute
	public long getPeriod() {
		return period;
	}
	
	@ManagedAttribute
	public void setPeriod(long period) {
		this.period = period;
	}

	@Value("${default.value}")
	private String defaultUser;
	
	@ManagedAttribute
	public String getDefaultUser() {
		return defaultUser;
	}

	@ManagedAttribute
	public void setDefaultUser(String defaultUser) {
		this.defaultUser = defaultUser;
	}

	private UserAccountResponseDTO convertResponseDTO(UserAccount userAccount) {
		return modelMapper.map(userAccount, UserAccountResponseDTO.class);
	}

	private UserAccount getUserByID(String id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	@Override
	public UserAccountResponseDTO addUser(UserRegisterDTO userRegisterDTO) {
		if(userRepository.existsById(userRegisterDTO.getLogin())) {
			throw new UserExistsException(userRegisterDTO.getLogin());
		}
		String hashPassword= BCrypt.hashpw(userRegisterDTO.getPassword(), BCrypt.gensalt());
		UserAccount userAccount = modelMapper.map(userRegisterDTO, UserAccount.class);
		userAccount.setExpDate(LocalDateTime.now().plusDays(period));
		userAccount.addRole(defaultUser);
		userAccount.setPassword(hashPassword);
		userRepository.save(userAccount);
		return convertResponseDTO(userAccount);
	}

	@Override
	public UserAccountResponseDTO getUserByLogin(String login) {
		UserAccount userAccount = getUserByID(login);		
		return convertResponseDTO(userAccount);
	}

	@Override
	public UserAccountResponseDTO removeUser(String login) {
		UserAccount userAccount = getUserByID(login);
		userRepository.deleteById(login);
		return convertResponseDTO(userAccount);
	}

	@Override
	public UserAccountResponseDTO editUser(String login, UserUpdateDTO userUpdateDTO) {
		boolean res= false;
		UserAccount userAccount = getUserByID(login);
		String firstName = userUpdateDTO.getFirstName();
		String lastName = userUpdateDTO.getLastName();
		if (firstName!=null) {
			userAccount.setFirstName(firstName);
			res=true;
		}
		if (lastName!=null) {
			userAccount.setLastName(lastName);
			res=true;
		}
		if(res)userRepository.save(userAccount);
		return convertResponseDTO(userAccount);
	}
	
	@Override
	public RoleResponseDTO changeRole(String login, String role, boolean isAddRole) {
		UserAccount userAccount = getUserByID(login);
		boolean res = isAddRole?userAccount.addRole(role.toUpperCase()):userAccount.deleteRole(role.toUpperCase());
		if(res)userRepository.save(userAccount);
		return modelMapper.map(userAccount, RoleResponseDTO.class);
	}

	@Override
	public void changePassword(String login, String password) {
		UserAccount userAccount = getUserByID(login);
		String hashPassword= BCrypt.hashpw(password, BCrypt.gensalt());
		userAccount.setPassword(hashPassword);
		userAccount.setExpDate(LocalDateTime.now().plusDays(period));
		userRepository.save(userAccount);
	}
}

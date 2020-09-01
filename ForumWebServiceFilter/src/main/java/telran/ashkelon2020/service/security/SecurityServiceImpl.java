package telran.ashkelon2020.service.security;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.dao.UserRepositoryMongoDB;
import telran.ashkelon2020.forum.dto.exception.ForbiddenException;
import telran.ashkelon2020.forum.dto.exception.UnauthorizedExeption;
import telran.ashkelon2020.forum.dto.exception.UserNotFoundException;
import telran.ashkelon2020.forum.dto.user.UserLoginDTO;
import telran.ashkelon2020.model.user.UserAccount;
@Service
public class SecurityServiceImpl implements SecurityService {
	
	Map<String, String> users = new ConcurrentHashMap<>();

	@Autowired
	UserRepositoryMongoDB userRepository;
	
	@Override
	public String getLogin(String token) {
		UserLoginDTO userLoginDto = tokenDecode(token);
		UserAccount userAccount = userRepository.findById(userLoginDto.getLogin())
				.orElseThrow(() -> new UserNotFoundException(userLoginDto.getLogin()));
		if (!BCrypt.checkpw(userLoginDto.getPassword(), userAccount.getPassword())) {
			throw new UnauthorizedExeption();
		}
		return userAccount.getLogin();
	}

	@Override
	public boolean checkExpDate(String login) {
		UserAccount userAccount = userRepository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		if (userAccount.getExpDate().isBefore(LocalDateTime.now())) {
			throw new ForbiddenException();
		}
		return true;
	}

	private UserLoginDTO tokenDecode(String token) {
		String[] credentials = token.split(" ");
		String credential = new String(Base64.getDecoder().decode(credentials[1]));
		credentials = credential.split(":");
		return new UserLoginDTO(credentials[0], credentials[1]);

		}

	@Override
	public boolean checkHaveRole(String login, String role) {
		UserAccount userAccount = userRepository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		return userAccount.getRoles().contains(role.toUpperCase());

	}

	@Override
	public boolean isBanned(String login) {
		UserAccount userAccount = userRepository.findById(login)
				.orElseThrow(() -> new UserNotFoundException(login));
		return userAccount.getRoles().isEmpty();
	}

	@Override
	public String addUser(String sessionId, String login) {
		return users.put(sessionId, login);
	}

	@Override
	public String getUser(String sessionId) {
		return users.get(sessionId);
	}

	@Override
	public String removeUser(String sessionId) {
		return users.remove(sessionId);
	}
	
	
}

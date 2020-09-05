package telran.ashkelon2020.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.dao.UserRepositoryMongoDB;
import telran.ashkelon2020.model.user.UserAccount;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepositoryMongoDB userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount userAccount = userRepository.findById(username).orElseThrow(()->new UsernameNotFoundException(username));
		String[] roles = userAccount.getRoles()
				.stream()
				.map(r -> "ROLE_" + r.toUpperCase())
				.toArray(String[]::new);
			return new User(username, userAccount.getPassword(), AuthorityUtils.createAuthorityList(roles));
	}

}

package telran.ashkelon2020.service.security;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.dao.ForumRepositoryMongoDB;
import telran.ashkelon2020.dao.UserRepositoryMongoDB;
import telran.ashkelon2020.model.Post;
import telran.ashkelon2020.model.user.UserAccount;

@Service("customSecurity")
public class CustomWebSecuritySrevice {
	
	@Autowired
	ForumRepositoryMongoDB forumRepository;
	
	@Autowired
	UserRepositoryMongoDB userRepository;

	
	public boolean checkPostAuthority(String postID, String user) {
		Post post = forumRepository.findById(postID).orElse(null);
		return post==null?true:post.getAuthor().equals(user);
	}
	
	public boolean checkExpDate(String login) {
		UserAccount user = userRepository.findById(login).orElse(null);
		return user==null?true:user.getExpDate().isAfter(LocalDateTime.now());
	}	
}

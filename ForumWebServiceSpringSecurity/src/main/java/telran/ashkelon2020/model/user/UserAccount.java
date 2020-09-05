package telran.ashkelon2020.model.user;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "login" })
@ToString
@Document(collection = "forum_users")
public class UserAccount {

	@Id
	String login;
	String password;
	String firstName;
	String lastName;
	LocalDateTime expDate;
	Set<String> roles = new HashSet<>();

	public boolean addRole(String role) {
		return roles.add(role.toUpperCase());		
	}

	public boolean deleteRole(String role) {
		return roles.remove(role.toUpperCase());
	}

}

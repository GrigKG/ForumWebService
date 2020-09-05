package telran.ashkelon2020.forum.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@NonNull
public class UserRegisterDTO {
	String login;
	String password;
	String firstName;
	String lastName;
}

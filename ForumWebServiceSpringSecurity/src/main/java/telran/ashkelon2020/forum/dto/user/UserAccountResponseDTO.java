package telran.ashkelon2020.forum.dto.user;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserAccountResponseDTO {
	String login;
	String firstName;
	String lastName;
	LocalDate expDate;
	@Singular
	Set<String> roles;
}

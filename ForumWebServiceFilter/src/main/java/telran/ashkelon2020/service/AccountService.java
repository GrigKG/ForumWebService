package telran.ashkelon2020.service;

import telran.ashkelon2020.forum.dto.user.RoleResponseDTO;
import telran.ashkelon2020.forum.dto.user.UserRegisterDTO;
import telran.ashkelon2020.forum.dto.user.UserAccountResponseDTO;
import telran.ashkelon2020.forum.dto.user.UserUpdateDTO;

public interface AccountService {
	UserAccountResponseDTO addUser(UserRegisterDTO userRegisterDTO);
	UserAccountResponseDTO getUserByLogin(String login);
	UserAccountResponseDTO editUser(String login, UserUpdateDTO userUpdateDTO);
	UserAccountResponseDTO removeUser(String login);
	RoleResponseDTO changeRole (String login, String role, boolean isAddRole);
	void changePassword(String login, String password);
}

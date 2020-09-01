package telran.ashkelon2020.accounting.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.forum.dto.user.RoleResponseDTO;
import telran.ashkelon2020.forum.dto.user.UserAccountResponseDTO;
import telran.ashkelon2020.forum.dto.user.UserRegisterDTO;
import telran.ashkelon2020.forum.dto.user.UserUpdateDTO;
import telran.ashkelon2020.service.AccountService;
import telran.ashkelon2020.service.security.SecurityService;

@RestController
@RequestMapping("/account")
public class UserAccountController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	SecurityService securityService; 
	

	@PostMapping("/register")
	public UserAccountResponseDTO register(@RequestBody UserRegisterDTO userRegisterDto) {
		return accountService.addUser(userRegisterDto);
	}

	@PostMapping("/login")
	public UserAccountResponseDTO login(Principal user) {
		return accountService.getUserByLogin(user.getName());
	}

	@PutMapping("/user/{login}")
 UserAccountResponseDTO updateUser(@PathVariable String login, @RequestBody UserUpdateDTO userUpdateDto) {
		return accountService.editUser(login, userUpdateDto);
	}

	@DeleteMapping("/user/{login}")
	public UserAccountResponseDTO removeUser(@PathVariable String login) {
		return accountService.removeUser(login);
	}

	@PutMapping("user/{login}/role/{role}")
	public RoleResponseDTO addRole(@PathVariable String login, @PathVariable String role) {
		return accountService.changeRole(login, role, true);

	}

	@DeleteMapping("user/{login}/role/{role}")
	public RoleResponseDTO removeRole(@PathVariable String login, @PathVariable String role) {
		return accountService.changeRole(login, role, false);
	}

	@PutMapping("/password")
	public void changePassword(@RequestHeader("X-Password") String newPassword, Principal user) {
		accountService.changePassword(user.getName(), newPassword);
	}

	
}

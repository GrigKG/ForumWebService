package telran.ashkelon2020.service.security;

public interface SecurityService {

	String getLogin(String tokin);
	
	public boolean checkExpDate(String login);

	boolean checkHaveRole(String user, String string);
	
	boolean isBanned(String login);
	
	String addUser(String sessionId, String login);
	
	String getUser(String sessionId);
	
	String removeUser(String sessionId);
	
}

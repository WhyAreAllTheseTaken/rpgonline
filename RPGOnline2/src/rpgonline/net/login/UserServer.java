package rpgonline.net.login;

public interface UserServer {
	public String getLoginToken(String login, String password);
	public boolean isValidToken(String token);
	public String getConnectToken(String token);
	public boolean isValidConnectToken(String token);
	public void clearConnectToken(String token);
	public long getUserID(String login);
	public long getUserIDToken(String token);
	public long getUserIDToken2(String token);
	public long getUserIDUsername(String username);
	public String getUsername(long id);
	public boolean isUp();
}

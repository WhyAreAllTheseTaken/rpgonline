package rpgonline.net.login;

public class WebUserServer implements UserServer {
	private ServerProtocol sp;
	
	public WebUserServer(ServerProtocol sp) {
		super();
		this.sp = sp;
	}
	
	public WebUserServer(String url) {
		this(getProtocol(url));
	}
	
	private static ServerProtocol getProtocol(String url) {
		if (url.startsWith("https://")) {
			return new HTTPSProtocol(url);
		}
		if (url.startsWith("http://")) {
			return new HTTPProtocol(url);
		}
		throw new IllegalArgumentException("Unknown protcol for url: " + url);
	}

	@Override
	public String getLoginToken(String login, String password) {
		return sp.request("mode=tokenget", "login=" + login, "password=" + password);
	}

	@Override
	public boolean isValidToken(String token) {
		return Boolean.parseBoolean(sp.request("mode=tokenvalid", "token=" + token));
	}

	@Override
	public String getConnectToken(String token) {
		return sp.request("mode=token2get", "token=" + token);
	}

	@Override
	public boolean isValidConnectToken(String token) {
		return Boolean.parseBoolean(sp.request("mode=token2valid", "token=" + token));
	}

	@Override
	public void clearConnectToken(String token) {
		sp.request("mode=token2clear", "token=" + token);
	}

	@Override
	public long getUserID(String login) {
		return Long.parseLong(sp.request("mode=idlogin", "login=" + login));
	}

	@Override
	public long getUserIDToken(String token) {
		return Long.parseLong(sp.request("mode=idtoken", "token=" + token));
	}

	@Override
	public long getUserIDToken2(String token) {
		return Long.parseLong(sp.request("mode=idtoken2", "token=" + token));
	}

	@Override
	public long getUserIDUsername(String username) {
		return Long.parseLong(sp.request("mode=idusername", "login=" + username));
	}

	@Override
	public String getUsername(long id) {
		return sp.request("mode=usernameget", "id=" + id);
	}

	@Override
	public boolean isUp() {
		return sp.isUp();
	}

}

package rpgonline.net.login;

/**
 * A user server that automatically picks the best protocol for a URL.
 * @author Tomas
 */
public class WebUserServer implements UserServer {
	/**
	 * The connection protocol.
	 */
	private final ServerProtocol sp;
	
	/**
	 * Constructs a new WebUserServer from a given protocol.
	 * @param sp The connection protocol.
	 */
	public WebUserServer(ServerProtocol sp) {
		super();
		this.sp = sp;
	}
	
	/**
	 * Constructs a new WebUserServer from a URL.
	 * @param url The URL to connect to.
	 */
	public WebUserServer(String url) {
		this(getProtocol(url));
	}
	
	/**
	 * Picks the protocol for a given URL.
	 * @param url The URL to connect to.
	 * @return The protocol to use.
	 */
	private static ServerProtocol getProtocol(String url) {
		if (url.startsWith("https://")) {
			return new HTTPSProtocol(url);
		}
		if (url.startsWith("http://")) {
			return new HTTPProtocol(url);
		}
		throw new IllegalArgumentException("Unknown protcol for url: " + url);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLoginToken(String login, String password) {
		return sp.request("mode=tokenget", "login=" + login, "password=" + password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidToken(String token) {
		return Boolean.parseBoolean(sp.request("mode=tokenvalid", "token=" + token));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getConnectToken(String token) {
		return sp.request("mode=token2get", "token=" + token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidConnectToken(String token) {
		return Boolean.parseBoolean(sp.request("mode=token2valid", "token=" + token));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearConnectToken(String token) {
		sp.request("mode=token2clear", "token=" + token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUserID(String login) {
		return Long.parseLong(sp.request("mode=idlogin", "login=" + login));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUserIDToken(String token) {
		return Long.parseLong(sp.request("mode=idtoken", "token=" + token));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUserIDToken2(String token) {
		return Long.parseLong(sp.request("mode=idtoken2", "token=" + token));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUserIDUsername(String username) {
		return Long.parseLong(sp.request("mode=idusername", "login=" + username));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUsername(long id) {
		return sp.request("mode=usernameget", "id=" + id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUp() {
		return sp.isUp();
	}

}

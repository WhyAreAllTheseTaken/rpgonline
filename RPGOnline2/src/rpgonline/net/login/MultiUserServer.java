package rpgonline.net.login;

import java.util.Arrays;
import java.util.List;

public class MultiUserServer implements UserServer {
	/**
	 * A server used if not server is available.
	 */
	private static final DownServer DOWN_SERVER = new DownServer();
	/**
	 * A list of possible servers.
	 */
	private final List<UserServer> servers;

	/**
	 * Constructs a {@code MultiLogonServer} from a list of servers.
	 * 
	 * @param servers A list of servers.
	 */
	public MultiUserServer(List<UserServer> servers) {
		this.servers = servers;
	}

	/**
	 * Constructs a {@code MultiLogonServer} from an array.
	 * 
	 * @param servers An array.
	 */
	public MultiUserServer(UserServer[] servers) {
		this(Arrays.asList(servers));
	}

	/**
	 * Gets the best server to connect to.
	 * 
	 * @return A server.
	 */
	public synchronized UserServer getBestServer() {
		for (UserServer server : servers) {
			if (server.isUp()) {
				return server;
			}
		}
		return DOWN_SERVER;
	}

	/**
	 * A server used if not other server can be found.
	 * 
	 * @author Tomas
	 */
	private static class DownServer implements UserServer {

		@Override
		public String getLoginToken(String login, String password) {
			return LocalDiskUserServer.INVALID_TOKEN;
		}

		@Override
		public boolean isValidToken(String token) {
			return false;
		}

		@Override
		public String getConnectToken(String token) {
			return LocalDiskUserServer.INVALID_TOKEN;
		}

		@Override
		public boolean isValidConnectToken(String token) {
			return false;
		}

		@Override
		public void clearConnectToken(String token) {
			
		}

		@Override
		public long getUserID(String login) {
			return -1;
		}

		@Override
		public long getUserIDToken(String token) {
			return -1;
		}

		@Override
		public long getUserIDToken2(String token) {
			return -1;
		}

		@Override
		public long getUserIDUsername(String username) {
			return -1;
		}

		@Override
		public String getUsername(long id) {
			return Long.toHexString(id);
		}

		@Override
		public boolean isUp() {
			return false;
		}

	}

	@Override
	public String getLoginToken(String login, String password) {
		return getBestServer().getLoginToken(login, password);
	}

	@Override
	public boolean isValidToken(String token) {
		return getBestServer().isValidToken(token);
	}

	@Override
	public String getConnectToken(String token) {
		return getBestServer().getConnectToken(token);
	}

	@Override
	public boolean isValidConnectToken(String token) {
		return getBestServer().isValidConnectToken(token);
	}

	@Override
	public void clearConnectToken(String token) {
		getBestServer().clearConnectToken(token);
	}

	@Override
	public long getUserID(String login) {
		return getBestServer().getUserID(login);
	}

	@Override
	public long getUserIDToken(String token) {
		return getBestServer().getUserIDToken(token);
	}

	@Override
	public long getUserIDToken2(String token) {
		return getBestServer().getUserIDToken2(token);
	}

	@Override
	public long getUserIDUsername(String username) {
		return getBestServer().getUserIDUsername(username);
	}

	@Override
	public String getUsername(long id) {
		return getBestServer().getUsername(id);
	}

	@Override
	public boolean isUp() {
		return getBestServer().isUp();
	}

}

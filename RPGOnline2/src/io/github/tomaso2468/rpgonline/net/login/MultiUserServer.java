/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.net.login;

import java.util.Arrays;
import java.util.List;

/**
 * A user server that combines multiple servers in a priority list.
 * @author Tomas
 */
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
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getLoginToken(String login, String password) {
			return UserServer.INVALID_TOKEN;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isValidToken(String token) {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getConnectToken(String token) {
			return UserServer.INVALID_TOKEN;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isValidConnectToken(String token) {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void clearConnectToken(String token) {
			
		}

		@Override
		/**
		 * {@inheritDoc}
		 */
		public long getUserID(String login) {
			return -1;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long getUserIDToken(String token) {
			return -1;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long getUserIDToken2(String token) {
			return -1;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public long getUserIDUsername(String username) {
			return -1;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getUsername(long id) {
			return Long.toHexString(id);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isUp() {
			return false;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLoginToken(String login, String password) {
		return getBestServer().getLoginToken(login, password);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidToken(String token) {
		return getBestServer().isValidToken(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getConnectToken(String token) {
		return getBestServer().getConnectToken(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidConnectToken(String token) {
		return getBestServer().isValidConnectToken(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearConnectToken(String token) {
		getBestServer().clearConnectToken(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUserID(String login) {
		return getBestServer().getUserID(login);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUserIDToken(String token) {
		return getBestServer().getUserIDToken(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUserIDToken2(String token) {
		return getBestServer().getUserIDToken2(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUserIDUsername(String username) {
		return getBestServer().getUserIDUsername(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUsername(long id) {
		return getBestServer().getUsername(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isUp() {
		return getBestServer().isUp();
	}

}

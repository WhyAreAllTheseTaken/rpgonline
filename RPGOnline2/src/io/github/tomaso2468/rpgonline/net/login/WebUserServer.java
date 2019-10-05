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

/**
 * A user server that automatically picks the best protocol for a URL.
 * @author Tomaso2468
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
		if (token.equals(UserServer.INVALID_TOKEN)) {
			return false;
		}
		return Boolean.parseBoolean(sp.request("mode=tokenvalid", "token=" + token));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getConnectToken(String token) {
		if (token.equals(UserServer.INVALID_TOKEN)) {
			return UserServer.INVALID_TOKEN;
		}
		return sp.request("mode=token2get", "token=" + token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValidConnectToken(String token) {
		if (token.equals(UserServer.INVALID_TOKEN)) {
			return false;
		}
		return Boolean.parseBoolean(sp.request("mode=token2valid", "token=" + token));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearConnectToken(String token) {
		if (token.equals(UserServer.INVALID_TOKEN)) {
			return;
		}
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
		if (token.equals(UserServer.INVALID_TOKEN)) {
			return -1;
		}
		return Long.parseLong(sp.request("mode=idtoken", "token=" + token));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUserIDToken2(String token) {
		if (token.equals(UserServer.INVALID_TOKEN)) {
			return -1;
		}
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

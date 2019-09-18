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
 * <p>An interface providing methods for accessing login servers.</p>
 * <p><b>Login Tokens should not be shared with other servers but a connect token may be.</b></p>
 * @author Tomas
 */
public interface UserServer {
	/**
	 * Tokens used for invalid requests.
	 */
	public static final String INVALID_TOKEN = "ffffffffffffffffffffffffffffffff";
	/**
	 * Gets a token for logging in.
	 * @param login The username/email of the user.
	 * @param password The password of the user.
	 * @return A valid token or {@value INVALID_TOKEN} if the login was invalid.
	 */
	public String getLoginToken(String login, String password);
	/**
	 * Determines if a token is valid.
	 * @param token The token to check.
	 * @return {@code true} if the token is valid, {@code false} otherwise.
	 */
	public boolean isValidToken(String token);
	/**
	 * Gets a single-use token for connecting to a server.
	 * @param token The normal login token.
	 * @return A valid connect token or {@value INVALID_TOKEN} if the login token was invalid.
	 */
	public String getConnectToken(String token);
	/**
	 * Determines if a connect token is valid.
	 * @param token The token to check.
	 * @return {@code true} if the token is valid, {@code false} otherwise.
	 */
	public boolean isValidConnectToken(String token);
	/**
	 * Removes and invalidates a connect token.
	 * @param token The token to invalidate.
	 */
	public void clearConnectToken(String token);
	/**
	 * Gets the user ID from a login.
	 * @param login The user login.
	 * @return A 64bit integer. {@code -1} is reserved as an invalid ID.
	 */
	public long getUserID(String login);
	/**
	 * Gets the user ID from a token.
	 * @param token The login token.
	 * @return A 64bit integer. {@code -1} is reserved as an invalid ID.
	 */
	public long getUserIDToken(String token);
	/**
	 * Gets the user ID from a connect token.
	 * @param token The login token.
	 * @return A 64bit integer. {@code -1} is reserved as an invalid ID.
	 */
	public long getUserIDToken2(String token);
	/**
	 * Gets the user ID from a username.
	 * @param username The username.
	 * @return A 64bit integer. {@code -1} is reserved as an invalid ID.
	 */
	public long getUserIDUsername(String username);
	/**
	 * Gets a user's name from their user ID.
	 * @param id A user ID.
	 * @return The user's username.
	 */
	public String getUsername(long id);
	/**
	 * Determines if the server can be connected to.
	 * @return {@code true} if the server is available, {@code false} otherwise.
	 */
	public boolean isUp();
}

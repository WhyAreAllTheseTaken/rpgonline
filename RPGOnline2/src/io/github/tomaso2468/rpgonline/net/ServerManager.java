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
package io.github.tomaso2468.rpgonline.net;

import io.github.tomaso2468.rpgonline.net.login.UserServer;

/**
 * A class for managing currently running server and client instances.
 * @author Tomas
 *
 */
public final class ServerManager {
	/**
	 Prevent instantiation
	 */
	private ServerManager() {
		
	}
	/**
	 * The currently running client.
	 */
	private static Client client;
	/**
	 * The currently running server.
	 */
	private static Server server;
	/**
	 * The connection to the login server.
	 */
	private static UserServer user_server;
	/**
	 * The time that the last server update took in nanoseconds.
	 */
	public static long server_time;
	/**
	 * The time that the last client update took in nanoseconds.
	 */
	public static long client_time;
	/**
	 * The maximum time of a server tick in nanoseconds.
	 */
	public static long server_max_time;
	/**
	 * The maximum time of a server tick in nanoseconds.
	 */
	public static long client_max_time;
	
	/**
	 * Gets the currently used client instance.
	 * @return A client instance or null if no instance exists.
	 */
	public static Client getClient() {
		return client;
	}
	
	/**
	 * Sets the currently used client instance.
	 * @return A client instance or null if to remove the current instance.
	 */
	public static void setClient(Client client) {
		ServerManager.client = client;
	}

	/**
	 * Gets the currently used server instance.
	 * @return A server instance or null if no instance exists.
	 */
	public static Server getServer() {
		return server;
	}

	/**
	 * Sets the currently used server instance.
	 * @return A server instance or null if to remove the current instance.
	 */
	public static void setServer(Server server) {
		ServerManager.server = server;
	}

	/**
	 * Gets the login server for this application.
	 * @return A non-null UserServer instance.
	 */
	public static UserServer getUserServer() {
		return user_server;
	}

	/**
	 * Sets the login server to use.
	 * @param user_server A non-null UserServer instance.
	 */
	public static void setUserServer(UserServer user_server) {
		ServerManager.user_server = user_server;
	}
}

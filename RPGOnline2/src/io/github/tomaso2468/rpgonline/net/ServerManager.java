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

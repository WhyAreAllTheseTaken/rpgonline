package rpgonline.net;

public final class ServerManager {
	private static Client client;
	private static Server server;
	public static long server_time;
	public static long client_time;
	
	public static Client getClient() {
		return client;
	}
	
	public static void setClient(Client client) {
		ServerManager.client = client;
	}

	public static Server getServer() {
		return server;
	}

	public static void setServer(Server server) {
		ServerManager.server = server;
	}
}

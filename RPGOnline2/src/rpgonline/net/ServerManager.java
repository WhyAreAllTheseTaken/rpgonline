package rpgonline.net;

public class ServerManager {
	private static Client client;
	private static Server server;
	
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

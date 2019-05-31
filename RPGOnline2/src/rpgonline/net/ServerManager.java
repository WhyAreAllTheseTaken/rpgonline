package rpgonline.net;

public class ServerManager {
	private static Client client;
	
	public static Client getClient() {
		return client;
	}
	
	public static void setClient(Client client) {
		ServerManager.client = client;
	}
}

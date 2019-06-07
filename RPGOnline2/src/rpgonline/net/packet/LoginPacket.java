package rpgonline.net.packet;

import rpgonline.net.ServerManager;

public class LoginPacket implements NetPacket {
	private static final long serialVersionUID = 246722656302089465L;
	private final String token;
	private final long id;
	
	public LoginPacket(String token, long id) {
		super();
		this.token = token;
		this.id = id;
	}
	
	public boolean isValid() {
		return ServerManager.getUserServer().isValidConnectToken(token) && ServerManager.getUserServer().getUserIDToken2(token) == id;
	}
	
	public long getID() {
		return id;
	}
	
}

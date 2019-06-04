package rpgonline.net.packet;

public class ServerInfoPacket implements NetPacket {
	private static final long serialVersionUID = -5803480872418619141L;
	public final String type;
	
	public ServerInfoPacket(String type) {
		super();
		this.type = type;
	}
	
	
}

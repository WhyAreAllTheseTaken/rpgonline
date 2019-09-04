package rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rpgonline.net.PacketType;

/**
 * Packet used for server info communication.
 * @author Tomas
 *
 */
public class ServerInfoPacket implements NetPacket {
	public static final byte PACKET_ID = (byte) 0xFF - 8;
	
	private static final long serialVersionUID = -5803480872418619141L;
	public final String type;
	
	public ServerInfoPacket(String type) {
		super();
		this.type = type;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeUTF(type);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new ServerInfoPacket(in.readUTF());
		}
	}
	
	
}

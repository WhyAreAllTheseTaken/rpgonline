package rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rpgonline.net.PacketType;

/**
 * Packet used for sending chat data./
 * @author Tomas
 *
 */
public class TextPacket implements NetPacket {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3045769783056355431L;

	public static final byte PACKET_ID = (byte) 0xFF - 12;
	
	private final String text;

	public TextPacket(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeUTF(text);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new TextPacket(in.readUTF());
		}
	}
}

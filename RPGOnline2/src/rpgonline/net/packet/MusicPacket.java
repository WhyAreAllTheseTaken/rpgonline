package rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rpgonline.net.PacketType;

/**
 * Packet used for music data.
 * @author Tomas
 *
 */
public class MusicPacket implements NetPacket {
	public static final byte PACKET_ID = (byte) 0xFF - 7;
	
	private static final long serialVersionUID = 2441590535347918982L;
	private final String music;

	public MusicPacket(String music) {
		super();
		this.music = music;
	}

	public String getMusic() {
		return music;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeUTF(music);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new MusicPacket(in.readUTF());
		}
	}
}

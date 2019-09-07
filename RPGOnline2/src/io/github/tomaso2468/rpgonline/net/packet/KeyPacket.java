package io.github.tomaso2468.rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.PublicKey;

import io.github.tomaso2468.rpgonline.net.PacketType;

/**
 * Packet used for the sending of public encryption keys.
 * @author Tomas
 *
 */
public class KeyPacket implements NetPacket {
	public static final byte PACKET_ID = (byte) 0xFF - 4;
	
	private static final long serialVersionUID = -3131656449111362838L;
	public final byte[] key;

	public KeyPacket(byte[] key) {
		this.key = key;
	}

	public KeyPacket(PublicKey key) {
		this(key.getEncoded());
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeInt(key.length);
		out.write(key);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			byte[] data = new byte[in.readInt()];
			in.readFully(data);
			return new KeyPacket(data);
		}
	}
}

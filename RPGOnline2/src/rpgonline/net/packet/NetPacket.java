package rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import rpgonline.net.PacketType;

public interface NetPacket extends Serializable {
	public static final byte PACKET_OBJECT = (byte) 0xFF;
	public default void write(DataOutputStream out) throws IOException {
		out.write(PACKET_OBJECT);
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(this);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return (NetPacket) new ObjectInputStream(in).readObject();
		}
	}
}

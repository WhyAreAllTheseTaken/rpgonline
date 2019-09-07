package io.github.tomaso2468.rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import io.github.tomaso2468.rpgonline.net.PacketType;

/**
 * A class used for representing and defining packet types.
 * @author Tomas
 *
 */
public interface NetPacket extends Serializable {
	/**
	 * The ID for serialised packets. This is the default type for all packets.
	 */
	public static final byte PACKET_OBJECT = (byte) 0xFF;
	/**
	 * Writes the packet including ID to a file.
	 * @param out The output stream to write to.
	 * @throws IOException If an error occurs writing data.
	 */
	public default void write(DataOutputStream out) throws IOException {
		out.write(PACKET_OBJECT);
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(this);
	}
	
	/**
	 * A class like this should be available for all packet classes.
	 * @author Tomas
	 *
	 */
	public static class Type implements PacketType {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return (NetPacket) new ObjectInputStream(in).readObject();
		}
	}
}

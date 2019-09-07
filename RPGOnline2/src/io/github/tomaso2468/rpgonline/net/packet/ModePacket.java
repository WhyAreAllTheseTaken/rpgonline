package io.github.tomaso2468.rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.github.tomaso2468.rpgonline.net.PacketType;

/**
 * A packet indicating the mode of the connection.
 * @author Tomas
 *
 */
public class ModePacket implements NetPacket {
	/**
	 * The ID of this packet.
	 */
	public static final byte PACKET_ID = (byte) 0xFF - 15;
	/**
	 * The ID for serialisation.
	 */
	private static final long serialVersionUID = -6976795378405555984L;

	/**
	 * Mode byte indicating a request for server information.
	 */
	public static final byte MODE_INFO = 1;
	
	/**
	 * Mode byte indicating a request for a server connection.
	 */
	public static final byte MODE_CONNECT = 2;
	
	/**
	 * The mode indicated by this packet.
	 */
	public final byte mode;

	/**
	 * Constructs a new ModePacket.
	 * @param mode The mode of the packet.
	 */
	public ModePacket(byte mode) {
		super();
		this.mode = mode;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeByte(mode);
	}
	
	/**
	 * Packet type declaration.
	 * @author Tomas
	 *
	 */
	public static class Type implements PacketType {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new ModePacket(in.readByte());
		}
	}
}

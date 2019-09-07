package io.github.tomaso2468.rpgonline.net;

import java.io.DataInputStream;
import java.io.IOException;

import io.github.tomaso2468.rpgonline.net.packet.NetPacket;

/**
 * An interface defining a packet type for a packet.
 * @author Tomas
 *
 */
public interface PacketType {
	/**
	 * Reads a packet from a network packet with the ID field already being read.
	 * @param in The input stream to read data from.
	 * @return A net packet object.
	 * @throws IOException 
	 * @throws ClassNotFoundException
	 */
	public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException;
}

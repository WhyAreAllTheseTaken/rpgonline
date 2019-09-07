package io.github.tomaso2468.rpgonline.net;

import java.io.Closeable;
import java.io.IOException;

import io.github.tomaso2468.rpgonline.net.packet.NetPacket;

/**
 * <p>
 * An interface for a 2-way network connections.
 * </p>
 * <p>
 * Packets are not guaranteed to be read in the same order that they are sent
 * and this may vary between implementation. Additionally, not every connection
 * type will support encryption. When {@code encrypt()} is called the method may
 * throw a {@code UnsupportedOperationException} or it may return and do
 * nothing.
 * </p>
 * <p>
 * The word packet is used here to many any small piece of data. However, some
 * data types may be larger than a standard packet. These will be sent as
 * multiple packets.
 * </p>
 * 
 * @author Tomas
 */
public interface Connection extends Closeable {
	/**
	 * Send the packet over the network.
	 * 
	 * @param p The packet to send.
	 * @throws IOException If an error occurs sending data.
	 */
	public void send(NetPacket p) throws IOException;

	/**
	 * Determines if a new packet is available to read.
	 * 
	 * @return {@code true} if a packet is available, {@code false} otherwise.
	 * @throws IOException If an error occurs reading data.
	 * 
	 * @see #getNext()
	 */
	public boolean isAvaliable() throws IOException;

	/**
	 * Gets the next available packet from the connection. The behaviour of this method is undefined if no packets are available.
	 * @return The next available packet.
	 * @throws IOException If an error occurs reading data.
	 */
	public NetPacket getNext() throws IOException;

	/**
	 * Encrypts the connection. This method will wait until the encryption is finished.
	 * @throws IOException If an error occurs sending and receiving data to perform an encryption.
	 */
	public void encrypt() throws IOException;
}

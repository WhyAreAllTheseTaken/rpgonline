/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
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
 * @author Tomaso2468
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

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.net.packet.NetPacket;

/**
 * A class for generating local connections for use without the need for an internet connection or IO.
 * @author Tomaso2468
 */
public class LocalConnectionGenerator {
	/**
	 * {@code true} if the connection is closed, {@code false} otherwise.
	 */
	private boolean closed;
	/**
	 * The list of packets sent to the client.
	 */
	private List<NetPacket> client = Collections.synchronizedList(new ArrayList<NetPacket>());
	/**
	 * The list of packets sent to the server.
	 */
	private List<NetPacket> server = Collections.synchronizedList(new ArrayList<NetPacket>());

	/**
	 * Gets a connection from the server to the client.
	 * @return A connection object.
	 */
	public Connection getServer() {
		return new Connection() {
			@Override
			public void close() throws IOException {
				if (closed) {
					throw new IOException("Connection is already closed.");
				}
				closed = true;
			}

			@Override
			public void send(NetPacket p) throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				client.add(p);
			}

			@Override
			public boolean isAvaliable() throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				return server.size() > 0;
			}

			@Override
			public NetPacket getNext() throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				return server.get(0);
			}

			@Override
			public void encrypt() throws IOException {
				Log.warn("Server attempted encryption on local connection: Ignoring");
			}
		};
	}
	
	/**
	 * Gets a connection from the client to the server.
	 * @return A connection object.
	 */
	public Connection getClient() {
		return new Connection() {
			@Override
			public void close() throws IOException {
				if (closed) {
					throw new IOException("Connection is already closed.");
				}
				closed = true;
			}

			@Override
			public void send(NetPacket p) throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				server.add(p);
			}

			@Override
			public boolean isAvaliable() throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				return client.size() > 0;
			}

			@Override
			public NetPacket getNext() throws IOException {
				if (closed) {
					throw new IOException("Connection is closed.");
				}
				return client.get(0);
			}

			@Override
			public void encrypt() throws IOException {
				Log.warn("Server attempted encryption on local connection: Ignoring");
			}
		};
	}
}

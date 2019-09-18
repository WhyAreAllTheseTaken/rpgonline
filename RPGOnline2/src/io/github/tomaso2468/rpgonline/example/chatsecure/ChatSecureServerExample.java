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
package io.github.tomaso2468.rpgonline.example.chatsecure;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.net.BasicPacketConnection;
import io.github.tomaso2468.rpgonline.net.Connection;
import io.github.tomaso2468.rpgonline.net.packet.TextPacket;

/**
 * An example of a secure chat server.
 * 
 * @author Tomas
 */
public class ChatSecureServerExample {
	/**
	 * The main method of the program.
	 * 
	 * @param args command line arguments (unused).
	 * @throws IOException If an error occurs communicating with the clients.
	 */
	public static void main(String[] args) throws IOException {
		// A list of connections to clients.
		List<Connection> connections = Collections.synchronizedList(new ArrayList<>());

		/*
		 * The TCP port to use for the server. This can be any valid port in the range
		 * 1024-49151 but it is better to use a port that isn't needed by a commonly
		 * used piece of software and/or allow the port to be set in configuration
		 * files.
		 */
		int port = 20004;

		// Open a new server socket.
		ServerSocket ss = new ServerSocket();

		/*
		 * Set server performance options. This doesn't do much in most socket
		 * implementations but it's included here for the purpose of showing what's
		 * needed for this server engine. These setting are set for low latency and high
		 * bandwidth.
		 */
		ss.setPerformancePreferences(0, 2, 1);

		// Bind the server socket to the specified port.
		ss.bind(new InetSocketAddress(port));

		while (!ss.isClosed()) {
			// Wait for new connection.
			Socket s = ss.accept();
			
			// Start thread for client.
			new Thread("Client") {
				public void run() {
					// Open a connection
					Connection c = new BasicPacketConnection(s);
					
					// Encrypt the connection
					try {
						c.encrypt();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					// Add the connection to the list of clients.
					connections.add(c);
					
					Log.debug("Starting Chat");
					
					while (!s.isClosed()) {
						Log.debug("Looping");
						try {
							// Loop through all available packets.
							while (c.isAvaliable()) {
								TextPacket p = (TextPacket) c.getNext();

								// Print packet data to log.
								System.out.println(p.getText());

								// Send packet to all clients.
								for (int i = 0; i < connections.size(); i++) {
									Connection c2 = connections.get(i);
									c2.send(p);
								}
							}
							try {
								// This should be lowered for gameplay to at most the time of one frame going below that may help with latency and stuttering.
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					Log.debug("Connection closed.");
					connections.remove(c);
					try {
						s.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}

		ss.close();
	}
}

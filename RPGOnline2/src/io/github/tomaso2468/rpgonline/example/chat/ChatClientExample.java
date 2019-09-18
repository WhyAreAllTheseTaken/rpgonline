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
package io.github.tomaso2468.rpgonline.example.chat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import io.github.tomaso2468.rpgonline.net.BasicPacketConnection;
import io.github.tomaso2468.rpgonline.net.Connection;
import io.github.tomaso2468.rpgonline.net.packet.TextPacket;

/**
 * An example of a client for a chat server.
 * @author Tomas
 */
public class ChatClientExample {
	
	/**
	 * The main method of the program.
	 * @param args command line arguments (unused).
	 * @throws UnknownHostException If an error occurs resolving the host name.
	 * @throws IOException If an error occurs communicating with the server..
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		// Construct a new socket on the server port (20004).
		Socket s = new Socket("localhost", 20004);
		
		//Open a new connection interface.
		Connection c = new BasicPacketConnection(s);
		
		//Create a new thread for managing received packets.
		new Thread() {
			public void run() {
				while (!s.isClosed()) {
					try {
						// Loop through all packets.
						while (c.isAvaliable()) {
							//Output packet text.
							System.out.println(((TextPacket) c.getNext()).getText());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					// Wait some time to prevent using all CPU resources.
					Thread.yield();
				}
				try {
					c.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		//XXX This could be fixed but it isn't nessecary.
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		//Go into an infinite loop.
		while (true) {
			//Wait for input.
			while (!sc.hasNextLine()) {
				Thread.yield();
			}
			
			//Send input to server.
			c.send(new TextPacket(sc.nextLine()));
		}
	}
}

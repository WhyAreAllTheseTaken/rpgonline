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

package io.github.tomaso2468.rpgonline.example.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.tomaso2468.rpgonline.net.BasicPacketConnection;
import io.github.tomaso2468.rpgonline.net.Connection;
import io.github.tomaso2468.rpgonline.net.packet.TextPacket;

/**
 * An example of a chat server.
 * 
 * @author Tomas
 */
public class ChatServerExample {
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
					// Open a connection and add it to the list of clients.
					Connection c = new BasicPacketConnection(s);
					connections.add(c);
					
					
					while (!s.isClosed()) {
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
					try {
						s.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					connections.remove(c);
				}
			}.start();
		}

		ss.close();
	}
}
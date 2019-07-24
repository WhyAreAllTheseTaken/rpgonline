package rpgonline.example.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rpgonline.net.BasicPacketConnection;
import rpgonline.net.Connection;
import rpgonline.net.packet.TextPacket;

public class ChatServerExample {
	public static void main(String[] args) throws IOException {
		List<Connection> connections = Collections.synchronizedList(new ArrayList<>());
		
		int port = 20004;
		
		ServerSocket ss = new ServerSocket();
		
		ss.setPerformancePreferences(0, 2, 1);
		
		ss.bind(new InetSocketAddress(port));
		
		while (!ss.isClosed()) {
			Socket s = ss.accept();
			new Thread("Client") {
				public void run() {
					Connection c = new BasicPacketConnection(s);
					connections.add(c);
					while (!s.isClosed()) {
						try {
							while (c.isAvaliable()) {
								TextPacket p = (TextPacket) c.getNext();
								
								System.out.println(p.getText());
								
								for (int i = 0; i < connections.size(); i++) {
									Connection c2 = connections.get(i);
									c2.send(p);
								}
							}
							try {
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
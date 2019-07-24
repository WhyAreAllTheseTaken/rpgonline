package rpgonline.example.chat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import rpgonline.net.BasicPacketConnection;
import rpgonline.net.Connection;
import rpgonline.net.packet.TextPacket;

public class ChatClientExample {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket s = new Socket("localhost", 20004);
		
		Connection c = new BasicPacketConnection(s);
		new Thread() {
			public void run() {
				while (!s.isClosed()) {
					try {
						while (c.isAvaliable()) {
							System.out.println(((TextPacket) c.getNext()).getText());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					Thread.yield();
				}
				try {
					c.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		while (true) {
			while (!sc.hasNextLine()) {
				Thread.yield();
			}
			c.send(new TextPacket(sc.nextLine()));
		}
	}
}

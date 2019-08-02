package rpgonline.net;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.util.Log;

import rpgonline.net.packet.AmbientPacket;
import rpgonline.net.packet.ChunkRequestPacket;
import rpgonline.net.packet.EntityRemovePacket;
import rpgonline.net.packet.KeyPacket;
import rpgonline.net.packet.LightsPacket;
import rpgonline.net.packet.LoginPacket;
import rpgonline.net.packet.MovePacket;
import rpgonline.net.packet.MusicPacket;
import rpgonline.net.packet.NetPacket;
import rpgonline.net.packet.ServerInfoPacket;
import rpgonline.net.packet.SoundPacket;
import rpgonline.net.packet.StopAmbientPacket;
import rpgonline.net.packet.TextPacket;
import rpgonline.net.packet.WindPacket;

public class BasicPacketConnection implements Connection {

	private List<NetPacket> toSend = Collections.synchronizedList(new ArrayList<NetPacket>());
	private List<NetPacket> recieved = Collections.synchronizedList(new ArrayList<NetPacket>());

	private boolean stopped = false;
	
	public BasicPacketConnection(Socket s) {
		this(s, basicTypes());
	}
	
	public static PacketType[] basicTypes() {
		PacketType[] types = new PacketType[0x100];
		
		types[NetPacket.PACKET_OBJECT & 0xFF] = new NetPacket.Type();
		types[AmbientPacket.PACKET_AMBIENT & 0xFF] = new AmbientPacket.Type();
		types[ChunkRequestPacket.PACKET_ID & 0xFF] = new ChunkRequestPacket.Type();
		types[EntityRemovePacket.PACKET_ID & 0xFF] = new EntityRemovePacket.Type();
		types[KeyPacket.PACKET_ID & 0xFF] = new KeyPacket.Type();
		types[LoginPacket.PACKET_ID & 0xFF] = new LoginPacket.Type();
		types[MovePacket.PACKET_ID & 0xFF] = new MovePacket.Type();
		types[MusicPacket.PACKET_ID & 0xFF] = new MusicPacket.Type();
		types[ServerInfoPacket.PACKET_ID & 0xFF] = new ServerInfoPacket.Type();
		types[SoundPacket.PACKET_ID & 0xFF] = new SoundPacket.Type();
		types[StopAmbientPacket.PACKET_ID & 0xFF] = new StopAmbientPacket.Type();
		types[WindPacket.PACKET_ID & 0xFF] = new WindPacket.Type();
		types[TextPacket.PACKET_ID & 0xFF] = new TextPacket.Type();
		types[LightsPacket.PACKET_ID & 0xFF] = new LightsPacket.Type();
		
		return types;
	}
	
	public BasicPacketConnection(Socket s, PacketType[] types) {
		if (types.length != 0x100) {
			throw new IllegalArgumentException("Type array must be of length 256");
		}
		new Thread(toString()) {
			public void run() {
				try {
					DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
					DataInputStream in = new DataInputStream(s.getInputStream());

					while (!stopped) {
						while (toSend.size() > 0) {
							try {
								toSend.get(0).write(out);
								toSend.remove(0);
							} catch (IOException e) {
								Log.error("Error writing packet.", e);
							}
						}
						out.flush();

						while (in.available() > 0) {
							try {
								byte id = (byte) in.read();
								PacketType type = types[id & 0xFF];
								if (type == null) {
									throw new IllegalArgumentException("Unknown packet type: " + id);
								} else {
									recieved.add(type.readPacket(in));
								}
							} catch (ClassNotFoundException e) {
								Log.error("Error reading packet.", e);
							}
						}

						if (s.isClosed()) {
							stopped = true;
						}

						Thread.yield();
					}
					
					s.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
		}.start();
	}
	
	@Override
	public void close() throws IOException {
		stopped = true;
	}

	@Override
	public void send(NetPacket p) throws IOException {
		toSend.add(p);
	}

	@Override
	public boolean isAvaliable() throws IOException {
		return recieved.size() > 0;
	}

	@Override
	public NetPacket getNext() throws IOException {
		NetPacket p = recieved.get(0);
		recieved.remove(0);
		return p;
	}

	@Override
	public void encrypt() throws IOException {
		throw new UnsupportedOperationException("Encrypting is not supported");
	}

}

package io.github.tomaso2468.rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.github.tomaso2468.rpgonline.net.PacketType;
import io.github.tomaso2468.rpgonline.net.ServerManager;

/**
 * Packet used for login data.
 * @author Tomas
 *
 */
public class LoginPacket implements NetPacket {
	public static final byte PACKET_ID = (byte) 0xFF - 5;
	
	private static final long serialVersionUID = 246722656302089465L;
	private final String token;
	private final long id;
	
	public LoginPacket(String token, long id) {
		super();
		this.token = token;
		this.id = id;
	}
	
	public boolean isValid() {
		return ServerManager.getUserServer().isValidConnectToken(token) && ServerManager.getUserServer().getUserIDToken2(token) == id;
	}
	
	public long getID() {
		return id;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeUTF(token);
		out.writeLong(id);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new LoginPacket(in.readUTF(), in.readLong());
		}
	}
	
}

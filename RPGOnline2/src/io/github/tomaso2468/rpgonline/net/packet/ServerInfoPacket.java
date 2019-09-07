package io.github.tomaso2468.rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.github.tomaso2468.rpgonline.Version;
import io.github.tomaso2468.rpgonline.net.PacketType;

/**
 * Packet used for server info communication.
 * @author Tomas
 *
 */
public class ServerInfoPacket implements NetPacket {
	public static final byte PACKET_ID = (byte) 0xFF - 8;
	
	private static final long serialVersionUID = -5803480872418619141L;
	public final String type;
	public final String name;
	public final String desc;
	public final int players;
	public final Version gameVersion;

	public ServerInfoPacket(String type, String name, String desc, int players, Version gameVersion) {
		super();
		this.type = type;
		this.name = name;
		this.desc = desc;
		this.players = players;
		this.gameVersion = gameVersion;
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeUTF(type);
		out.writeUTF(name);
		out.writeUTF(desc);
		out.writeInt(players);
		out.writeUTF(gameVersion.toString());
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new ServerInfoPacket(in.readUTF(), in.readUTF(), in.readUTF(), in.readInt(), new Version(in.readUTF()));
		}
	}
	
	
}

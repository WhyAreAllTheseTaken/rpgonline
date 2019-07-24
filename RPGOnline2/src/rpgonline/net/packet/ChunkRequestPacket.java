package rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rpgonline.net.PacketType;

public class ChunkRequestPacket implements NetPacket {
	public static final byte PACKET_ID = (byte) 0xFF - 2;
	private static final long serialVersionUID = 2088638661829275185L;
	public final long x, y, z;
	public ChunkRequestPacket(long x, long y, long z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeLong(x);
		out.writeLong(y);
		out.writeLong(z);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new ChunkRequestPacket(in.readLong(), in.readLong(), in.readLong());
		}
	}
}

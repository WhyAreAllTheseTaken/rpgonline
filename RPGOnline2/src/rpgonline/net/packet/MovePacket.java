package rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rpgonline.net.PacketType;

public class MovePacket implements NetPacket {
	public static final byte PACKET_ID = (byte) 0xFF - 6;
	
	private static final long serialVersionUID = 3538606078927907705L;
	public final double x, y;
	public final boolean sprint;
	
	public MovePacket(double x, double y, boolean sprint) {
		super();
		this.x = x;
		this.y = y;
		this.sprint = sprint;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeBoolean(sprint);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new MovePacket(in.readDouble(), in.readDouble(), in.readBoolean());
		}
	}
	
}

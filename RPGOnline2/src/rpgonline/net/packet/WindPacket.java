package rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rpgonline.net.PacketType;

public class WindPacket implements NetPacket {
	public static final byte PACKET_ID = (byte) 0xFF - 11;
	
	private static final long serialVersionUID = 6681502216787431450L;
	private final float wind;

	public WindPacket(float wind) {
		super();
		this.wind = wind;
	}

	public float getWind() {
		return wind;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeFloat(wind);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new WindPacket(in.readFloat());
		}
	}
}

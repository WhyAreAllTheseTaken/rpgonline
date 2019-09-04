package rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rpgonline.audio.AudioManager;
import rpgonline.net.PacketType;

/**
 * Packet used for sound data.
 * @author Tomas
 *
 */
public class SoundPacket implements NetPacket {
	public static final byte PACKET_ID = (byte) 0xFF - 9;

	private static final long serialVersionUID = 1589226011832111169L;
	private final String id;
	private final float v;
	private final float p;
	private final float x;
	private final float y;
	private final float z;
	private final float dx;
	private final float dy;
	private final float dz;

	public SoundPacket(String id, float v, float p, float x, float y, float z, float dx, float dy, float dz) {
		super();
		this.id = id;
		this.v = v;
		this.p = p;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	public void apply() {
		AudioManager.playSound(id, v, p, x, y, z, false, dx, dy, dz);
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeUTF(id);
		out.writeFloat(v);
		out.writeFloat(p);
		out.writeFloat(x);
		out.writeFloat(y);
		out.writeFloat(z);
		out.writeFloat(dx);
		out.writeFloat(dy);
		out.writeFloat(dz);
	}

	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new SoundPacket(in.readUTF(), in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat(),
					in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat());
		}
	}
}

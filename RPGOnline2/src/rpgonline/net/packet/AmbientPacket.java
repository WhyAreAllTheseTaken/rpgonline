package rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rpgonline.audio.AudioManager;
import rpgonline.net.PacketType;

public class AmbientPacket implements NetPacket {
	public static final byte PACKET_AMBIENT = (byte) 0xFF - 1;
	private static final long serialVersionUID = -6976795378405555984L;
	private final String id;
	private final float v;
	private final float p;
	private final float x;
	private final float y;
	private final float z;
	private final boolean loop;

	public AmbientPacket(String id, float v, float p, float x, float y, float z, boolean loop) {
		super();
		this.id = id;
		this.v = v;
		this.p = p;
		this.x = x;
		this.y = y;
		this.z = z;
		this.loop = loop;
	}

	public void apply() {
		AudioManager.playAmbient(id, v, p, x, y, z, loop);
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_AMBIENT);
		out.writeUTF(id);
		out.writeFloat(v);
		out.writeFloat(p);
		out.writeFloat(x);
		out.writeFloat(y);
		out.writeFloat(z);
		out.writeBoolean(loop);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new AmbientPacket(in.readUTF(), in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat(), in.readBoolean());
		}
	}
}

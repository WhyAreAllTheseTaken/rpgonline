package rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

import rpgonline.net.PacketType;
import rpgonline.world.LightSource;

public class LightsPacket implements NetPacket {
	private static final long serialVersionUID = -3648467862338726913L;
	
	public static final byte PACKET_ID = (byte) 0xFF - 13;
	
	private final List<LightSource> lights;

	public LightsPacket(List<LightSource> lights) {
		super();
		this.lights = lights;
	}

	public List<LightSource> getLights() {
		return lights;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeInt(lights.size());
		for (LightSource l : lights) {
			out.writeDouble(l.x);
			out.writeDouble(l.y);
			out.writeFloat(l.c.r);
			out.writeFloat(l.c.g);
			out.writeFloat(l.c.b);
			out.writeFloat(l.c.a);
			out.writeFloat(l.brightness);
		}
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			int len = in.readInt();
			
			List<LightSource> lights = new ArrayList<>(len);
			
			for (int i = 0; i < len; i++) {
				double x = in.readDouble();
				double y = in.readDouble();
				float r = in.readFloat();
				float g = in.readFloat();
				float b = in.readFloat();
				float a = in.readFloat();
				float brightness = in.readFloat();
				lights.add(new LightSource(x, y, new Color(r, g, b, a), brightness));
			}
			
			return new LightsPacket(lights);
		}
	}
}

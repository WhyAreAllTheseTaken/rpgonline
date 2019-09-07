package io.github.tomaso2468.rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.net.PacketType;
import io.github.tomaso2468.rpgonline.world.LightSource;

/**
 * Packet used for sending light data.
 * @author Tomas
 *
 */
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
			out.writeDouble(l.getLX());
			out.writeDouble(l.getLY());
			out.writeFloat(l.getR());
			out.writeFloat(l.getG());
			out.writeFloat(l.getB());
			out.writeFloat(l.getColor().a);
			out.writeFloat(l.getBrightness());
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
				lights.add(new LightSource(x, y, new Color(r, g, b, a), brightness, false));
			}
			
			return new LightsPacket(lights);
		}
	}
}

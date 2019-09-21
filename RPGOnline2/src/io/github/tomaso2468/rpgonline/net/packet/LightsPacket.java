/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.net.PacketType;
import io.github.tomaso2468.rpgonline.world2d.LightSource;

/**
 * Packet used for sending light data.
 * @author Tomas
 *
 */
public class LightsPacket implements NetPacket {
	/**
	 * The serialisation ID.
	 */
	private static final long serialVersionUID = -3648467862338726913L;
	
	/**
	 * The packet ID.
	 */
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

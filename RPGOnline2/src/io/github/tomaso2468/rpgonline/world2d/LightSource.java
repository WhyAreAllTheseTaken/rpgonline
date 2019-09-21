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
package io.github.tomaso2468.rpgonline.world2d;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.net.Server2D;
import io.github.tomaso2468.rpgonline.net.PacketType;
import io.github.tomaso2468.rpgonline.net.ServerManager;
import io.github.tomaso2468.rpgonline.net.packet.NetPacket;

/**
 * A class for storing point light data.
 * 
 * @author Tomas
 */
public final class LightSource {
	/**
	 * The X position of the light.
	 */
	private double x;
	/**
	 * The Y position of the light.
	 */
	private double y;
	/**
	 * The color of the light.
	 */
	private Color c;
	/**
	 * The brightness of the light.
	 */
	private float brightness;
	/**
	 * Determines if updates to this light cause updates to be sent to the server.
	 */
	private boolean packet;

	/**
	 * Constructs a new light source.
	 * 
	 * @param x          The X position of the light.
	 * @param y          The Y position of the light.
	 * @param c          The color of the light.
	 * @param brightness The brightness of the light.
	 */
	public LightSource(double x, double y, Color c, float brightness, boolean packet) {
		super();
		this.x = x;
		this.y = y;
		this.c = c;
		this.brightness = brightness;
		this.packet = packet;
	}

	private void doUpdate() {
		if (packet) {
			((Server2D) ServerManager.getServer()).updateLight(new LightUpdate(this));
		}
	}

	/**
	 * Gets the X position of the light.
	 * 
	 * @return A double value.
	 */
	public double getLX() {
		return x;
	}

	/**
	 * Sets the X position of the light.
	 * 
	 * @param x A double value.
	 */
	public void setLX(double x) {
		this.x = x;
		doUpdate();
	}

	/**
	 * Gets the Y position of the light.
	 * 
	 * @return A double value.
	 */
	public double getLY() {
		return y;
	}

	/**
	 * Sets the Y position of the light.
	 * 
	 * @param y A double value.
	 */
	public void setLY(double y) {
		this.y = y;
		doUpdate();
	}

	/**
	 * Gets the red channel of this light.
	 * 
	 * @return A float value in the range 0..1.
	 */
	public float getR() {
		return c.r;
	}

	/**
	 * Gets the green channel of this light.
	 * 
	 * @return A float value in the range 0..1.
	 */
	public float getG() {
		return c.g;
	}

	/**
	 * Gets the blue channel of this light.
	 * 
	 * @return A float value in the range 0..1.
	 */
	public float getB() {
		return c.b;
	}

	/**
	 * Gets the color of this light.
	 * 
	 * @return A color object.
	 */
	public Color getColor() {
		return c;
	}

	/**
	 * Sets the color of this light.
	 * 
	 * @param c A color object.
	 */
	public void setColor(Color c) {
		this.c = c;
		doUpdate();
	}

	/**
	 * Gets the brightness of this light.
	 * 
	 * @return A float value in the range 0..Infinity.
	 */
	public float getBrightness() {
		return brightness;
	}

	/**
	 * Sets the brightness of this light.
	 * 
	 * @param brightness A float value in the range 0..Infinity.
	 */
	public void setBrightness(float brightness) {
		this.brightness = brightness;
		doUpdate();
	}

	/**
	 * A packet for lighting data.
	 * 
	 * @author Tomas
	 *
	 */
	public static class LightUpdate implements NetPacket {
		/**
		 * ID for serialisation.
		 */
		private static final long serialVersionUID = 8886589051797002820L;

		/**
		 * The ID of this packet.
		 */
		public static final byte PACKET_ID = (byte) 0xFF - 14;

		/**
		 * The color of the light.
		 */
		public final Color c;
		/**
		 * The X position of the light.
		 */
		public final double x;
		/**
		 * The Y position of the light.
		 */
		public final double y;
		/**
		 * The brightness of the light.
		 */
		public final float brightness;

		/**
		 * Constructs a new LightUpdate.
		 * @param l The light to update.
		 */
		public LightUpdate(LightSource l) {
			this(l.getColor(), l.getLX(), l.getLY(), l.getBrightness());
		}
		
		/**
		 * Constructs a new LightUpdate
		 * @param c The color of the light.
		 * @param x The X position of the light.
		 * @param y The Y position of the light.
		 * @param brightness The brightness of the light.
		 */
		public LightUpdate(Color c, double x, double y, float brightness) {
			super();
			this.c = c;
			this.x = x;
			this.y = y;
			this.brightness = brightness;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(DataOutputStream out) throws IOException {
			out.write(PACKET_ID);
			out.writeDouble(x);
			out.writeDouble(y);
			out.writeFloat(c.r);
			out.writeFloat(c.g);
			out.writeFloat(c.b);
			out.writeFloat(c.a);
			out.writeFloat(brightness);
		}

		/**
		 * The packet type of this packet.
		 * @author Tomas
		 *
		 */
		public static class Type implements PacketType {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
				double x = in.readDouble();
				double y = in.readDouble();
				float r = in.readFloat();
				float g = in.readFloat();
				float b = in.readFloat();
				float a = in.readFloat();
				float brightness = in.readFloat();

				return new LightUpdate(new Color(r, g, b, a), x, y, brightness);
			}
		}
	}
}

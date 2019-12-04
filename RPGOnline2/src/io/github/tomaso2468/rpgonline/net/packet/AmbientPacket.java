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

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.net.PacketType;

/**
 * Packet used for ambient sound events.
 * @author Tomaso2468
 *
 */
public class AmbientPacket implements NetPacket {
	/**
	 * The packet ID.
	 */
	public static final byte PACKET_AMBIENT = (byte) 0xFF - 1;
	/**
	 * The serialisation ID.
	 */
	private static final long serialVersionUID = -6976795378405555984L;
	/**
	 * The ID of the sound.
	 */
	private final String id;
	/**
	 * The volume of the sound.
	 */
	private final float v;
	/**
	 * The pitch of the sound.
	 */
	private final float p;
	/**
	 * The X position of the sound.
	 */
	private final float x;
	/**
	 * The Y position of the sound.
	 */
	private final float y;
	/**
	 * The Z position of the sound.
	 */
	private final float z;
	/**
	 * Determines if the sound should loop.
	 */
	private final boolean loop;

	/**
	 * Constructs a new Ambient Packet.
	 * @param id The ID of the sound.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 * @param loop Determines if the sound should loop.
	 */
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

	/**
	 * Apply the packet.
	 * 
	 * @param game The game.
	 */
	public void apply(Game game) {
		game.getAudio().playAmbient(id, v, p, x, y, z, loop);
	}
	
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * A class that is used to load the packet of this type.
	 * @author Tomas
	 */
	public static class Type implements PacketType {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new AmbientPacket(in.readUTF(), in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat(), in.readBoolean());
		}
	}
}

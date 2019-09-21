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

import io.github.tomaso2468.rpgonline.Version;
import io.github.tomaso2468.rpgonline.net.PacketType;

/**
 * Packet used for server info communication.
 * @author Tomas
 *
 */
public class ServerInfoPacket implements NetPacket {
	/**
	 * The packet ID.
	 */
	public static final byte PACKET_ID = (byte) 0xFF - 8;
	
	/**
	 * The serialisation ID.
	 */
	private static final long serialVersionUID = -5803480872418619141L;
	public final String type;
	public final String name;
	public final String desc;
	public final int players;
	public final Version gameVersion;

	public ServerInfoPacket(String type, String name, String desc, int players, Version gameVersion) {
		super();
		this.type = type;
		this.name = name;
		this.desc = desc;
		this.players = players;
		this.gameVersion = gameVersion;
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeUTF(type);
		out.writeUTF(name);
		out.writeUTF(desc);
		out.writeInt(players);
		out.writeUTF(gameVersion.toString());
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new ServerInfoPacket(in.readUTF(), in.readUTF(), in.readUTF(), in.readInt(), new Version(in.readUTF()));
		}
	}
	
	
}

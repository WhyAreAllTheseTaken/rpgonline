package io.github.tomaso2468.rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.github.tomaso2468.rpgonline.audio.AudioManager;
import io.github.tomaso2468.rpgonline.net.PacketType;

/**
 * Packet with a command to stop all ambient sounds.
 * @author Tomas
 *
 */
public class StopAmbientPacket implements NetPacket {
	public static final byte PACKET_ID = (byte) 0xFF - 10;
	
	private static final long serialVersionUID = 3184792895522235066L;
	
	public void apply() {
		AudioManager.stopAmbient();
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new StopAmbientPacket();
		}
	}
}

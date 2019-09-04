package rpgonline.net.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import rpgonline.entity.Entity;
import rpgonline.net.PacketType;

/**
 * Packet used to remove entities.
 * @author Tomas
 *
 */
public class EntityRemovePacket implements NetPacket {
	public static final byte PACKET_ID = (byte) 0xFF - 3;
	
	private static final long serialVersionUID = -5857037228284495985L;
	private final String id;

	public EntityRemovePacket(Entity e) {
		this.id = e.getID();
	}
	
	public EntityRemovePacket(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		out.write(PACKET_ID);
		out.writeUTF(id);
	}
	
	public static class Type implements PacketType {
		@Override
		public NetPacket readPacket(DataInputStream in) throws IOException, ClassNotFoundException {
			return new EntityRemovePacket(in.readUTF());
		}
	}
}

package rpgonline.net.packet;

import rpgonline.entity.Entity;

public class EntityRemovePacket implements NetPacket {
	private static final long serialVersionUID = -5857037228284495985L;
	private final String id;

	public EntityRemovePacket(Entity e) {
		this.id = e.getID();
	}

	public String getID() {
		return id;
	}
}

package rpgonline.net.packet;

import rpgonline.abt.TagGroup;
import rpgonline.entity.Entity;
import rpgonline.entity.EntityManager;

public class EntityAddPacket implements NetPacket {
	private static final long serialVersionUID = 1972178890606910609L;
	private final TagGroup tg;

	public EntityAddPacket(Entity e) {
		tg = e.toABT("entity");
	}

	public Entity resolve(EntityManager m) {
		return new Entity(m, tg, false);
	}
}

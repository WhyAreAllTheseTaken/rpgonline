package io.github.tomaso2468.rpgonline.net.packet;

import io.github.tomaso2468.rpgonline.abt.TagGroup;
import io.github.tomaso2468.rpgonline.entity.Entity;
import io.github.tomaso2468.rpgonline.entity.EntityManager;

/**
 * Packet used to add entities.
 * @author Tomas
 *
 */
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

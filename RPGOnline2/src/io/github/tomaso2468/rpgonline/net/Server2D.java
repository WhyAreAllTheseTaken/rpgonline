package io.github.tomaso2468.rpgonline.net;

import io.github.tomaso2468.rpgonline.entity.Entity;
import io.github.tomaso2468.rpgonline.net.packet.UpdatePacket;
import io.github.tomaso2468.rpgonline.world.LightSource.LightUpdate;

public interface Server2D extends Server {
	/**
	 * Called to indicate an update to an entity has occurred.
	 * @param up The entity information to update.
	 */ 
	public void updateEntity(UpdatePacket up);

	/**
	 * Adds an entity to the server.
	 * @param e The entity to add.
	 */
	public void addEntity(Entity e);

	/**
	 * Removes an entity from the server.
	 * @param e The entity to remove.
	 */
	public void removeEntity(Entity e);

	/**
	 * Called to indicate an update to a light has occurred.
	 * @param lightUpdate the update packet.
	 */ 
	public void updateLight(LightUpdate lightUpdate);
}

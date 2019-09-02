package rpgonline.net;

import rpgonline.entity.Entity;
import rpgonline.net.packet.UpdatePacket;

/**
 * An interface representing a server.
 * @author Tomas
 */
public interface Server extends TickBased {
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
	 * Plays a sound at the specified location.
	 * @param id The ID of the sound to play.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 * @param dx The X speed of the sound.
	 * @param dy The Y speed of the sound.
	 * @param dz The Z speed of the sound.
	 */
	public void playSound(String id, float v, float p, float x, float y, float z, float dx, float dy, float dz);

	/**
	 * Plays a sound at the specified location.
	 * @param id The ID of the sound to play.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 */
	public default void playSound(String id, float v, float p, float x, float y, float z) {
		playSound(id, v, p, x, y, z, 0, 0, 0);
	}

	/**
	 * Plays a sound at the specified location.
	 * @param id The ID of the sound to play.
	 * @param v The volume of the sound.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 */
	public default void playSound(String id, float v, float x, float y, float z) {
		playSound(id, v, 1, x, y, z, 0, 0, 0);
	}

	/**
	 * Plays a sound at the specified location.
	 * @param id The ID of the sound to play.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 */
	public default void playSound(String id, float x, float y, float z) {
		playSound(id, 1, 1, x, y, z, 0, 0, 0);
	}

	/**
	 * Plays an ambient sound at the specified location.
	 * @param name The name of the sound.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 * @param loop {@code true} if the sound should loop, {@code false} otherwise.
	 */
	public void playAmbient(String name, float v, float p, float x, float y, float z, boolean loop);

	/**
	 * Plays an ambient sound at the specified location.
	 * @param name The name of the sound.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 */
	public default void playAmbient(String name, float v, float p, float x, float y, float z) {
		playAmbient(name, v, p, x, y, z, false);
	}
}

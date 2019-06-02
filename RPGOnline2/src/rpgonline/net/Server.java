package rpgonline.net;

import rpgonline.entity.Entity;
import rpgonline.entity.UpdatePacket;

public interface Server {
	public void updateEntity(UpdatePacket up);

	public void addEntity(Entity e);

	public void removeEntity(Entity e);

	public void playSound(String id, float v, float p, float x, float y, float z, float dx, float dy, float dz);

	public default void playSound(String id, float v, float p, float x, float y, float z) {
		playSound(id, v, p, x, y, z, 0, 0, 0);
	}

	public default void playSound(String id, float v, float x, float y, float z) {
		playSound(id, v, 1, x, y, z, 0, 0, 0);
	}

	public default void playSound(String id, float x, float y, float z) {
		playSound(id, 1, 1, x, y, z, 0, 0, 0);
	}

	public void playAmbient(String name, float v, float p, float x, float y, float z, boolean loop);

	public default void playAmbient(String name, float v, float p, float x, float y, float z) {
		playAmbient(name, v, p, x, y, z, false);
	}
}

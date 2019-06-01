package rpgonline.texture.entity;

import rpgonline.entity.Entity;
import rpgonline.world.World;

public class NullEntityTexture implements EntityTexture {

	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		return -1;
	}

}

package io.github.tomaso2468.rpgonline.texture.entity;

import io.github.tomaso2468.rpgonline.entity.Entity;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * An entity texture that renders nothing.
 * @author Tomas
 *
 */
public class NullEntityTexture implements EntityTexture {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		return -1;
	}

}

package io.github.tomaso2468.rpgonline.texture.entity;

import io.github.tomaso2468.rpgonline.entity.Entity;
import io.github.tomaso2468.rpgonline.texture.TextureMap;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * A basic static entity texture.
 * @author Tomas
 *
 */
public class BasicEntityTexture implements EntityTexture {
	/**
	 * The texture ID to use.
	 */
	private final int t;
	/**
	 * The X offset for this texture.
	 */
	private final float x;
	/**
	 * The Y offset for this texture.
	 */
	private final float y;
	
	/**
	 * Constructs a new BasicEntityTexture.
	 * @param s The texture ID.
	 * @param x The X offset for this texture.
	 * @param y The Y offset for this texture.
	 */
	public BasicEntityTexture(String s, float x, float y) {
		this.t = TextureMap.getTextureIndex(s);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructs a new BasicEntityTexture.
	 * @param s The texture ID.
	 */
	public BasicEntityTexture(String s) {
		this(s, 0, 0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		return this.t;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getX() {
		return x;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getY() {
		return y;
	}

}

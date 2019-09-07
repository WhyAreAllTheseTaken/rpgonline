package io.github.tomaso2468.rpgonline.texture.entity;

import org.apache.commons.math3.util.FastMath;

import io.github.tomaso2468.rpgonline.entity.Entity;
import io.github.tomaso2468.rpgonline.texture.TextureMap;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * An entity texture that changes depending on wind.
 * @author Tomas
 *
 */
public class WindEntityTexture implements EntityTexture {
	/**
	 * The textures to use.
	 */
	private final int[] t;
	/**
	 * The points at which the texture should be changed.
	 */
	private final float[] b;
	/**
	 * The X offset for this texture.
	 */
	private final float x;
	/**
	 * The Y offset for this texture.
	 */
	private final float y;
	
	/**
	 * Constructs a new WindEntityTexture.
	 * @param s The textures to use.
	 * @param b The values to change the texture at.
	 * @param x The X offset for this texture.
	 * @param y The Y offset for this texture.
	 */
	public WindEntityTexture(String[] s, float[] b, float x, float y) {
		t = new int[s.length];
		
		for (int i = 0; i < s.length; i++) {
			t[i] = TextureMap.getTextureIndex(s[i]);
		}
		
		this.x = x;
		this.y = y;
		
		this.b = b;
	}
	
	/**
	 * Constructs a new WindEntityTexture.
	 * @param s The textures to use.
	 * @param b The values to change the texture at.
	 */
	public WindEntityTexture(String[] s, float[] b) {
		this(s, b, 0, 0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		if(wind == 0) {
			return t[0];
		}
		float wind2 = FastMath.abs(wind);
		
		int texture = t[0];
		for (int i = 0; i < b.length; i++) {
			if(wind2 >= b[i]) {
				texture = t[i];
			}
		}
		
		return texture;
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

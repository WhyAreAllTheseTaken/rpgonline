package io.github.tomaso2468.rpgonline.texture;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.Graphics;

import io.github.tomaso2468.rpgonline.RPGConfig;
import io.github.tomaso2468.rpgonline.tile.Tile;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * A tile texture that is affected by wind.
 * @author Tomas
 */
public class WindTexture extends BasicTileTexture {
	/**
	 * The strength of the wind effect.
	 */
	private float f;
	/**
	 * Constructs a new WindTexture.
	 * @param s The texture ID.
	 * @param x The X offset of the texture.
	 * @param y The Y offset of the texture.
	 * @param f The strength of the wind effect.
	 */
	public WindTexture(String s, float x, float y, float f) {
		super(s, x, y);
		this.f = f;
	}
	
	/**
	 * Constructs a new WindTexture.
	 * @param s The texture ID.
	 * @param f The strength of the wind effect.
	 */
	public WindTexture(String s, float f) {
		this(s, 0, 0, f);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCustom() {
		return RPGConfig.isWind();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics g, long x, long y, long z, World w, String state, Tile t, float sx, float sy, float wind) {
		Graphics.setCurrent(g);
		
		float amount = (float) wibble((x * y + (System.currentTimeMillis() / 50)) * (double) f) * wind
				* f;
		
		TextureMap.getTexture(getTexture(x, y, z, w, state, t)).drawSheared(sx - amount, sy, amount, 0);
	}
	
	/**
	 * A function that computes the wind effect.
	 * @param v The value for wind.
	 * @return A double value.
	 */
	public static double wibble(double v) {
		double a = v % 50 + FastMath.log10(v);
		double b = FastMath.sin(a) + FastMath.sin(FastMath.cbrt(a)) * 2 + (FastMath.cos(2 * a) / 3)
				+ (FastMath.sin(-a) / 2) + (FastMath.tanh(a / 2)) / 3;
		return (FastMath.abs(b) / 2.5 - 1 + FastMath.sin(v / 4) * 4) / 8;
	}
}

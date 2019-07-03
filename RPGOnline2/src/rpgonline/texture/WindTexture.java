package rpgonline.texture;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.Graphics;

import rpgonline.RPGConfig;
import rpgonline.tile.Tile;
import rpgonline.world.World;

public class WindTexture extends BasicTileTexture {
	private float f;
	public WindTexture(String s, float x, float y, float f) {
		super(s, x, y);
		this.f = f;
	}
	
	public WindTexture(String s, float f) {
		this(s, 0, 0, f);
	}
	
	@Override
	public boolean isCustom() {
		return RPGConfig.isWind();
	}
	
	@Override
	public void render(Graphics g, long x, long y, long z, World w, String state, Tile t, float sx, float sy, float wind) {
		Graphics.setCurrent(g);
		
		float amount = (float) wibble((x * y + (System.currentTimeMillis() / 50)) * (double) f) * wind
				* f;
		
		TextureMap.getTexture(getTexture(x, y, z, w, state, t)).drawSheared(sx - amount, sy, amount, 0);
	}
	
	public static double wibble(double v) {
		double a = v % 50 + FastMath.log10(v);
		double b = FastMath.sin(a) + FastMath.sin(FastMath.cbrt(a)) * 2 + (FastMath.cos(2 * a) / 3)
				+ (FastMath.sin(-a) / 2) + (FastMath.tanh(a / 2)) / 3;
		return (FastMath.abs(b) / 2.5 - 1 + FastMath.sin(v / 4) * 4) / 8;
	}
}

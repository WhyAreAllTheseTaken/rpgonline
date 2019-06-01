package rpgonline.texture.entity;

import org.apache.commons.math3.util.FastMath;

import rpgonline.entity.Entity;
import rpgonline.texture.TextureMap;
import rpgonline.world.World;

public class WindEntityTexture implements EntityTexture {
	private final int[] t;
	private final float[] b;
	private final float x;
	private final float y;
	
	public WindEntityTexture(String[] s, float[] b, float x, float y) {
		t = new int[s.length];
		
		for (int i = 0; i < s.length; i++) {
			t[i] = TextureMap.getTextureIndex(s[i]);
		}
		
		this.x = x;
		this.y = y;
		
		this.b = b;
	}
	
	public WindEntityTexture(String[] s, float[] b) {
		this(s, b, 0, 0);
	}
	
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
	
	@Override
	public float getX() {
		return x;
	}
	
	@Override
	public float getY() {
		return y;
	}

}

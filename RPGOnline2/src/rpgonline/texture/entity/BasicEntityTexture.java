package rpgonline.texture.entity;

import rpgonline.entity.Entity;
import rpgonline.texture.TextureMap;
import rpgonline.world.World;

public class BasicEntityTexture implements EntityTexture {
	private final int t;
	private final float x;
	private final float y;
	
	public BasicEntityTexture(String s, float x, float y) {
		this.t = TextureMap.getTextureIndex(s);
		this.x = x;
		this.y = y;
	}
	
	public BasicEntityTexture(String s) {
		this(s, 0, 0);
	}
	
	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		return this.t;
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

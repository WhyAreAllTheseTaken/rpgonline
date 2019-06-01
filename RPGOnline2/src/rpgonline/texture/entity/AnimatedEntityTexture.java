package rpgonline.texture.entity;

import rpgonline.entity.Entity;
import rpgonline.texture.TextureMap;
import rpgonline.world.World;

public class AnimatedEntityTexture implements EntityTexture {
	private final int[] textures;
	private final long interval;
	private final float x;
	private final float y;
	
	public AnimatedEntityTexture(String[] textures, long interval, float x, float y) {
		super();
		this.textures = new int[textures.length];
		for (int i = 0; i < this.textures.length; i++) {
			this.textures[i] = TextureMap.getTextureIndex(textures[i]);
		}
		this.interval = interval;
		this.x = x;
		this.y = y;
	}
	
	public AnimatedEntityTexture(String[] textures, long interval) {
		this(textures, interval, 0, 0);
	}
	
	public AnimatedEntityTexture(String texture, int length, long interval, float x, float y) {
		this(getTexturesFromIndex(texture, length), interval, x, y);
	}
	
	public AnimatedEntityTexture(String texture, int length, long interval) {
		this(getTexturesFromIndex(texture, length), interval, 0, 0);
	}
	
	protected static String[] getTexturesFromIndex(String texture, int length) {
		String[] textures = new String[length];
		
		for (int i = 0; i < textures.length; i++) {
			textures[i] = texture + "." + i;
		}
		
		return textures;
	}

	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		final int index = (int) (System.currentTimeMillis() / interval % textures.length);
		return textures[index];
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

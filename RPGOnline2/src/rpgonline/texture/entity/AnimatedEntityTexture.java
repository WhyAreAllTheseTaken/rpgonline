package rpgonline.texture.entity;

import rpgonline.entity.Entity;
import rpgonline.texture.TextureMap;
import rpgonline.world.World;

/**
 * An animated entity texture.
 * @author Tomas
 *
 */
public class AnimatedEntityTexture implements EntityTexture {
	/**
	 * The textures to use.
	 */
	private final int[] textures;
	/**
	 * The interval between each frame in milliseconds.
	 */
	private final long interval;
	/**
	 * The X offset for this texture.
	 */
	private final float x;
	/**
	 * The Y offset for this texture.
	 */
	private final float y;
	
	/**
	 * Constructs a new AnimatedEntityTexture.
	 * @param textures The textures to use.
	 * @param interval The time between each frame in milliseconds.
	 * @param x The X offset for this texture.
	 * @param y The Y offset for this texture.
	 */
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
	
	/**
	 * Constructs a new AnimatedEntityTexture.
	 * @param textures The textures to use.
	 * @param interval The time between each frame in milliseconds.
	 */
	public AnimatedEntityTexture(String[] textures, long interval) {
		this(textures, interval, 0, 0);
	}
	
	/**
	 * Constructs a new AnimatedEntityTexture.
	 * @param texture The texture prefix to use.
	 * @param length The length of the texture list.
	 * @param interval The time between each frame in milliseconds.
	 * @param x The X offset for this texture.
	 * @param y The Y offset for this texture.
	 */
	public AnimatedEntityTexture(String texture, int length, long interval, float x, float y) {
		this(getTexturesFromIndex(texture, length), interval, x, y);
	}
	
	/**
	 * Constructs a new AnimatedEntityTexture.
	 * @param texture The texture prefix to use.
	 * @param length The length of the texture list.
	 * @param interval The time between each frame in milliseconds.
	 */
	public AnimatedEntityTexture(String texture, int length, long interval) {
		this(getTexturesFromIndex(texture, length), interval, 0, 0);
	}
	
	/**
	 * Gets the list of textures from the single texture prefix.
	 * @param texture The texture prefix.
	 * @param length The length of the texture list.
	 * @return An array of texture IDs.
	 */
	protected static String[] getTexturesFromIndex(String texture, int length) {
		String[] textures = new String[length];
		
		for (int i = 0; i < textures.length; i++) {
			textures[i] = texture + "." + i;
		}
		
		return textures;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		final int index = (int) (System.currentTimeMillis() / interval % textures.length);
		return textures[index];
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

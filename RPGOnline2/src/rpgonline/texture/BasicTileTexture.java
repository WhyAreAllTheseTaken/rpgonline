package rpgonline.texture;

import rpgonline.tile.Tile;
import rpgonline.world.World;

/**
 * A basic static tile texture.
 * @author Tomas
 *
 */
public class BasicTileTexture implements TileTexture {
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
	 * Constructs a new BasicTileTexture.
	 * @param s The texture ID.
	 * @param x The X offset for this texture.
	 * @param y The Y offset for this texture.
	 */
	public BasicTileTexture(String s, float x, float y) {
		this.t = TextureMap.getTextureIndex(s);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructs a new BasicTileTexture.
	 * @param s The texture ID.
	 */
	public BasicTileTexture(String s) {
		this(s, 0, 0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture(long x, long y, long z, World w, String state, Tile t) {
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

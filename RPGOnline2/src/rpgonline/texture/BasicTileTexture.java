package rpgonline.texture;

import rpgonline.tile.Tile;
import rpgonline.world.World;

public class BasicTileTexture implements TileTexture {
	private final int t;
	private final float x;
	private final float y;
	
	public BasicTileTexture(String s, float x, float y) {
		this.t = TextureMap.getTextureIndex(s);
		this.x = x;
		this.y = y;
	}
	
	public BasicTileTexture(String s) {
		this(s, 0, 0);
	}
	
	@Override
	public int getTexture(long x, long y, long z, World w, String state, Tile t) {
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

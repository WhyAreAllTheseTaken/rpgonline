package rpgonline.texture;

import rpgonline.tile.Tile;
import rpgonline.world.World;

public class GroupedTileTexture implements TileTexture {
	private final TileTexture[] textures;
	
	public GroupedTileTexture(TileTexture[] textures) {
		super();
		this.textures = textures;
	}
	
	@Override
	public boolean isPure() {
		return false;
	}

	@Override
	public int getTexture(long x, long y, long z, World w, String state, Tile t) {
		return -1;
	}
	
	@Override
	public TileTexture[] getTextures() {
		return textures;
	}

}

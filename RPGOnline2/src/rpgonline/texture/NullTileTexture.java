package rpgonline.texture;

import rpgonline.tile.Tile;
import rpgonline.world.World;

public class NullTileTexture implements TileTexture {

	@Override
	public int getTexture(long x, long y, long z, World w, String state, Tile t) {
		return -1;
	}

}

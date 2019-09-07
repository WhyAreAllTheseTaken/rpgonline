package io.github.tomaso2468.rpgonline.texture;

import io.github.tomaso2468.rpgonline.tile.Tile;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * A texture that renders nothing.
 * @author Tomas
 *
 */
public class NullTileTexture implements TileTexture {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture(long x, long y, long z, World w, String state, Tile t) {
		return -1;
	}

}

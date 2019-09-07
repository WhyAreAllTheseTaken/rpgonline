package io.github.tomaso2468.rpgonline.texture;

import io.github.tomaso2468.rpgonline.tile.Tile;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * A tile texture made of multiple textures.
 * @author Tomas
 *
 */
public class GroupedTileTexture implements TileTexture {
	/**
	 * The array of textures.
	 */
	private final TileTexture[] textures;
	
	/**
	 * Constructs a new GroupedTileTexture.
	 * @param textures The array of textures.
	 */
	public GroupedTileTexture(TileTexture[] textures) {
		super();
		this.textures = textures;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPure() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture(long x, long y, long z, World w, String state, Tile t) {
		return -1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TileTexture[] getTextures(long x, long y, long z, World w, String state, Tile t) {
		return textures;
	}

}

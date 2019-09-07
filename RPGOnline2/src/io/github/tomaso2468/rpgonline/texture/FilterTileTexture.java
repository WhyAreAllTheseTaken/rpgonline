package io.github.tomaso2468.rpgonline.texture;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import io.github.tomaso2468.rpgonline.tile.Tile;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * A tile texture that is filtered through a color based upon the hex value of their game state.
 * @author Tomas
 */
public class FilterTileTexture extends BasicTileTexture {
	/**
	 * Constructs a new FilterTileTexture.
	 * @param s The texture ID.
	 * @param x The X offset of this texture.
	 * @param y The Y offset of this texture.
	 */
	public FilterTileTexture(String s, float x, float y) {
		super(s, x, y);
	}
	
	/**
	 * Constructs a new FilterTileTexture.
	 * @param s The texture ID.
	 */
	public FilterTileTexture(String s) {
		super(s, 0, 0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCustom() {
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics g, long x, long y, long z, World w, String state, Tile t, float sx, float sy,
			float wind) {
		Graphics.setCurrent(g);
		
		TextureMap.getTexture(getTexture(x, y, z, w, state, t)).draw(sx, sy, new Color(Integer.parseUnsignedInt(state, 16)));
	}
}

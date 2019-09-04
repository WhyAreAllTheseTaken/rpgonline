package rpgonline.texture;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import rpgonline.tile.Tile;
import rpgonline.world.World;

/**
 * An interface representing a system for drawing tiles.
 * @author Tomas
 *
 * @see rpgonline.texture.entity.EntityTexture
 */
public interface TileTexture {
	/**
	 * Determines if the texture is "pure" in that it contains no sub textures.
	 * @return {@code true} if the texture is pure, {@code false} otherwise.
	 */
	public default boolean isPure() {
		return true;
	}
	
	/**
	 * Determines if the texture uses custom rendering.
	 * @return {@code true} if the texture uses custom rendering, {@code false} otherwise.
	 */
	public default boolean isCustom() {
		return false;
	}
	
	/**
	 * Gets the texture ID for this texture.
	 * @param x The X position of the tile.
	 * @param y The Y position of the tile.
	 * @param z The Z position of the tile.
	 * @param w The world the tile is in.
	 * @param state The state of the tile.
	 * @param t The tile to render.
	 * @return A texture ID.
	 */
	public default int getTexture(long x, long y, long z, World w, String state, Tile t) {
		return -1;
	}
	
	/**
	 * Gets all subtextures in this texture.
	 * @param x The X position of the tile.
	 * @param y The Y position of the tile.
	 * @param z The Z position of the tile.
	 * @param w The world the tile is in.
	 * @param state The state of the tile.
	 * @param t The tile to render.
	 * @return A texture array.
	 */
	public default TileTexture[] getTextures(long x, long y, long z, World w, String state, Tile t) {
		return new TileTexture[] {this};
	}
	
	/**
	 * Renders the tile.
	 * @param g The graphics context to use.
	 * @param x The X position of the tile.
	 * @param y The Y position of the tile.
	 * @param z The Z position of the tile.
	 * @param w The world the tile is in.
	 * @param state The state of the tile.
	 * @param t The tile to render.
	 * @param sx The X position of the tile on the screen.
	 * @param sy The Y position of the tile on the screen.
	 * @param wind The current wind value.
	 * @throws SlickException If an error occurs during rendering.
	 */
	public default void render(Graphics g, long x, long y, long z, World w, String state, Tile t, float sx, float sy, float wind) throws SlickException {
		
	}
	
	/**
	 * Gets the X offset of the tile.
	 * @return A float value.
	 */
	public default float getX() {
		return 0;
	}
	
	/**
	 * Gets the Y offset of the tile.
	 * @return A float value.
	 */
	public default float getY() {
		return 0;
	}
}

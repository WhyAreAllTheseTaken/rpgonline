package rpgonline.texture.entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import rpgonline.entity.Entity;
import rpgonline.texture.TextureMap;
import rpgonline.world.World;

/**
 * An interface for rendering entities. This is similar to {@code TileTexture}.
 * @author Tomas
 *
 * @see rpgonline.texture.TileTexture
 */
public interface EntityTexture {
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
	 * Gets the texture ID of this texture.
	 * @param x The X position of the entity.
	 * @param y The Y position of the entity.
	 * @param z The Z position of the entity.
	 * @param w The world the entity is in.
	 * @param e The entity to render.
	 * @param wind The current wind value.
	 * @return A texture ID.
	 */
	public int getTexture(double x, double y, double z, World w, Entity e, float wind);
	
	/**
	 * Gets all subtextures in this texture.
	 * @param x The X position of the tile.
	 * @param y The Y position of the tile.
	 * @param z The Z position of the tile.
	 * @param w The world the tile is in.
	 * @param e The entity to render.
	 * @param wind The current wind value.
	 * @return A texture array.
	 */
	public default EntityTexture[] getTextures(double x, double y, double z, World w, Entity e, float wind) {
		return new EntityTexture[] {this};
	}
	
	/**
	 * Renders the entity with this texture.
	 * @param g The current graphics context.
	 * @param x The X position of the tile.
	 * @param y The Y position of the tile.
	 * @param z The Z position of the tile.
	 * @param w The world the tile is in.
	 * @param e The entity to render.
	 * @param sx The X position on the screen to render the entity at.
	 * @param sy The Y position on the screen to render the entity at.
	 * @param wind The current wind value.
	 */
	public default void render(Graphics g, double x, double y, double z, World w, Entity e, float sx, float sy, float wind) {
		Image img = TextureMap.getTexture(getTexture(x, y, z, w, e, wind));
		
		if (img == null) {
			return;
		}
		
		g.drawImage(img, sx, sy);
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

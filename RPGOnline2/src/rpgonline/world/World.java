package rpgonline.world;

import java.io.IOException;
import java.util.List;

import org.newdawn.slick.Color;

import rpgonline.entity.Entity;
import rpgonline.tile.Tile;

/**
 * <p>An interface for classes storing world data.</p>
 * <p>Layers are indexed by their z value with a lower z value being higher up</p>
 * @author Tomas
 */
public interface World {
	/**
	 * Gets the tile at a specified position.
	 * @param x The X position to read from.
	 * @param y The Y position to read from.
	 * @param z The Z (layer) position to read from.
	 * @return a non-null tile instance.
	 */
	public Tile getTile(long x, long y, long z);

	/**
	 * Gets the state data of a tile at a specified position.
	 * @param x The X position to read from.
	 * @param y The Y position to read from.
	 * @param z The Z (layer) position to read from.
	 * @return a string representing a state or an empty string (null may not be returned).
	 */
	public String getTileState(long x, long y, long z);

	/**
	 * Sets the tile at a specified position.
	 * @param x The X position to write to.
	 * @param y The Y position to write to.
	 * @param z The Z (layer) position to write to.
	 * @param tile A non-null tile instance.
	 * @param state a string representing a state or an empty string (null may not be set).
	 */
	public void setTile(long x, long y, long z, Tile tile, String state);

	/**
	 * Sets the area ID at a specified position. This can be used to determine music for specified areas in open world games.
	 * @param x The X position to write to.
	 * @param y The Y position to write to.
	 * @param z The Z (layer) position to write to.
	 * @param id The ID of the area or null to remove area data.
	 */
	public void setAreaID(long x, long y, long z, String id);

	/**
	 * Gets the area ID at a specified position. This can be used to determine music for specified areas in open world games.
	 * @param x The X position to read from.
	 * @param y The Y position to read from.
	 * @param z The Z (layer) position to read from.
	 * @return The ID of the area or null if no area data exists.
	 */
	public String getAreaID(long x, long y, long z);

	/**
	 * Sets the biome ID at a specified location. This can be used in open world games with generated terrain or for setting theming for different areas.
	 * @param x The X position to write to.
	 * @param y The Y position to write to.
	 * @param z The Z (layer) position to write to.
	 * @param id A biome index usually from an enum or array.
	 */
	public void setBiomeID(long x, long y, long z, int id);

	/**
	 * Gets the biome ID at a specified location. This can be used in open world games with generated terrain or for setting theming for different areas.
	 * @param x The X position to read from.
	 * @param y The Y position to read from.
	 * @param z The Z (layer) position to read from.
	 * @return A biome index usually from an enum or array.
	 */
	public int getBiomeID(long x, long y, long z);

	/**
	 * Sets the tile at a specified position.
	 * @param x The X position to write to.
	 * @param y The Y position to write to.
	 * @param z The Z (layer) position to write to.
	 * @param tile A non-null tile instance.
	 */
	public default void setTile(long x, long y, long z, Tile tile) {
		setTile(x, y, z, tile, "");
	}

	/**
	 * Gets the lowest z position in the world.
	 * @return A long value.
	 */
	public long getMinZ();

	/**
	 * Gets the highest z position in the world.
	 * @return A long value.
	 */
	public long getMaxZ();

	/**
	 * Gets the lowest x position in the world.
	 * @return A long value.
	 */
	public long getMinX();

	/**
	 * Gets the highest x position in the world.
	 * @return A long value.
	 */
	public long getMaxX();

	/**
	 * Gets the lowest y position in the world.
	 * @return A long value.
	 */
	public long getMinY();

	/**
	 * Gets the highest y position in the world.
	 * @return A long value.
	 */
	public long getMaxY();

	/**
	 * A method where client-side updates should be performed (e.g. unloading).
	 */
	public default void doUpdateClient() {
	}

	/**
	 * A method where server-side updates should be performed (e.g. unloading). Normally, this method calls {@code doUpdateClient} at a slower rate.
	 */
	public default void doUpdateServer() {
		if (System.currentTimeMillis() / 10 % 10 == 0) {
			doUpdateClient();
		}
	}

	/**
	 * Gets the ambient light color of this level.
	 * @return A color object.
	 */
	public default Color getLightColor() {
		return Color.white;
	}

	/**
	 * Saves the level data.
	 * @throws IOException If an error occurs writing data.
	 */
	public default void save() throws IOException {

	}

	/**
	 * Adds a light to the world.
	 * @param light The light to add.
	 */
	public void addLight(LightSource light);

	/**
	 * Removes a light.
	 * @param light The light to remove.
	 */
	public void removeLight(LightSource light);

	/**
	 * Gets all lights in the world.
	 * @return A list of light sources.
	 */
	public List<LightSource> getLights();

	/**
	 * Gets a modifiable list of all entities in the world.
	 * @return A list object.
	 */
	public List<Entity> getEntities();
}

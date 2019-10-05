/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.world2d;

import java.io.IOException;
import java.util.List;

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.world2d.entity.Entity;

/**
 * <p>An interface for classes storing world data.</p>
 * <p>Layers are indexed by their z value with a lower z value being higher up</p>
 * @author Tomaso2468
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
	 * Sets the state of a tile.
	 * @param x The X position to write to.
	 * @param y The Y position to write to.
	 * @param z The Z (layer) position to write to.
	 * @param state a string representing a state or an empty string (null may not be set).
	 */
	public void setState(long x, long y, long z, String state);

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

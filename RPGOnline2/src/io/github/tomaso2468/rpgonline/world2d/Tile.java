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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.world2d.texture.TileTexture;

/**
 * A class for storing the properties of a single tile.
 * @author Tomas
 *
 */
public class Tile {
	/**
	 * The default hitbox.
	 */
	private static final Rectangle defaultBox = new Rectangle(0, 0, 1, 1);
	/**
	 * The color on maps of this tile.
	 */
	private final Color c;
	/**
	 * The texture of this tile.
	 */
	private final TileTexture t;
	/**
	 * The ID of this tile.
	 */
	private final String id;
	/**
	 * The tags associated with this tile.
	 */
	private final List<String> tags = new ArrayList<String>();

	/**
	 * Constructs a new tile.
	 * @param id The ID of this tile.
	 * @param c The color on the map of this tile.
	 * @param t The texture of this tile.
	 * @param registry The tile registry for this tile to register to.
	 */
	public Tile(String id, Color c, TileTexture t, Map<String, Tile> registry) {
		super();
		this.c = c;
		this.t = t;
		this.id = id;
		
		registry.put(id, this);
		
		addTag("id:" + id);
	}

	/**
	 * Determines if this tile is solid in the specified state.
	 * @param state The state this tile is in.
	 * @return {@code true} if hitboxes should be computed for this tile, {@code false} otherwise.
	 */
	public boolean isSolid(String state) {
		return true;
	}

	/**
	 * Gets the color of this tile for maps.
	 * @return A color object.
	 */
	public Color getColor() {
		return c;
	}

	/**
	 * Gets the texture of this tile.
	 * @return A non-null tile texture object.
	 */
	public TileTexture getTexture() {
		return t;
	}
	
	/**
	 * Causes a world update at a tile of this type.
	 * @param x The X position of the tile.
	 * @param y The Y position of the tile.
	 * @param z The Z position of the tile.
	 * @param w The world the tile is in.
	 * @param state The state of the tile.
	 * @param wind The current wind value.
	 */
	public void update(long x, long y, long z, World w, String state, float wind) {
		
	}
	
	/**
	 * Causes a world update at a tile of this type.
	 * @param x The X position of the tile.
	 * @param y The Y position of the tile.
	 * @param z The Z position of the tile.
	 * @param w The world the tile is in.
	 * @param state The state of the tile.
	 */
	public void update(long x, long y, long z, World w, String state) {
		update(x, y, z, w, state, 0);
	}

	/**
	 * Gets the ID of this tile.
	 * @return A non-null string.
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Determines if this tile damages entities.
	 * @param entity_id The entity type.
	 * @return {@code true} if this tile can damage entities, {@code false} otherwise.
	 */
	public boolean isDamage(String entity_id) {
		return false;
	}
	
	/**
	 * Adds a tag to this tile.
	 * @param tag A non-null string.
	 */
	public void addTag(String tag) {
		tags.add(tag);
	}
	
	/**
	 * Determines if this tile has the specified tag.
	 * @param tag The tag to check for.
	 * @return {@code true} if this tile has the specified tag, {@code false} otherwise.
	 */
	public boolean hasTag(String tag) {
		for (String t : tags) {
			if(t.equals(tag)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the hitbox of this tile.
	 * @return A rectangle object.
	 */
	public Rectangle getHitBox() {
		return defaultBox;
	}
}

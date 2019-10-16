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
package io.github.tomaso2468.rpgonline.world2d.texture.entity;

import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.TextureMap;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.world2d.World;
import io.github.tomaso2468.rpgonline.world2d.entity.Entity;

/**
 * An interface for rendering entities. This is similar to {@code TileTexture}.
 * @author Tomaso2468
 *
 * @see io.github.tomaso2468.rpgonline.world2d.texture.TileTexture
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
	public default void render(Renderer renderer, double x, double y, double z, World w, Entity e, float sx, float sy, float wind) {
		Image img = TextureMap.getTexture(getTexture(x, y, z, w, e, wind));
		
		if (img == null) {
			return;
		}
		
		renderer.render(img, sx, sy, img.getWidth(), img.getHeight());
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

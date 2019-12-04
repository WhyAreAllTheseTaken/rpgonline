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
package io.github.tomaso2468.rpgonline.world2d.texture;

import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.world2d.Tile;
import io.github.tomaso2468.rpgonline.world2d.World;

/**
 * An interface representing a system for drawing tiles.
 * @author Tomaso2468
 * @see io.github.tomaso2468.rpgonline.world2d.texture.entity.EntityTexture
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
	public default void render(Game game, Renderer renderer, long x, long y, long z, World w, String state, Tile t, float sx, float sy, float wind) throws RenderException {
		Image img = game.getTextures().getTexture(getTexture(x, y, z, w, state, t));
		
		if (img != null) {
			renderer.render(img, sx, sy, img.getWidth(), img.getHeight());
		}
		
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

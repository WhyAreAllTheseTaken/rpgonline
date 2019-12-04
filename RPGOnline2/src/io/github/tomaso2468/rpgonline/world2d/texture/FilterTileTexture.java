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

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.world2d.Tile;
import io.github.tomaso2468.rpgonline.world2d.World;

/**
 * A tile texture that is filtered through a color based upon the hex value of their game state.
 * @author Tomaso2468
 */
public class FilterTileTexture extends BasicTileTexture {
	/**
	 * Constructs a new FilterTileTexture.
	 * @param s The texture ID.
	 * @param x The X offset of this texture.
	 * @param y The Y offset of this texture.
	 */
	public FilterTileTexture(Game game, String s, float x, float y) {
		super(game, s, x, y);
	}
	
	/**
	 * Constructs a new FilterTileTexture.
	 * @param s The texture ID.
	 */
	public FilterTileTexture(Game game, String s) {
		super(game, s, 0, 0);
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
	public void render(Game game, Renderer renderer, long x, long y, long z, World w, String state, Tile t, float sx, float sy,
			float wind) {
		Image img = game.getTextures().getTexture(getTexture(x, y, z, w, state, t));
		
		if (img != null) {
			renderer.renderFiltered(img, sx, sy, img.getWidth(), img.getHeight(), new Color(Integer.parseUnsignedInt(state, 16)));
		}
	}
}

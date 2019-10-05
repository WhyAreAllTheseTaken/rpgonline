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

import io.github.tomaso2468.rpgonline.TextureMap;
import io.github.tomaso2468.rpgonline.world2d.Tile;
import io.github.tomaso2468.rpgonline.world2d.World;

/**
 * An animated tile texture.
 * @author Tomaso2468
 *
 */
public class AnimatedTileTexture implements TileTexture {
	/**
	 * The textures to use.
	 */
	private final int[] textures;
	/**
	 * The interval between each frame in milliseconds.
	 */
	private final long interval;
	/**
	 * The X offset for this texture.
	 */
	private final float x;
	/**
	 * The Y offset for this texture.
	 */
	private final float y;
	
	/**
	 * Constructs a new AnimatedTileTexture.
	 * @param textures The textures to use.
	 * @param interval The time between each frame in milliseconds.
	 * @param x The X offset for this texture.
	 * @param y The Y offset for this texture.
	 */
	public AnimatedTileTexture(String[] textures, long interval, float x, float y) {
		super();
		this.textures = new int[textures.length];
		for (int i = 0; i < this.textures.length; i++) {
			this.textures[i] = TextureMap.getTextureIndex(textures[i]);
		}
		this.interval = interval;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructs a new AnimatedTileTexture.
	 * @param textures The textures to use.
	 * @param interval The time between each frame in milliseconds.
	 */
	public AnimatedTileTexture(String[] textures, long interval) {
		this(textures, interval, 0, 0);
	}
	
	/**
	 * Constructs a new AnimatedTileTexture.
	 * @param texture The texture prefix to use.
	 * @param length The length of the texture list.
	 * @param interval The time between each frame in milliseconds.
	 * @param x The X offset for this texture.
	 * @param y The Y offset for this texture.
	 */
	public AnimatedTileTexture(String texture, int length, long interval, float x, float y) {
		this(getTexturesFromIndex(texture, length), interval, x, y);
	}
	
	/**
	 * Constructs a new AnimatedTileTexture.
	 * @param texture The texture prefix to use.
	 * @param length The length of the texture list.
	 * @param interval The time between each frame in milliseconds.
	 */
	public AnimatedTileTexture(String texture, int length, long interval) {
		this(getTexturesFromIndex(texture, length), interval, 0, 0);
	}
	
	/**
	 * Gets the list of textures from the single texture prefix.
	 * @param texture The texture prefix.
	 * @param length The length of the texture list.
	 * @return An array of texture IDs.
	 */
	protected static String[] getTexturesFromIndex(String texture, int length) {
		String[] textures = new String[length];
		
		for (int i = 0; i < textures.length; i++) {
			textures[i] = texture + "." + i;
		}
		
		return textures;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture(long x, long y, long z, World w, String state, Tile t) {
		final int index = (int) (System.currentTimeMillis() / interval % textures.length);
		return textures[index];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getX() {
		return x;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getY() {
		return y;
	}

}

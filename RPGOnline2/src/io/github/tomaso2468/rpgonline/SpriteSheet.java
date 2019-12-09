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
package io.github.tomaso2468.rpgonline;

import io.github.tomaso2468.rpgonline.render.Renderer;

/**
 * A class holding an image to be used as a sprite sheet.
 * 
 * @author Tomaso2468
 *
 */
public class SpriteSheet extends Image {
	/**
	 * The width of each sprite.
	 */
	private int tw;
	/**
	 * The height of each sprite.
	 */
	private int th;
	/**
	 * The sprites in this sprite sheet.
	 */
	private Image[][] images;

	/**
	 * Constructs a new sprite sheet.
	 * 
	 * @param renderer The renderer to use for image operations.
	 * @param img      The image to use for this sprite sheet.
	 * @param tw       The width of each sprite.
	 * @param th       The height of each sprite.
	 */
	public SpriteSheet(Renderer renderer, Image img, int tw, int th) {
		super(renderer, img);
		this.tw = tw;
		this.th = th;
		images = new Image[getVerticalCount()][getHorizontalCount()];
		for (int y = 0; y < getVerticalCount(); y++) {
			for (int x = 0; x < getHorizontalCount(); x++) {
				images[y][x] = getSubImage(x, y);
			}
		}
	}

	/**
	 * Gets the number of sprites horizontally in this sprite sheet.
	 * 
	 * @return An int value.
	 */
	public int getHorizontalCount() {
		return (int) Math.ceil(getWidth() / tw);
	}

	/**
	 * Gets the number of sprites vertically in this sprite sheet.
	 * 
	 * @return An int value.
	 */
	public int getVerticalCount() {
		return (int) Math.ceil(getHeight() / th);
	}

	/**
	 * Gets the sprite at the specified position.
	 * 
	 * @param x The X position of the sprite.
	 * @param y The Y position of the sprite.
	 * @return An image.
	 */
	public Image getSprite(int x, int y) {
		return images[y][x];
	}

	/**
	 * Gets the sprite at the specified position as a new image.
	 * 
	 * @param x The X position of the sprite.
	 * @param y The Y position of the sprite.
	 * @return An image.
	 */
	public Image getSubImage(int x, int y) {
		return getSubImage(x * tw, y * th, tw, th);
	}

}

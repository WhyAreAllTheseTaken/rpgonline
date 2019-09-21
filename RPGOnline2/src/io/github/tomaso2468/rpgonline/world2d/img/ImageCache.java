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
package io.github.tomaso2468.rpgonline.world2d.img;

import java.awt.image.BufferedImage;

/**
 * A class that stores an image for use in {@code WorldFromImage}.
 * @author Tomas
 *
 * @see io.github.tomaso2468.rpgonline.world2d.img.WorldFromImage
 */
public class ImageCache {
	/**
	 * The image to cache.
	 */
	private final BufferedImage img;
	/**
	 * The time of the last access of this image.
	 */
	private long time = System.currentTimeMillis();
	/**
	 * The X position of the image.
	 */
	private final long x;
	/**
	 * The Y position of the image.
	 */
	private final long y;

	/**
	 * Constructs a new ImageCache
	 * @param img The image to cache.
	 * @param x The X position of the image.
	 * @param y The Y position of the image.
	 */
	public ImageCache(BufferedImage img, long x, long y) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the image held in this cache.
	 * @return A buffered image object.
	 */
	public BufferedImage getImage() {
		time = System.currentTimeMillis();
		return img;
	}

	/**
	 * Determines if this cache is expired.
	 * @return
	 */
	public boolean isExpired() {
		return System.currentTimeMillis() - time > 1000 * 60 * 10;
	}

	/**
	 * Gets the X position of this image.
	 * @return A long value.
	 */
	public long getX() {
		return x;
	}

	/**
	 * Gets the Y position of this image.
	 * @return A long value.
	 */
	public long getY() {
		return y;
	}
}

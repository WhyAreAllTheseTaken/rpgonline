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
package io.github.tomaso2468.rpgonline.sky;

import org.newdawn.slick.Image;

/**
 * A sky layer mesh made of a top, middle and bottom image.
 * @author Tomaso2468
 *
 */
public class ThreeImageMesh extends ImageMeshLayer {
	/**
	 * The top image.
	 */
	private final Image top;
	/**
	 * The middle image.
	 */
	private final Image middle;
	/**
	 * The bottom image.
	 */
	private final Image bottom;
	
	/**
	 * Constructs a new ThreeImageMesh
	 * @param imageWidth The width of one image.
	 * @param imageHeight The height of one image.
	 * @param top The top image.
	 * @param middle The middle image.
	 * @param bottom The bottom image.
	 */
	public ThreeImageMesh(int imageWidth, int imageHeight, Image top, Image middle, Image bottom) {
		super(imageWidth, imageHeight);
		this.top = top;
		this.middle = middle;
		this.bottom = bottom;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getImageAt(long x, long y) {
		if (y < 0) {
			return top;
		} else if (y == 0) {
			return middle;
		} else {
			return bottom;
		}
	}

}

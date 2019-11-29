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

import org.lwjgl.opengl.GL11;

/**
 * An enum of image filtering modes.
 * @author Tomaso2468
 *
 */
public enum ImageFilter {
	/**
	 * A filter that uses nearest neighbour interpolation.
	 */
	NEAREST(GL11.GL_NEAREST, org.newdawn.slick.Image.FILTER_NEAREST),
	/**
	 * A filter that uses linear interpolation.
	 */
	LINEAR(GL11.GL_LINEAR, org.newdawn.slick.Image.FILTER_LINEAR),
	;
	/**
	 * The opengl constant for this filtering method.
	 */
	public final int glMapping;
	/**
	 * The slick2d constant for this filtering method.
	 */
	public final int slickMapping;
	/**
	 * Construct enum value.
	 * @param glMapping opengl
	 * @param slickMapping slick2d
	 */
	private ImageFilter(int glMapping, int slickMapping) {
		this.glMapping = glMapping;
		this.slickMapping = slickMapping;
	}
	
}

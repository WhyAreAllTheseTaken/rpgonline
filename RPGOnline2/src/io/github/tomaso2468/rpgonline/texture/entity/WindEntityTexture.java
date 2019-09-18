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
package io.github.tomaso2468.rpgonline.texture.entity;

import org.apache.commons.math3.util.FastMath;

import io.github.tomaso2468.rpgonline.entity.Entity;
import io.github.tomaso2468.rpgonline.texture.TextureMap;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * An entity texture that changes depending on wind.
 * @author Tomas
 *
 */
public class WindEntityTexture implements EntityTexture {
	/**
	 * The textures to use.
	 */
	private final int[] t;
	/**
	 * The points at which the texture should be changed.
	 */
	private final float[] b;
	/**
	 * The X offset for this texture.
	 */
	private final float x;
	/**
	 * The Y offset for this texture.
	 */
	private final float y;
	
	/**
	 * Constructs a new WindEntityTexture.
	 * @param s The textures to use.
	 * @param b The values to change the texture at.
	 * @param x The X offset for this texture.
	 * @param y The Y offset for this texture.
	 */
	public WindEntityTexture(String[] s, float[] b, float x, float y) {
		t = new int[s.length];
		
		for (int i = 0; i < s.length; i++) {
			t[i] = TextureMap.getTextureIndex(s[i]);
		}
		
		this.x = x;
		this.y = y;
		
		this.b = b;
	}
	
	/**
	 * Constructs a new WindEntityTexture.
	 * @param s The textures to use.
	 * @param b The values to change the texture at.
	 */
	public WindEntityTexture(String[] s, float[] b) {
		this(s, b, 0, 0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		if(wind == 0) {
			return t[0];
		}
		float wind2 = FastMath.abs(wind);
		
		int texture = t[0];
		for (int i = 0; i < b.length; i++) {
			if(wind2 >= b[i]) {
				texture = t[i];
			}
		}
		
		return texture;
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

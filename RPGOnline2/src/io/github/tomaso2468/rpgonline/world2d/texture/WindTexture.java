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

import org.apache.commons.math3.util.FastMath;

import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.RPGConfig;
import io.github.tomaso2468.rpgonline.TextureMap;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.world2d.Tile;
import io.github.tomaso2468.rpgonline.world2d.World;

/**
 * A tile texture that is affected by wind.
 * @author Tomaso2468
 */
public class WindTexture extends BasicTileTexture {
	/**
	 * The strength of the wind effect.
	 */
	private float f;
	/**
	 * Constructs a new WindTexture.
	 * @param s The texture ID.
	 * @param x The X offset of the texture.
	 * @param y The Y offset of the texture.
	 * @param f The strength of the wind effect.
	 */
	public WindTexture(String s, float x, float y, float f) {
		super(s, x, y);
		this.f = f;
	}
	
	/**
	 * Constructs a new WindTexture.
	 * @param s The texture ID.
	 * @param f The strength of the wind effect.
	 */
	public WindTexture(String s, float f) {
		this(s, 0, 0, f);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCustom() {
		return RPGConfig.isWind();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Renderer renderer, long x, long y, long z, World w, String state, Tile t, float sx, float sy, float wind) {
		float amount = (float) wibble((x * y + (System.currentTimeMillis() / 50)) * (double) f) * wind
				* f;
		
		Image img = TextureMap.getTexture(getTexture(x, y, z, w, state, t));
		
		if (img != null) {
			renderer.renderSheared(img, sx - amount, sy, img.getWidth(), img.getHeight(), amount, 0);
		}
	}
	
	/**
	 * A function that computes the wind effect.
	 * @param v The value for wind.
	 * @return A double value.
	 */
	public static double wibble(double v) {
		double a = v % 50 + FastMath.log10(v);
		double b = FastMath.sin(a) + FastMath.sin(FastMath.cbrt(a)) * 2 + (FastMath.cos(2 * a) / 3)
				+ (FastMath.sin(-a) / 2) + (FastMath.tanh(a / 2)) / 3;
		return (FastMath.abs(b) / 2.5 - 1 + FastMath.sin(v / 4) * 4) / 8;
	}
}

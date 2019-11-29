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
package io.github.tomaso2468.rpgonline.render;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.post.LUT;

public class BasicLUT implements LUT {
	public final int size;
	public final float[][][] data_r;
	public final float[][][] data_g;
	public final float[][][] data_b;

	public BasicLUT(int size, float[][][] data_r, float[][][] data_g, float[][][] data_b) {
		super();
		this.size = size;
		this.data_r = data_r;
		this.data_g = data_g;
		this.data_b = data_b;
	}

	public BasicLUT(int size, float[][][][] data) {
		this(size, data[0], data[1], data[2]);
	}

	@Override
	public Color apply(Color in) {
		return new Color(lookupRed(in.r, in.g, in.b), lookupGreen(in.r, in.g, in.b), lookupBlue(in.r, in.g, in.b));
	}

	protected float clamp(float x) {
		if (x < 0) {
			return 0;
		}
		if (x > 1) {
			return 1;
		}
		return x;
	}

	protected int clampToVoxel(int x) {
		if (x < 0) {
			return 0;
		}
		if (x > size - 1) {
			return size - 1;
		}
		return x;
	}

	protected float lerp(float a, float b, float x) {
		return a * (1 - x) + b * x;
	}

	protected float lookup(int r, int g, int b, float[][][] data) {
		return data[clampToVoxel(r)][clampToVoxel(g)][clampToVoxel(b)];
	}

	protected float dist(float r, float g, float b, float r2, float g2, float b2) {
		return (float) FastMath.sqrt((r - r2) * (r - r2) + (g - g2) * (g - g2) + (b - b2) * (b - b2));
	}

	protected float lookup(float r, float g, float b, float[][][] data) {
		float v1 = lookup((int) FastMath.floor(r * size), (int) FastMath.floor(g * size),
				(int) FastMath.floor(b * size), data);
		float v2 = lookup((int) FastMath.ceil(r * size), (int) FastMath.ceil(g * size), (int) FastMath.ceil(b * size),
				data);

		float d = dist(r * size, g * size, b * size, (int) FastMath.floor(r * size), (int) FastMath.floor(g * size),
				(int) FastMath.floor(b * size));

		return lerp(v1, v2, d);
	}

	@Override
	public float lookupRed(float r, float g, float b) {
		return lookup(r, g, b, data_r);
	}

	@Override
	public float lookupGreen(float r, float g, float b) {
		return lookup(r, g, b, data_g);
	}

	@Override
	public float lookupBlue(float r, float g, float b) {
		return lookup(r, g, b, data_b);
	}

	@Override
	public void bindToShader(Shader shader, String var) {
		throw new UnsupportedOperationException("bindToShader");
	}

}

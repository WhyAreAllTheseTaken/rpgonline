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
package io.github.tomaso2468.rpgonline.post.pack;

import io.github.tomaso2468.rpgonline.post.ColorEffectsShader;
import io.github.tomaso2468.rpgonline.post.DynamicHeatShader2;
import io.github.tomaso2468.rpgonline.post.FragmentExpose;
import io.github.tomaso2468.rpgonline.post.MotionBlur;
import io.github.tomaso2468.rpgonline.post.MultiEffect;

/**
 * A high quality shader pack for high end devices.
 * 
 * @author Tomas
 */
@Deprecated
public class ExtremeShaderPack extends MultiEffect {
	/**
	 * Create the shader pack.
	 */
	public ExtremeShaderPack() {
		//super(new DynamicHeatShader2("get heat"), new FragmentExpose(), new EdgeResample(), new ColorBoostEffect(), new MotionBlur(0.35f));
		super(new DynamicHeatShader2(), new FragmentExpose(), new MotionBlur(0.35f), new ColorEffectsShader(1f /*s*/, 0f /*b*/, 1.25f /*c*/, 1.25f /*v*/, 0f /*h*/, 0.8f /*g*/));
	}
}
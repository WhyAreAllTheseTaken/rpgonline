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
package io.github.tomaso2468.rpgonline.post;

import io.github.tomaso2468.rpgonline.Game;
import slickshader.Shader;

/**
 * A shader that effects the colours on the screen.
 * 
 * @author Tomaso2468
 *
 * @see io.github.tomaso2468.rpgonline.post.SaturateShader
 * @see io.github.tomaso2468.rpgonline.post.BrightnessEffect
 * @see io.github.tomaso2468.rpgonline.post.ContrastEffect
 * @see io.github.tomaso2468.rpgonline.post.VibranceEffect
 * @see io.github.tomaso2468.rpgonline.post.HueShiftEffect
 * @see io.github.tomaso2468.rpgonline.post.GammaEffect
 */
public class ColorEffectsShader extends GLShaderEffect {
	/**
	 * A value that increases the saturation of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.SaturateShader
	 */
	public float saturation;
	/**
	 * A value that adjusts the brightness of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.BrightnessEffect
	 */
	public float brightness;
	/**
	 * A value that changes the contrast of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.ContrastEffect
	 */
	public float contrast;
	/**
	 * A value that changes the vibrance of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.VibranceEffect
	 */
	public float vibrance;
	/**
	 * A value that changes the hue of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.HueShiftEffect
	 */
	public float hue;
	/**
	 * A value that changes the gamma of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.GammaEffect
	 */
	public float gamma;

	/**
	 * Constructs a new ColorEffectsShader.
	 * 
	 * @param saturation A value that increases the saturation of the image.
	 * @param brightness A value that adjusts the brightness of the image.
	 * @param contrast A value that changes the contrast of the image.
	 * @param vibrance A value that changes the vibrance of the image.
	 * @param hue A value that changes the hue of the image.
	 * @param gamma A value that changes the gamma of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.SaturateShader
	 * @see io.github.tomaso2468.rpgonline.post.BrightnessEffect
	 * @see io.github.tomaso2468.rpgonline.post.ContrastEffect
	 * @see io.github.tomaso2468.rpgonline.post.VibranceEffect
	 * @see io.github.tomaso2468.rpgonline.post.HueShiftEffect
	 * @see io.github.tomaso2468.rpgonline.post.GammaEffect
	 */
	public ColorEffectsShader(float saturation, float brightness, float contrast, float vibrance, float hue,
			float gamma) {
		super(ColorEffectsShader.class.getResource("/generic.vrt"),
				ColorEffectsShader.class.getResource("/colorpost.frg"));
		this.saturation = saturation;
		this.brightness = brightness;
		this.contrast = contrast;
		this.vibrance = vibrance;
		this.hue = hue;
		this.gamma = gamma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, Game c) {
		shader.setUniformFloatVariable("saturation", saturation);
		shader.setUniformFloatVariable("brightness", brightness);
		shader.setUniformFloatVariable("contrast", contrast);
		shader.setUniformFloatVariable("_vibrance", vibrance);
		shader.setUniformFloatVariable("hueShift", hue);
		shader.setUniformFloatVariable("gamma", gamma);
	}
}

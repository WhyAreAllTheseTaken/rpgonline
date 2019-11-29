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

import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.render.Shader;

public class HDRPost extends ShaderPost {
	private float exposure_last = Float.NaN;
	private float exposure = 1f;
	private float gamma_last = Float.NaN;
	private float gamma = 1.2f;
	
	private float curve_angle_last = Float.NaN;
	private float curve_angle = 1f;
	private float curve_exponent_last = Float.NaN;
	private float curve_exponent = -1f;
	private float contrast_last = Float.NaN;
	private float contrast = 1.4f;
	private float white_last = Float.NaN;
	private float white = 1f;
	
	public HDRPost() {
		super(HDRPost.class.getResource("/generic.vrt"), HDRPost.class.getResource("/hdr.frg"));
	}
	
	@Override
	public void updateShader(Shader shader, Renderer renderer) throws RenderException {
		super.updateShader(shader, renderer);
		if (exposure != exposure_last) {
			shader.setUniform("exposure", exposure);
			this.exposure_last = exposure;
		}
		if (gamma != gamma_last) {
			shader.setUniform("gamma", gamma);
			this.gamma_last = gamma;
		}
		
		if (curve_angle != curve_angle_last) {
			shader.setUniform("curve_angle", curve_angle);
			this.curve_angle_last = curve_angle;
		}
		if (curve_exponent != curve_exponent_last) {
			shader.setUniform("curve_exponent", curve_exponent);
			this.curve_exponent_last = curve_exponent;
		}
		if (contrast != contrast_last) {
			shader.setUniform("contrast", contrast);
			this.contrast_last = contrast;
		}
		if (white != white_last) {
			shader.setUniform("white_point", white);
			this.white_last = white;
		}
		shader.setUniform("screenScale", 1, (float) renderer.getWidth() / renderer.getHeight());
	}

	public float getExposure() {
		return exposure;
	}

	public void setExposure(float exposure) {
		this.exposure = exposure;
	}

	public float getGamma() {
		return gamma;
	}

	public void setGamma(float gamma) {
		this.gamma = gamma;
	}

	public float getCurveAngle() {
		return curve_angle;
	}

	public void setCurveAngle(float curve_angle) {
		this.curve_angle = curve_angle;
	}

	public float getCurveExponent() {
		return curve_exponent;
	}

	public void setCurveExponent(float curve_exponent) {
		this.curve_exponent = curve_exponent;
	}

	public float getContrast() {
		return contrast;
	}

	public void setContrast(float contrast) {
		this.contrast = contrast;
	}

	public float getWhite() {
		return white;
	}

	public void setWhite(float white) {
		this.white = white;
	}
}

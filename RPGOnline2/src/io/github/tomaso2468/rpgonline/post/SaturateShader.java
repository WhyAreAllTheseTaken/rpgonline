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
import io.github.tomaso2468.rpgonline.render.Shader;

/**
 * An effect that changes the saturation of the screen.
 * <strong>It may be best to use VibranceEffect instead as it offers better results for improving colours.</strong>
 * @author Tomaso2468
 *
 */
public class SaturateShader extends GLShaderEffect {
	/**
	 * The saturation effect factor.
	 * <ul>
	 * <li>0 - Grayscale</li>
	 * <li>0..1 - Desaturated</li>
	 * <li>1 - Normal</li>
	 * <li>1.. - Saturated</li>
	 * <ul>
	 */
	private float sat;
	
	/**
	 * Constructs a new SaturateShader. 
	 * <ul>
	 * <li>0 - Grayscale</li>
	 * <li>0..1 - Desaturated</li>
	 * <li>1 - Normal</li>
	 * <li>1.. - Saturated</li>
	 * <ul>
	 * @param sat The saturation effect factor.
	 */
	public SaturateShader(float sat) {
		super(SaturateShader.class.getResource("/generic.vrt"), SaturateShader.class.getResource("/saturate.frg"));
		this.sat = sat;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, Game c) {
		super.updateShader(shader, c);
		shader.setUniform("saturation", sat);
	}
}

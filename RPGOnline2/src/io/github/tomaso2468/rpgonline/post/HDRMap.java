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
 * An effect for mapping tones in MDR rendering. <b>This is set automatically when MDR is enabled.</b>
 * @author Tomaso2468
 *
 */
public class HDRMap extends GLShaderEffect {
	/**
	 * The exposure of the MDR tone map.
	 */
	private float exposure = 1f;
	/**
	 * The gamma of the screen.
	 */
	private float gamma = 1.2f;
	
	/**
	 * Constructs a new MDRMap with an exposure of 1 for a monitor with a gamma of 1.2.
	 */
	public HDRMap() {
		super(HDRMap.class.getResource("/mdr.frg"));
	}

	/**
	 * Constructs a new MDRMap.
	 * @param exposure The exposure.
	 * @param gamma The gamma of the monitor (normally 1.2).
	 */
	public HDRMap(float exposure, float gamma) {
		super(HDRMap.class.getResource("/mdr.frg"));
		this.exposure = exposure;
		this.gamma = gamma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initShader(Shader shader, Game c) {
		super.initShader(shader, c);
		shader.setUniform("exposure", exposure);
		shader.setUniform("gamma", gamma);
	}
}

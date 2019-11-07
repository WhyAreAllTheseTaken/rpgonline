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
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.render.Shader;

/**
 * <p>A 2 pass gaussian blur effect.</p>
 * 
 * @author Tomaso2468
 */
public class Blur implements PostEffect {
	/**
	 * Vertical shader.
	 */
	private Shader shader;
	/**
	 * Horizontal shader.
	 */
	private Shader shader2;
	/**
	 * Size of the blur.
	 */
	public int size;
	/**
	 * Deviation of the blur.
	 */
	public float sigma;
	
	/**
	 * Constructs a new blur effect.
	 * @param size The size of the blur.
	 * @param sigma The deviation of the blur.
	 */
	public Blur(int size, float sigma) {
		super();
		this.size = size;
		this.sigma = sigma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(Game game, Image buffer, Renderer renderer)
			throws RenderException {
		buffer.setFilter(Image.FILTER_LINEAR);
		
		//XXX Two files needed because uniforms wouldn't change.
		if (shader == null) {
			shader = renderer.createShader(Blur.class.getResource("/generic.vrt"), Blur.class.getResource("/blurV.frg"));
		}
		if (shader2 == null) {
			shader2 = renderer.createShader(Blur.class.getResource("/generic.vrt"), Blur.class.getResource("/blurH.frg"));
		}
		
		renderer.useShader(shader);
		shader.setUniform("blurSize", size);
		shader.setUniform("sigma", sigma);
		
		// vertical pass
		renderer.drawImage(buffer, 0, 0);
		
		//TODO Is this needed?
		renderer.useShader(null);
		
		renderer.copyArea(buffer, 0, 0);
		
		renderer.useShader(shader);
		shader.setUniform("blurSize", size);
		shader.setUniform("sigma", sigma);
		
		// horizontal pass
		renderer.drawImage(buffer, 0, 0);
		renderer.useShader(null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose(Renderer renderer) throws RenderException {
		renderer.deleteShader(shader);
		renderer.deleteShader(shader2);
	}
}

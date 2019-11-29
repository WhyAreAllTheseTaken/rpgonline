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

import java.net.URL;

import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.render.Shader;

public class ShaderPost implements PostProcessing {
	protected Shader shader;
	private URL vertex;
	private URL fragment;
	
	public ShaderPost(URL vertex, URL fragment) {
		this.vertex = vertex;
		this.fragment = fragment;
	}
	
	public ShaderPost(Shader shader) {
		this.shader = shader;
	}

	@Override
	public void postProcess(Image input, Image output, Renderer renderer) throws RenderException {
		if (shader == null) {
			shader = renderer.createShader(vertex, fragment);
			renderer.useShader(shader);
			initShader(shader, renderer);
		} else {
			renderer.useShader(shader);
		}
		updateShader(shader, renderer);
		
		preDraw(shader, renderer);
		renderer.drawImage(input, 0, 0);
		postDraw(shader, renderer);
		
		renderer.useShader(null);
	}
	
	public void initShader(Shader shader, Renderer renderer) throws RenderException {
		
	}
	
	public void updateShader(Shader shader, Renderer renderer) throws RenderException {
		
	}
	
	public void preDraw(Shader shader, Renderer renderer) throws RenderException {
		
	}
	
	public void postDraw(Shader shader, Renderer renderer) throws RenderException {
		
	}
}

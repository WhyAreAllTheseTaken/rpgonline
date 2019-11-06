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

/**
 * An effect that causes bright areas of the screen to bleed.
 * @author Tomaso2468
 * 
 * @see io.github.tomaso2468.rpgonline.post.Blur
 * @see io.github.tomaso2468.rpgonline.post.HighPass
 */
public class Bloom extends MultiEffect {
	/**
	 * A second buffer used for storing data.
	 */
	private Image buffer2;
	
	/**
	 * Constructs a new bloom effect.
	 * @param value The threshold for the bloom effect.
	 * @param blur The amount to blur by.
	 * @param sigma used by the blur effect.
	 */
	public Bloom(float value, int blur, float sigma) {
		super(new HighPass(value), new Blur(blur, sigma));
	}
	/**
	 * Constructs a new bloom effect.
	 * @param value The threshold for the bloom effect.
	 */
	public Bloom(float value) {
		this(value, 5, 2);
	}
	
	/**
	 * Constructs a new bloom effect with a threshold of 0.95f.
	 */
	public Bloom() {
		this(0.95f, 5, 2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(Game game, Image buffer, Renderer renderer)
			throws RenderException {
		if (buffer2 == null) {
			buffer2 = new Image(renderer, game.getWidth(), game.getHeight());
		} else if (game.getWidth() != buffer2.getWidth() || game.getHeight() != buffer2.getHeight()) {
			buffer2.destroy();
			buffer2 = new Image(renderer, game.getWidth(), game.getHeight());
		}
		
		Image target = renderer.getCurrentTarget();
		
		renderer.setRenderTarget(buffer2);
		renderer.clear();
		
		renderer.drawImage(buffer, 0, 0);
		
		renderer.setRenderTarget(target);
		
		super.doPostProcess(game, buffer, renderer);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose(Renderer renderer) throws RenderException {
		super.dispose(renderer);
		buffer2.destroy();
	}
}

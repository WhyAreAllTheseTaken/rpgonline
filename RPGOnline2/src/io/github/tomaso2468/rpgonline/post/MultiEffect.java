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
import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;

/**
 * Allows for the chaining of multiple effects together.
 * 
 * @author Tomaso2468
 */
public class MultiEffect implements PostEffect {
	/**
	 * An array of effects.
	 */
	private final PostEffect[] effects;

	/**
	 * Create a {@code MultiEffect} object
	 * 
	 * @param effects The effects to apply in order.
	 */
	public MultiEffect(PostEffect... effects) {
		super();
		this.effects = effects;
	}

	private Image buffer2;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(Game game, Image buffer, Renderer renderer)
			throws RenderException {
		Image current_buffer = renderer.getCurrentTarget();
		
		if (buffer2 == null) {
			buffer2 = new Image(renderer, game.getWidth(), game.getHeight());
		} else if (game.getWidth() != buffer2.getWidth() || game.getHeight() != buffer2.getHeight()) {
			buffer2.destroy();
			buffer2 = new Image(renderer, game.getWidth(), game.getHeight());
		}
		renderer.setRenderTarget(buffer2);
		
		if (effects.length == 0) {
			NullPostProcessEffect.INSTANCE.doPostProcess(game, buffer, renderer);
			Image buffer_swap = buffer;
			buffer = buffer2;
			buffer2 = buffer_swap;
		}
		
		for (PostEffect e : effects) {
			if (!(e instanceof MultiEffect)) {
				Debugger.start("post-" + e.getClass());
			}
			e.doPostProcess(game, buffer, renderer);

			renderer.resetTransform();
			
			if (!(e instanceof MultiEffect)) {
				Debugger.stop("post-" + e.getClass());
			}
			
			Image buffer_swap = buffer;
			buffer = buffer2;
			buffer2 = buffer_swap;
			
			renderer.setRenderTarget(buffer2);
		}
		
		renderer.setRenderTarget(current_buffer);
		renderer.drawImage(buffer, 0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose(Renderer renderer) throws RenderException {
		for (PostEffect e : effects) {
			e.dispose(renderer);
		}
	}

}

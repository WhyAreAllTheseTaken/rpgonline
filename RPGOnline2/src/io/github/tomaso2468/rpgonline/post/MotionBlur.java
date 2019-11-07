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

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;

/**
 * An effect that provides a motion blur.
 * 
 * @author Tomaso2468
 */
public class MotionBlur implements PostEffect {
	/**
	 * The previous frame.
	 */
	private Image last;

	/**
	 * The alpha value of the previous frame.
	 */
	public float amount;

	/**
	 * Creates a {@code MotionBlur} effect.
	 * 
	 * @param amount The alpha value of the previous frame.
	 */
	public MotionBlur(float amount) {
		this.amount = amount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(Game game, Image buffer, Renderer renderer) 
			throws RenderException {
		renderer.drawImage(buffer, 0, 0);

		if (last == null) {
			last = new Image(renderer, (int) buffer.getWidth(), (int) buffer.getHeight());
		}
		if (game.getWidth() != last.getWidth() || game.getHeight() != last.getHeight()) {
			last.destroy();
			last = new Image(renderer, (int) game.getWidth(), (int) game.getHeight());
		}

		renderer.renderFiltered(last, 0, 0, last.getWidth(), last.getHeight(), new Color(1, 1, 1, amount * game.getFPS()));

		renderer.copyArea(last, 0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose(Renderer renderer) throws RenderException {
		if (last != null) {
			last.destroy();
		}
	}
}

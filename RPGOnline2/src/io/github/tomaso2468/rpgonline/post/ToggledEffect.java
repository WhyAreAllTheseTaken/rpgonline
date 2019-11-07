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
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;

/**
 * An effect that can be toggled on and off.
 * @author Tomaso2468
 */
public class ToggledEffect extends MultiEffect {
	/**
	 * The toggled state of this effect.
	 */
	private boolean state;
	/**
	 * The effect to toggle.
	 */
	private PostEffect e;

	/**
	 * Constructs a new ToggledEffect with an initial state.
	 * @param e The effect to toggle.
	 * @param state The initial state of this effect.
	 */
	public ToggledEffect(PostEffect e, boolean state) {
		super(e);
		this.state = state;
		this.e = e;
	}
	
	/**
	 * Constructs a new ToggledEffect.
	 * @param e The effect to toggle.
	 */
	public ToggledEffect(PostEffect e) {
		this(e, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(Game game, Image buffer, Renderer renderer) 
			throws RenderException {
		if (state) {
			super.doPostProcess(game, buffer, renderer);
		} else {
			NullPostProcessEffect.INSTANCE.doPostProcess(game, buffer, renderer);
		}
	}

	/**
	 * Gets the state of this effect.
	 * @return {@code true} if this effect is active, {@code false} otherwise.
	 */
	public boolean isState() {
		return state;
	}

	/**
	 * Sets the state of this effect.
	 * @param state {@code true} if this effect is active, {@code false} otherwise.
	 */
	public void setState(boolean state) {
		this.state = state;
	}
	
	/**
	 * Gets the effect that is toggled.
	 * @return A post effect instance.
	 */
	public PostEffect getEffect() {
		return e;
	}
	
	/**
	 * Sets the effect to toggle.
	 * @param e A non-null post effect.
	 */
	public void setEffect(PostEffect e) {
		this.e = e;
	}

}

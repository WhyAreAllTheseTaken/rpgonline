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
package io.github.tomaso2468.rpgonline.cutscene;

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.GameState;
import io.github.tomaso2468.rpgonline.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.transition.FadeInTransition;
import io.github.tomaso2468.rpgonline.transition.FadeOutTransition;
import io.github.tomaso2468.rpgonline.transition.Transition;

/**
 * A state that holds cutscene information.
 * @author Tomaso2468
 */
public abstract class CutsceneState implements GameState {
	/**
	 * The ID of the state.
	 */
	private final int id;
	/**
	 * The ID of the state to switch to after the cutscene is finished.
	 */
	private final int nextState;
	/**
	 * The cutscene to play.
	 */
	private final Cutscene scene;
	/**
	 * The transition to play when leaving this state.
	 */
	private final Transition leave;
	/**
	 * The transition to play when entering the next state.
	 */
	private final Transition enter;
	/**
	 * Constructs a new Cutscene state.
	 * @param id The ID of the state.
	 * @param nextState The next state ID to switch to after the cutscene plays.
	 * @param scene The scene to play.
	 * @param leave The transition to play when leaving this state.
	 * @param enter The transition to play when entering the next state.
	 */
	public CutsceneState(int id, int nextState, Cutscene scene, Transition leave, Transition enter) {
		super();
		this.id = id;
		this.nextState = nextState;
		this.scene = scene;
		this.leave = leave;
		this.enter = enter;
	}
	/**
	 * Constructs a new Cutscene state.
	 * @param id The ID of the state.
	 * @param nextState The next state ID to switch to after the cutscene plays.
	 * @param scene The scene to play.
	 * @param c The color of the transition screen.
	 */
	public CutsceneState(int id, int nextState, Cutscene scene, Color c) {
		this(id, nextState, scene, new FadeOutTransition(c, 0.5f), new FadeInTransition(c, 0.05f));
	}
	/**
	 * Constructs a new Cutscene state.
	 * @param id The ID of the state.
	 * @param nextState The next state ID to switch to after the cutscene plays.
	 * @param scene The scene to play.
	 */
	public CutsceneState(int id, int nextState, Cutscene scene) {
		this(id, nextState, scene, Color.black);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(Game game) throws RenderException {
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Game game, Renderer renderer) throws RenderException {
		scene.render(game, renderer, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Game game, float delta) throws RenderException {
		scene.update(delta);
		if (scene.isDone()) {
			game.changeState(nextState, leave, enter);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enterState(Game game) throws RenderException {
		scene.reset();
		scene.onStart();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getID() {
		return id;
	}

}

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

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.audio.AmbientMusic;
import io.github.tomaso2468.rpgonline.render.Renderer;

/**
 * A cutscene type that allows multiple cutscenes to be combined.
 * @author Tomaso2468
 *
 */
public class MultiCutscene extends Cutscene {
	/**
	 * The array of scenes.
	 */
	private final Cutscene[] scenes;
	/**
	 * The current cutscene index.
	 */
	private int index = 0;
	/**
	 * The previous game updates cutscene index.
	 */
	private int lastIndex = -1;
	/**
	 * The previously playing music.
	 */
	private AmbientMusic lastMusic = null;
	/**
	 * Constructs a new MultiCutscene.
	 * @param scenes The array of cutscenes.
	 */
	public MultiCutscene(Cutscene[] scenes) {
		super(1);
		this.scenes = scenes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Game game, Renderer renderer, CutsceneState state) {
		if (index < scenes.length) {
			scenes[index].render(game, renderer, state);
		}
		
		renderer.resetTransform();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDone() {
		return index == scenes.length;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		lastIndex = -1;
		index = 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float delf) {
		if (lastIndex != index) {
			if (index < scenes.length) {
				scenes[index].onStart();
			}
		}
		if (index < scenes.length) {
			scenes[index].update(delf);
			
			lastMusic = scenes[index].getMusic();
			
			if (scenes[index].isDone()) {
				index += 1;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AmbientMusic getMusic() {
		return lastMusic;
	}

}

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
import io.github.tomaso2468.rpgonline.audio.AudioManager;
import io.github.tomaso2468.rpgonline.render.Renderer;

/**
 * An abstract class representing a cutscene.
 * @author Tomaso2468
 */
public abstract class Cutscene {
	/**
	 * The time left for the cutscene in seconds.
	 */
	protected float time;
	/**
	 * The total length of the cutscene.
	 */
	protected final float base_time;
	
	/**
	 * Constructs a new cutscene.
	 * @param time The total length of the cutscene.
	 */
	public Cutscene(float time) {
		this.base_time = time;
		this.time = time;
	}
	
	/**
	 * Determines if the cutscene has finished playing.
	 * @return {@code true} if the cutscene is finished, {@code false} otherwise.
	 */
	public boolean isDone() {
		return time <= 0;
	}
	/**
	 * Resets the cutscene to the beginning. This should not be used to determine the start of the cutscene.
	 */
	public void reset() {
		time = base_time;
	}
	/**
	 * Updates the cutscene.
	 * @param delf The time since the last game update in seconds.
	 */
	public void update(float delf) {
		time -= delf;
	}
	/**
	 * Renders the cutscene/
	 * @param c The current game container.
	 * @param game The current game.
	 * @param g The current graphics context.
	 * @param state The current game state.
	 */
	public abstract void render(Game game, Renderer renderer, CutsceneState state);
	/**
	 * Gets the music for this cutscene.
	 * @return An ambient music object.
	 */
	public abstract AmbientMusic getMusic();
	/**
	 * Called at the start of a cutscene.
	 */
	public void onStart() {
		AudioManager.setMusic(getMusic());
	}
}

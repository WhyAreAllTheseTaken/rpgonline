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
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.audio.AmbientMusic;
import io.github.tomaso2468.rpgonline.render.Renderer;

/**
 * A cutscene that displays a static texture.
 * @author Tomaso2468
 */
public class TextureCutscene extends Cutscene {
	/**
	 * The texture ID.
	 */
	private final String texture;
	/**
	 * The music ID.
	 */
	private final String music;
	/**
	 * Constructs a new TextureCutscene.
	 * @param time The length of the cutscene in seconds.
	 * @param texture The texture ID to display.
	 * @param music The music to play.
	 */
	public TextureCutscene(float time, String texture, String music) {
		super(time);
		this.texture = texture;
		this.music = music;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Game game, Renderer renderer, CutsceneState state) {
		Image img = game.getTextures().getTexture(texture).getScaledCopy(renderer.getWidth(), renderer.getHeight());
		renderer.render(img, 0, 0, img.getWidth(), img.getHeight());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AmbientMusic getMusic(Game game) {
		return game.getAudio().getAmbientMusic(music);
	}

}

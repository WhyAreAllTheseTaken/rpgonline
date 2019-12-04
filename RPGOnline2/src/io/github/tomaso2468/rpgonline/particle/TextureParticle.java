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
package io.github.tomaso2468.rpgonline.particle;

import java.util.List;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.world2d.World;

/**
 * A particle with a texture.
 * @author Tomaso2468
 *
 */
public class TextureParticle implements Particle {
	/**
	 * The X position of the particle.
	 */
	protected float x;
	/**
	 * The Y position of the particle.
	 */
	protected float y;
	/**
	 * The texture of the particle.
	 */
	protected int t;
	/**
	 * The time the particle has left on the screen.
	 */
	protected float time;
	/**
	 * The maximum time of this particle.
	 */
	private final float otime;
	
	/**
	 * Constructs a new TextureParticle.
	 * @param s The texture of the particle.
	 * @param x The X position of the particle.
	 * @param y The Y position of the particle.
	 * @param time The time the particle has left on the screen.
	 */
	public TextureParticle(Game game, String s, float x, float y, float time) {
		this.t = game.getTextures().getTextureIndex(s);
		this.x = x;
		this.y = y;
		this.time = time;
		this.otime = time;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture() {
		return t;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getX() {
		return x;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getY() {
		return y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doBehaviour(World w, float wind, List<Particle> particles, float delta) {
		time -= delta;
		
		if (time <= 0) {
			particles.remove(this);
			return;
		}
		
		x += wind * delta;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getAlpha() {
		if (time > otime - 1f) {
			return 1 - (time - (otime - 1f));
		}
		return time / otime;
	}

}

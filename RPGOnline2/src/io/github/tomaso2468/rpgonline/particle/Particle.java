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

import org.newdawn.slick.Graphics;

import io.github.tomaso2468.rpgonline.TextureMap;
import io.github.tomaso2468.rpgonline.world2d.World;

/**
 * An interface representing a particle.
 * @author Tomaso2468
 */
public interface Particle {
	/**
	 * Determines if the particle uses custom rendering.
	 * @return {@code true} if the particle uses custom rendering, {@code false} otherwise.
	 */
	public default boolean isCustom() {
		return false;
	}
	
	/**
	 * Determines if the particle is affected by light.
	 * @return{@code true} if the particle is affected by light, {@code false} otherwise.
	 */
	public default boolean isLightAffected() {
		return true;
	}
	
	/**
	 * Gets the texture of this particle.
	 * @return A texture ID.
	 */
	public default int getTexture() {
		return -1;
	}
	
	/**
	 * Renders the particle.
	 * @param g The current graphics context.
	 * @param sx The X position to render at.
	 * @param sy The Y position to render at.
	 */
	public default void render(Graphics g, float sx, float sy) {
		g.drawImage(TextureMap.getTexture(getTexture()), sx, sy);
	}
	/**
	 * Gets the X position of this particle.
	 * @return A float value.
	 */
	public float getX();
	/**
	 * Gets the Y position of this particle.
	 * @return A float value.
	 */
	public float getY();
	
	/**
	 * Performs behaviour calculations on this particle.
	 * @param w The world the particle is in.
	 * @param wind The current wind value.
	 * @param particles The list of all particles.
	 * @param delta The time since the last update in seconds.
	 */
	public default void doBehaviour(World w, float wind, List<Particle> particles, float delta) {
		
	}
	
	/**
	 * Gets the transparency of this particle.
	 * @return A float value in the range 0..1.
	 */
	public float getAlpha();
}

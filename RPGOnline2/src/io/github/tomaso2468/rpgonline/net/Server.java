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
package io.github.tomaso2468.rpgonline.net;

/**
 * An interface representing a server.
 * @author Tomas
 */
public interface Server extends TickBased {
	/**
	 * Plays a sound at the specified location.
	 * @param id The ID of the sound to play.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 * @param dx The X speed of the sound.
	 * @param dy The Y speed of the sound.
	 * @param dz The Z speed of the sound.
	 */
	public void playSound(String id, float v, float p, float x, float y, float z, float dx, float dy, float dz);

	/**
	 * Plays a sound at the specified location.
	 * @param id The ID of the sound to play.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 */
	public default void playSound(String id, float v, float p, float x, float y, float z) {
		playSound(id, v, p, x, y, z, 0, 0, 0);
	}

	/**
	 * Plays a sound at the specified location.
	 * @param id The ID of the sound to play.
	 * @param v The volume of the sound.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 */
	public default void playSound(String id, float v, float x, float y, float z) {
		playSound(id, v, 1, x, y, z, 0, 0, 0);
	}

	/**
	 * Plays a sound at the specified location.
	 * @param id The ID of the sound to play.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 */
	public default void playSound(String id, float x, float y, float z) {
		playSound(id, 1, 1, x, y, z, 0, 0, 0);
	}

	/**
	 * Plays an ambient sound at the specified location.
	 * @param name The name of the sound.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 * @param loop {@code true} if the sound should loop, {@code false} otherwise.
	 */
	public void playAmbient(String name, float v, float p, float x, float y, float z, boolean loop);

	/**
	 * Plays an ambient sound at the specified location.
	 * @param name The name of the sound.
	 * @param v The volume of the sound.
	 * @param p The pitch of the sound.
	 * @param x The X position of the sound.
	 * @param y The Y position of the sound.
	 * @param z The Z position of the sound.
	 */
	public default void playAmbient(String name, float v, float p, float x, float y, float z) {
		playAmbient(name, v, p, x, y, z, false);
	}
}

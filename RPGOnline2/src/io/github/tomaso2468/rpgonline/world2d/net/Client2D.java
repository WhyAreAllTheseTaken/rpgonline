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
package io.github.tomaso2468.rpgonline.world2d.net;

import java.util.List;

import io.github.tomaso2468.abt.*;
import io.github.tomaso2468.rpgonline.net.Client;
import io.github.tomaso2468.rpgonline.world2d.World;

public interface Client2D extends Client {
	/**
	 * Gets the world that should be rendered.
	 * @return
	 */
	public World getWorld();
	/**
	 * Gets the X position of the player camera.
	 * @return a double value.
	 */
	public double getPlayerX();
	/**
	 * Gets the Y position of the player camera.
	 * @return a double value.
	 */
	public double getPlayerY();
	/**
	 * Gets the strength of the wind effect. A negative number will reverse the wind.
	 * @return A float value.
	 */
	public float getWind();
	/**
	 * Called to indicate that the player has walked in the X axis.
	 * @param s The distance the player has walked (1 for button presses). This value will be different for control systems that are analogue.
	 * @param delta The time since the last game update in seconds.
	 */
	public void walkY(double s, double delta);
	/**
	 * Called to indicate that the player has walked in the Y axis.
	 * @param s The distance the player has walked (1 for button presses). This value will be different for control systems that are analogue.
	 * @param delta The time since the last game update in seconds.
	 */
	public void walkX(double s, double delta);
	/**
	 * Indicates a request from the world for a chunk at the specified position.
	 * @param x The X position of the chunk
	 * @param y The Y position of the chunk
	 * @param z The Z position of the chunk
	 * 
	 * @see io.github.tomaso2468.rpgonline.world2d.ABTWorld
	 * @see io.github.tomaso2468.rpgonline.world2d.ABTNetWorld
	 */
	public void requestChunk(long x, long y, long z);
	/**
	 * Gets the list of received requested chunks.
	 * @return A list of tag groups representing chunk data.
	 */
	public List<TagGroup> getRequestedChunks();
	/**
	 * Sets if the sprint controls are enabled.
	 * @param s {@code true} if sprint is pressed, {@code false} otherwise.
	 */
	public void setSprint(boolean s);
}

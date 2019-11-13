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
package io.github.tomaso2468.rpgonline;

import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;

/**
 * A single state in a game.
 * 
 * @author Tomaso2468
 *
 */
public interface GameState {
	/**
	 * Called when a state is entered.
	 * 
	 * @param game The game.
	 * @throws RenderException If an error occurs involving the rendering engine.
	 */
	public default void enterState(Game game) throws RenderException {

	}

	/**
	 * Called when a state is exited.
	 * 
	 * @param game The game.
	 * @throws RenderException If an error occurs involving the rendering engine.
	 */
	public default void exitState(Game game) throws RenderException {

	}

	/**
	 * Initialises the game state. This is called after all states have been added
	 * before the init method completes. If your game creates states after the game
	 * has inited, your game should call this method. Optionally, lazy loading can
	 * be used an initialisation can be done in the enterState() method.
	 * 
	 * @param game The game
	 * @throws RenderException If an error occurs involving the rendering engine.
	 * 
	 * @see #enterState(Game)
	 */
	public default void init(Game game) throws RenderException {

	}

	/**
	 * Called every game update to update the state.
	 * @param game The game.
	 * @param delta The time since the last update in seconds.
	 * @throws RenderException If an error occurs involving the rendering engine.
	 */
	public default void update(Game game, float delta) throws RenderException {

	}

	/**
	 * Called to render the state.
	 * @param game The game.
	 * @param renderer The renderer.
	 * @throws RenderException If an error occurs involving the rendering engine.
	 */
	public void render(Game game, Renderer renderer) throws RenderException;

	/**
	 * Gets the unique ID number of this state.
	 * @return An integer value.
	 */
	public int getID();

	/**
	 * Draw the left half of this state's debug information.
	 * @param game The game.
	 * @param g The GUI graphics context.
	 * @param y The y position of the text.
	 * @return The next available y position for rendering text.
	 * @throws RenderException If an error occurs involving the rendering engine.
	 */
	public default float drawDebugLeft(Game game, Graphics g, float y) throws RenderException {
		y = game.drawDebugLine(g, "N/A", y, false);
		return y;
	}

	/**
	 * Draw the right half of this state's debug information.
	 * @param game The game.
	 * @param g The GUI graphics context.
	 * @param y The y position of the text.
	 * @return The next available y position for rendering text.
	 * @throws RenderException If an error occurs involving the rendering engine.
	 */
	public default float drawDebugRight(Game game, Graphics g, float y) throws RenderException {
		y = game.drawDebugLine(g, "N/A", y, true);
		return y;
	}

}

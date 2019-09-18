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
package io.github.tomaso2468.rpgonline.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A part of the GUI
 * 
 * @author Tomas
 */
public interface GUIItem {
	/**
	 * Render this GUI
	 * 
	 * @param g         The screen graphics.
	 * @param container The game container holding this game.
	 * @param game      This game.
	 * @param s_width   The width of the screen (scaled).
	 * @param s_height  The height of the screen (scaled).
	 */
	public void render(Graphics g, GameContainer container, StateBasedGame game, float s_width, float s_height);

	/**
	 * <p>
	 * Indicated if coordinates should be centred.
	 * </p>
	 * <p>
	 * For example, if {@code isCentered} returns {@code true} then coordinates
	 * would be scaled so that {@code (0, 0)} is in the centre of the screen. If
	 * {@code isCentered} returns {@code false} then {@code (0,0)} is in the top
	 * left corner.
	 * 
	 * @return {@code true} if coordinates should be centred. {@code false}
	 *         otherwise.
	 */
	public boolean isCentered();

	/**
	 * <p>
	 * Update the GUI.
	 * </p>
	 * <p>
	 * This method should be overridden by GUI elements with keyboard controls.
	 * </p>
	 * 
	 * @param container The game container holding this game.
	 * @param game      This game.
	 * @param delta     The time in milliseconds since the last update.
	 */
	public default void update(GameContainer container, StateBasedGame game, int delta) {

	}
}

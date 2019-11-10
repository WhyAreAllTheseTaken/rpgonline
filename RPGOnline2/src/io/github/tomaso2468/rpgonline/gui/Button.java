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
package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;
import io.github.tomaso2468.rpgonline.input.Input;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderException;

/**
 * A clickable button.
 * @author Tomaso2468
 *
 */
public class Button extends Component {
	/**
	 * The state of the button.
	 */
	private boolean state;
	/**
	 * The text of the button.
	 */
	private String text;
	
	/**
	 * Constructs a new button.
	 * @param text The text of the button.
	 */
	public Button(String text) {
		super();
		this.text = text;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws RenderException {
		ThemeManager.getTheme().paintButton(g, scaling, this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClickedLeft(float x, float y) {
		setState(true);
		onAction(x, y, state);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseExited(float x, float y) {
		setState(false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float delta, Input input) {
		setState(false);
	}
	
	/**
	 * Gets the state of the button.
	 * @return {@code true} if the button is pressed down, {@code false} otherwise.
	 */
	public boolean isState() {
		return state;
	}
	
	/**
	 * Sets the state of the button.
	 * @param state {@code true} if the button is pressed down, {@code false} otherwise.
	 */
	public void setState(boolean state) {
		this.state = state;
	}
	
	/**
	 * The event triggered for the button action.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse,
	 * @param state {@code true} if the button is pressed down, {@code false} otherwise.
	 */
	public void onAction(float x, float y, boolean state) {
		
	}

	/**
	 * Gets the text of this button.
	 * @return A string.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of this button.
	 * @param text A string.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateButtonBounds(c, this);
	}
}

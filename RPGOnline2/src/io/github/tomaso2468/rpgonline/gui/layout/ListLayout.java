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
package io.github.tomaso2468.rpgonline.gui.layout;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.Component;

/**
 * A layout that places components in a vertical list.
 * @author Tomaso2468
 *
 */
public class ListLayout extends Layout {
	/**
	 * The spacing of the layout.
	 */
	private final float spacing;
	
	/**
	 * Constructs a new ListLayout.
	 * @param spacing The spacing of the layout.
	 */
	public ListLayout(float spacing) {
		super();
		this.spacing = spacing;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Game game, Component c) {
		components.add(c);
		layout(game);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Game game, Component c) {
		super.remove(game, c);
		layout(game);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResize(Game game, float x, float y, float w, float h) {
		super.onResize(game, x, y, w, h);
		layout(game);
	}
	
	/**
	 * Updates the layout of this component.
	 */
	protected void layout(Game game) {
		Debugger.start("gui-layout");
		float y = 0;
		
		for (Component c : components) {
			c.setBounds(game, 0, y, c.getDefaultBounds(game, this).getWidth(), c.getDefaultBounds(game, this).getHeight());
			y += c.getDefaultBounds(game, this).getHeight() + spacing;
		}
		Debugger.stop("gui-layout");
	}
}

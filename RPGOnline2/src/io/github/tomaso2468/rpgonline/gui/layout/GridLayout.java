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

import java.util.HashMap;
import java.util.Map;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.Component;

/**
 * A layout that splits a container into a grid. The components will not be resized to fit each grid cell.
 * @author Tomaso2468
 *
 */
public class GridLayout extends Layout {
	/**
	 * The X position of the components.
	 */
	protected final Map<Component, Integer> x = new HashMap<>();
	/**
	 * The Y position of the components.
	 */
	protected final Map<Component, Integer> y = new HashMap<>();
	/**
	 * The grid width.
	 */
	protected int grid_x;
	/**
	 * The grid height.
	 */
	protected int grid_y;

	/**
	 * Constructs a new GridLayout
	 * @param grid_x The grid width.
	 * @param grid_y The grid height.
	 */
	public GridLayout(int grid_x, int grid_y) {
		super();
		this.grid_x = grid_x;
		this.grid_y = grid_y;
	}

	/**
	 * Adds a component to this container.
	 * @param c The component to add.
	 * @param x The X position of the component.
	 * @param y The Y position of the component.
	 */
	public void add(Game game, Component c, int x, int y) {
		components.add(c);
		this.x.put(c, x);
		this.y.put(c, y);

		Debugger.start("gui-layout");
		float spacingX = getW() / (grid_x + 1);
		float spacingY = getW() / (grid_y + 1);
		
		c.setBounds(game, spacingX * (x + 1) - c.getDefaultBounds(game, this).getWidth() / 2,
				spacingY * (y + 1) - c.getDefaultBounds(game, this).getHeight() / 2,
				c.getDefaultBounds(game, this).getWidth(),
				c.getDefaultBounds(game, this).getHeight());
		Debugger.stop("gui-layout");
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(Game game, Component c) {
		add(game, c, 0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResize(Game game, float ox, float oy, float w, float h) {
		Debugger.start("gui-layout");
		for (Component c : components) {
			int x = this.x.get(c);
			int y = this.y.get(c);

			float spacingX = getW() / (grid_x + 1);
			float spacingY = getW() / (grid_y + 1);
			
			c.setBounds(game, spacingX * (x + 1) - c.getDefaultBounds(game, this).getWidth() / 2,
					spacingY * (y + 1) - c.getDefaultBounds(game, this).getHeight() / 2,
					c.getDefaultBounds(game, this).getWidth(),
					c.getDefaultBounds(game, this).getHeight());
		}
		Debugger.stop("gui-layout");
	}
}

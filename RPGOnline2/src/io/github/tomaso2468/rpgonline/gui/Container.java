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

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderException;

/**
 * A component that can hold other components.
 * @author Tomaso2468
 *
 */
public class Container extends Component {
	/**
	 * The list of components in this container.
	 */
	protected final List<Component> components = new ArrayList<>();

	/**
	 * Gets the component at the specified mouse position. This function will also check containers held within this container so children of the returned component should not be containers.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 * @return A component held in this component or this component if no it is the only visible component.
	 */
	public Component getSelected(Game game, float x, float y) {
		Component selected = this;

		for (Component c : components) {
			if (c.getBounds(game).contains(x, y)) {
				selected = c;
			}
		}

		if (selected != this && selected instanceof Container) {
			return ((Container) selected).getSelected(game, x - selected.getX(), y - selected.getY());
		}

		return selected;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Game game, Graphics g, float scaling) throws RenderException {
		game.getTheme().paintContainer(game, g, scaling, this);
		paintComponents(game, g, scaling);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void debug(Game game, Graphics g, float scaling) {
		super.debug(game, g, scaling);
		debugComponents(game, g, scaling);
	}
	
	/**
	 * Debugs all components in this container.
	 * @param g The graphics context.
	 * @param scaling The scaling factor for rendering.
	 * @throws SlickException If an error occurs rendering the components.
	 */
	public void debugComponents(Game game, Graphics g, float scaling) {
		for (Component component : components) {
			g.pushTransform();
			g.translate(component.getX(), component.getY());
			component.debug(game, g, scaling);
			g.popTransform();
		}
	}

	/**
	 * Paints all components in this container.
	 * @param g The graphics context.
	 * @param scaling The scaling factor for rendering.
	 * @throws SlickException If an error occurs rendering the components.
	 */
	public void paintComponents(Game game, Graphics g, float scaling) throws RenderException {
		for (Component component : components) {
			g.pushTransform();
			g.translate(component.getX(), component.getY());
			component.paint(game, g, scaling);
			g.popTransform();
		}
	}

	/**
	 * Adds a component to this container.. This may trigger a layout change.
	 * @param c The component to add.
	 */
	public void add(Game game, Component c) {
		components.add(c);
		c.setBounds(game, 0, 0, c.getDefaultBounds(game, this).getWidth(), c.getDefaultBounds(game, this).getHeight());
	}

	/**
	 * Removes a component from this container.
	 * @param c The component to remove.
	 */
	public void remove(Game game, Component c) {
		components.remove(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResize(Game game, float x, float y, float w, float h) {
		for (Component c : components) {
			c.setBounds(game, 0, 0, c.getDefaultBounds(game, this).getWidth(), c.getDefaultBounds(game, this).getHeight());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClickedLeft(Game game, float x, float y) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mouseClickedLeft(game, x - selected.getX(), y - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressedLeft(Game game, float x, float y) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mousePressedLeft(game, x - selected.getX(), y - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseUnpressedLeft(Game game, float x, float y) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mouseUnpressedLeft(game, x - selected.getX(), y - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseEntered(Game game, float x, float y) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mouseEntered(game, x - selected.getX(), y - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseExited(Game game, float x, float y) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mouseExited(game, x - selected.getX(), y - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseMoved(Game game, float ox, float oy, float nx, float ny) {
		Component selected = getSelected(game, nx, ny);

		if (selected != this) {
			selected.mouseMoved(game, ox - selected.getX(), oy - selected.getY(), nx - selected.getX(), ny - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseDraggedLeft(Game game, float ox, float oy, float nx, float ny) {
		Component selected = getSelected(game, nx, ny);

		if (selected != this) {
			selected.mouseDraggedLeft(game, ox - selected.getX(), oy - selected.getY(), nx - selected.getX(),
					ny - selected.getY());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClickedAdditional(Game game, float x, float y, int button) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mouseClickedAdditional(game, x - selected.getX(), y - selected.getY(), button);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClickedMiddle(Game game, float x, float y) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mouseClickedMiddle(game, x - selected.getX(), y - selected.getY());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClickedRight(Game game, float x, float y) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mouseClickedRight(game, x - selected.getX(), y - selected.getY());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseDraggedMiddle(Game game, float ox, float oy, float nx, float ny) {
		Component selected = getSelected(game, nx, ny);

		if (selected != this) {
			selected.mouseDraggedMiddle(game, ox - selected.getX(), oy - selected.getY(), nx - selected.getX(),
					ny - selected.getY());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseDraggedRight(Game game, float ox, float oy, float nx, float ny) {
		Component selected = getSelected(game, nx, ny);

		if (selected != this) {
			selected.mouseDraggedRight(game, ox - selected.getX(), oy - selected.getY(), nx - selected.getX(),
					ny - selected.getY());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressedAdditional(Game game, float x, float y, int button) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mousePressedAdditional(game, x - selected.getX(), y - selected.getY(), button);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressedMiddle(Game game, float x, float y) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mousePressedMiddle(game, x - selected.getX(), y - selected.getY());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressedRight(Game game, float x, float y) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mousePressedRight(game, x - selected.getX(), y - selected.getY());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseUnpressedAdditional(Game game, float x, float y, int button) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mouseUnpressedAdditional(game, x - selected.getX(), y - selected.getY(), button);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseUnpressedMiddle(Game game, float x, float y) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mouseUnpressedMiddle(game, x - selected.getX(), y - selected.getY());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseUnpressedRight(Game game, float x, float y) {
		Component selected = getSelected(game, x, y);

		if (selected != this) {
			selected.mouseUnpressedRight(game, x - selected.getX(), y - selected.getY());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void containerUpdate(Game c) {
		for (Component component : components) {
			component.containerUpdate(c);
		}
	}
}

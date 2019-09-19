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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A component that can hold other components.
 * @author Tomas
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
	public Component getSelected(float x, float y) {
		Component selected = this;

		for (Component c : components) {
			if (c.getBounds().contains(x, y)) {
				selected = c;
			}
		}

		if (selected != this && selected instanceof Container) {
			return ((Container) selected).getSelected(x - selected.getX(), y - selected.getY());
		}

		return selected;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintContainer(g, scaling, this);
		paintComponents(g, scaling);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void debug(Graphics g, float scaling) {
		super.debug(g, scaling);
		debugComponents(g, scaling);
	}
	
	/**
	 * Debugs all components in this container.
	 * @param g The graphics context.
	 * @param scaling The scaling factor for rendering.
	 * @throws SlickException If an error occurs rendering the components.
	 */
	public void debugComponents(Graphics g, float scaling) {
		for (Component component : components) {
			g.pushTransform();
			g.translate(component.getX(), component.getY());
			component.debug(g, scaling);
			g.popTransform();
		}
	}

	/**
	 * Paints all components in this container.
	 * @param g The graphics context.
	 * @param scaling The scaling factor for rendering.
	 * @throws SlickException If an error occurs rendering the components.
	 */
	public void paintComponents(Graphics g, float scaling) throws SlickException {
		for (Component component : components) {
			g.pushTransform();
			g.translate(component.getX(), component.getY());
			component.paint(g, scaling);
			g.popTransform();
		}
	}

	/**
	 * Adds a component to this container.. This may trigger a layout change.
	 * @param c The component to add.
	 */
	public void add(Component c) {
		components.add(c);
		c.setBounds(0, 0, c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
	}

	/**
	 * Removes a component from this container.
	 * @param c The component to remove.
	 */
	public void remove(Component c) {
		components.remove(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResize(float x, float y, float w, float h) {
		for (Component c : components) {
			c.setBounds(0, 0, c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClickedLeft(float x, float y) {
		Component selected = getSelected(x, y);

		if (selected != this) {
			selected.mouseClickedLeft(x - selected.getX(), y - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressedLeft(float x, float y) {
		Component selected = getSelected(x, y);

		if (selected != this) {
			selected.mousePressedLeft(x - selected.getX(), y - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseUnpressedLeft(float x, float y) {
		Component selected = getSelected(x, y);

		if (selected != this) {
			selected.mouseUnpressedLeft(x - selected.getX(), y - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseEntered(float x, float y) {
		Component selected = getSelected(x, y);

		if (selected != this) {
			selected.mouseEntered(x - selected.getX(), y - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseExited(float x, float y) {
		Component selected = getSelected(x, y);

		if (selected != this) {
			selected.mouseExited(x - selected.getX(), y - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseMoved(float ox, float oy, float nx, float ny) {
		Component selected = getSelected(nx, ny);

		if (selected != this) {
			selected.mouseMoved(ox - selected.getX(), oy - selected.getY(), nx - selected.getX(), ny - selected.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseDraggedLeft(float ox, float oy, float nx, float ny) {
		Component selected = getSelected(nx, ny);

		if (selected != this) {
			selected.mouseDraggedLeft(ox - selected.getX(), oy - selected.getY(), nx - selected.getX(),
					ny - selected.getY());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void containerUpdate(GameContainer c) {
		for (Component component : components) {
			component.containerUpdate(c);
		}
	}
}

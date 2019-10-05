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
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.render.Graphics;

/**
 * The root of the GUI system.
 * @author Tomaso2468
 *
 */
public class GUI {
	/**
	 * The list of screens in this GUI system.
	 */
	private final List<Screen> screens = new ArrayList<Screen>();
	/**
	 * The selected component in the GUI.
	 */
	private Component selected = null;
	/**
	 * The state of the left mouse button.
	 */
	private boolean mouseLeft = false;
	
	/**
	 * Click data for the left mouse button.
	 */
	private boolean mouseClick = false;
	/**
	 * The state of the right mouse button.
	 */
	private boolean mouseRight = false;
	/**
	 * Click data for the right mouse button.
	 */
	private boolean mouseRightClick = false;
	/**
	 * The state of the middle mouse button.
	 */
	private boolean mouseMiddle = false;
	/**
	 * Click data for the middle mouse button.
	 */
	private boolean mouseMiddleClick = false;
	/**
	 * The state of the additional mouse buttons.
	 */
	private boolean[] mouseAdditional;
	/**
	 * Click data for the additional mouse buttons.
	 */
	private boolean[] mouseAdditionalClick;
	/**
	 * The X position of the mouse.
	 */
	private float mx;
	/**
	 * The Y position of the mouse.
	 */
	private float my;
	/**
	 * The scaling factor for the display.
	 */
	private float scaling = 1;

	/**
	 * Adds a screen to this GUI.
	 * @param screen A screen object.
	 */
	public void add(Screen screen) {
		if (screens.size() > 0) {
			getTopScreen().mouseExited(mx, my);
		}
		screens.add(screen);
		selected = null;
	}
	
	/**
	 * Removes a screen from this GUI.
	 * @param screen A screen object.
	 */
	public void remove(Screen screen) {
		if (screens.size() > 0) {
			getTopScreen().mouseExited(mx, my);
		}
		screens.remove(screen);
		selected = null;
	}

	/**
	 * Gets the top visible screen.
	 * @return A screen object.
	 */
	private Screen getTopScreen() {
		return screens.get(screens.size() - 1);
	}

	/**
	 * Updates the state of the mouse. This method does not calculate motion.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 * @param button The state of each mouse button.
	 */
	public void mouseState(float x, float y, boolean[] buttons) {
		mouseStateLeft(x, y, buttons[0]);
		mouseStateRight(x, y, buttons[1]);
		mouseStateMiddle(x, y, buttons[2]);
		
		if (mouseAdditional == null) {
			mouseAdditional = new boolean[buttons.length];
		}
		if (mouseAdditional.length != buttons.length) {
			mouseAdditional = new boolean[buttons.length];
		}
		
		if (buttons.length > 3) {
			for (int i = 3; i < buttons.length; i++) {
				mouseStateAdditional(x, y, i, buttons[i]);
			}
		}
	}
	
	/**
	 * Updates the state of an additional mouse button.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 * @param state the state of the mouse button.
	 */
	protected void mouseStateAdditional(float x, float y, int button, boolean state) {
		if (mouseAdditional[button] == state) {
			return;
		}

		mouseAdditional[button] = state;

		if (state) {
			mousePressedAdditional(x, y, button);
			mouseAdditionalClick[button] = true;
		} else {
			mouseUnpressedAdditional(x, y, button);
			if (mouseAdditionalClick[button]) {
				mouseAdditionalClick[button] = false;
				mouseClickedAdditional(x, y, button);
			}
		}
	}
	
	/**
	 * Updates the state of the left mouse button.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 * @param left The state of the left mouse button.
	 */
	protected void mouseStateLeft(float x, float y, boolean left) {
		if (mouseLeft == left) {
			return;
		}

		mouseLeft = left;

		if (left) {
			mousePressedLeft(x, y);
			mouseClick = true;
		} else {
			mouseUnpressedLeft(x, y);
			if (mouseClick) {
				mouseClick = false;
				mouseClickedLeft(x, y);
			}
		}
	}
	
	/**
	 * Updates the state of the right mouse button.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 * @param left The state of the right mouse button.
	 */
	protected void mouseStateRight(float x, float y, boolean right) {
		if (mouseRight == right) {
			return;
		}

		mouseRight = right;

		if (right) {
			mousePressedRight(x, y);
			mouseRightClick = true;
		} else {
			mouseUnpressedRight(x, y);
			if (mouseRightClick) {
				mouseRightClick = false;
				mouseClickedRight(x, y);
			}
		}
	}
	/**
	 * Updates the state of the middle mouse button.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 * @param left The state of the middle mouse button.
	 */
	protected void mouseStateMiddle(float x, float y, boolean middle) {
		if (mouseMiddle == middle) {
			return;
		}

		mouseMiddle = middle;

		if (middle) {
			mousePressedMiddle(x, y);
			mouseMiddleClick = true;
		} else {
			mouseUnpressedMiddle(x, y);
			if (mouseMiddleClick) {
				mouseMiddleClick = false;
				mouseClickedMiddle(x, y);
			}
		}
	}

	/**
	 * Updates the position of the mouse.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	public void mouseMoved(float x, float y) {
		if (mouseLeft || mouseRight || mouseMiddle) {
			if (mouseLeft) {
				mouseDraggedLeft(mx, my, x, y);
			} else if (mouseRight) {
				mouseDraggedRight(mx, my, x, y);
			} else if (mouseMiddle) {
				mouseDraggedMiddle(mx, my, x, y);
			} 
			
		} else {
			mouseMoved(mx, my, x / scaling, y / scaling);

			if (screens.size() > 0) {
				Component selected = getTopScreen().getSelected(x, y);
				if (selected != this.selected) {
					selected.mouseEntered(x, y);
					if (this.selected != null) this.selected.mouseExited(x, y);
					this.selected = selected;
				}
			}
		}
	}

	/**
	 * Left click event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mouseClickedLeft(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseClickedLeft(x, y);
		}
	}

	/**
	 * Left press event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mousePressedLeft(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mousePressedLeft(x, y);
		}
	}

	/**
	 * Left unpress event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mouseUnpressedLeft(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseUnpressedLeft(x, y);
		}
	}
	
	/**
	 * Right click event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mouseClickedRight(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseClickedRight(x, y);
		}
	}

	/**
	 * Right press event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mousePressedRight(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mousePressedRight(x, y);
		}
	}

	/**
	 * Right unpress event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mouseUnpressedRight(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseUnpressedRight(x, y);
		}
	}
	
	/**
	 * Middle click event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mouseClickedMiddle(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseClickedMiddle(x, y);
		}
	}

	/**
	 * Middle press event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mousePressedMiddle(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mousePressedMiddle(x, y);
		}
	}

	/**
	 * Middle unpress event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mouseUnpressedMiddle(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseUnpressedMiddle(x, y);
		}
	}
	
	/**
	 * Additional click event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 * @param button The button that was pressed.
	 */
	protected void mouseClickedAdditional(float x, float y, int button) {
		if (screens.size() > 0) {
			getTopScreen().mouseClickedAdditional(x, y, button);
		}
	}

	/**
	 * Additional press event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 * @param button The button that was pressed.
	 */
	protected void mousePressedAdditional(float x, float y, int button) {
		if (screens.size() > 0) {
			getTopScreen().mousePressedAdditional(x, y, button);
		}
	}

	/**
	 * Additional unpress event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 * @param button The button that was pressed.
	 */
	protected void mouseUnpressedAdditional(float x, float y, int button) {
		if (screens.size() > 0) {
			getTopScreen().mouseUnpressedAdditional(x, y, button);
		}
	}

	/**
	 * The move event for the mouse.
	 * @param ox The previous X position of the mouse.
	 * @param oy The previous Y position of the mouse.
	 * @param nx The new X position of the mouse.
	 * @param ny The new Y position of the mouse.
	 */
	protected void mouseMoved(float ox, float oy, float nx, float ny) {
		if (screens.size() > 0) {
			getTopScreen().mouseMoved(ox, oy, nx, ny);
		}
	}

	/**
	 * The drag event for the left mouse button.
	 * @param ox The previous X position of the mouse.
	 * @param oy The previous Y position of the mouse.
	 * @param nx The new X position of the mouse.
	 * @param ny The new Y position of the mouse.
	 */
	protected void mouseDraggedLeft(float ox, float oy, float nx, float ny) {
		if (screens.size() > 0) {
			getTopScreen().mouseDraggedLeft(ox, oy, nx, ny);
		}
	}
	
	/**
	 * The drag event for the right mouse button.
	 * @param ox The previous X position of the mouse.
	 * @param oy The previous Y position of the mouse.
	 * @param nx The new X position of the mouse.
	 * @param ny The new Y position of the mouse.
	 */
	protected void mouseDraggedRight(float ox, float oy, float nx, float ny) {
		if (screens.size() > 0) {
			getTopScreen().mouseDraggedRight(ox, oy, nx, ny);
		}
	}
	/**
	 * The drag event for the middle mouse button.
	 * @param ox The previous X position of the mouse.
	 * @param oy The previous Y position of the mouse.
	 * @param nx The new X position of the mouse.
	 * @param ny The new Y position of the mouse.
	 */
	protected void mouseDraggedMiddle(float ox, float oy, float nx, float ny) {
		if (screens.size() > 0) {
			getTopScreen().mouseDraggedMiddle(ox, oy, nx, ny);
		}
	}

	/**
	 * The mouse scroll event.
	 * @param scroll The value to scroll by.
	 */
	public void mouseWheel(float scroll) {
		if (scroll == 0) {
			return;
		}
		if (selected == null) {
			if (screens.size() > 0) {
				getTopScreen().mouseWheel(scroll);
			}
		} else {
			selected.mouseWheel(scroll);
		}
	}
	
	/**
	 * Initialises this GUI with the screen dimensions and scaling.
	 * @param w The width of the screen.
	 * @param h The height of the screen.
	 * @param scaling The scaling of the screen.
	 */
	public void init(int w, int h, float scaling) {
		for (Screen screen : screens) {
			screen.setBounds(0, 0, w / scaling, h / scaling);
		}
		this.scaling = scaling;
		selected = null;
	}

	/**
	 * Paints this GUI.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the screen.
	 * @throws SlickException If an error occurs when rendering.
	 */
	public void paint(Graphics g, float scaling) throws SlickException {
		this.scaling = scaling;
		g.scale(scaling, scaling);
		for (Screen screen : screens) {
			g.pushTransform();
			screen.paint(g, scaling);
			g.popTransform();
		}
	}
	
	/**
	 * Debugs this GUI.
	 * @param g The current graphics context.
	 * @param scaling The scaling of the screen.
	 * @throws SlickException If an error occurs when rendering.
	 */
	public void debug(Graphics g, float scaling) throws SlickException {
		this.scaling = scaling;
		g.scale(scaling, scaling);
		for (Screen screen : screens) {
			g.pushTransform();
			screen.debug(g, scaling);
			g.popTransform();
		}
	}
	
	/**
	 * Game update information.
	 * @param delta The time since the last game update in seconds.
	 */
	public void update(float delta) {
		if (selected != null) {
			selected.update(delta);
		}
	}
	
	/**
	 * Updates the game container.
	 * @param c A game container object.
	 */
	public void containerUpdate(GameContainer c) {
		for (Screen screen : screens) {
			screen.containerUpdate(c);
		}
	}
}
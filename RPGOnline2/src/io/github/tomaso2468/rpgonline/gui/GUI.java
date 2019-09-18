package io.github.tomaso2468.rpgonline.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * The root of the GUI system.
 * @author Tomas
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
			getTopScreen().mouseExited(mx / scaling, my / scaling);
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
			getTopScreen().mouseExited(mx / scaling, my / scaling);
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
	 * @param left The state of the left mouse button.
	 * @param right The state of the right mouse button.
	 * @param middle The state of the middle mouse button.
	 */
	public void mouseState(float x, float y, boolean left, boolean right, boolean middle) {
		mouseStateLeft(x, y, left);
		mouseStateRight(x, y, right);
		mouseStateMiddle(x, y, middle);
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
			mousePressedLeft(x / scaling, y / scaling);
			mouseClick = true;
		} else {
			mouseUnpressedLeft(x / scaling, y / scaling);
			if (mouseClick) {
				mouseClick = false;
				mouseClickedLeft(x / scaling, y / scaling);
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
			mousePressedRight(x / scaling, y / scaling);
			mouseRightClick = true;
		} else {
			mouseUnpressedRight(x / scaling, y / scaling);
			if (mouseRightClick) {
				mouseRightClick = false;
				mouseClickedRight(x / scaling, y / scaling);
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
			mousePressedMiddle(x / scaling, y / scaling);
			mouseMiddleClick = true;
		} else {
			mouseUnpressedMiddle(x / scaling, y / scaling);
			if (mouseMiddleClick) {
				mouseMiddleClick = false;
				mouseClickedMiddle(x / scaling, y / scaling);
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
				mouseDraggedLeft(mx, my, x / scaling, y / scaling);
			} else if (mouseRight) {
				mouseDraggedRight(mx, my, x / scaling, y / scaling);
			} else if (mouseMiddle) {
				mouseDraggedMiddle(mx, my, x / scaling, y / scaling);
			} 
			
		} else {
			mouseMoved(mx, my, x / scaling, y / scaling);

			if (screens.size() > 0) {
				Component selected = getTopScreen().getSelected(x / scaling, y / scaling);
				if (selected != this.selected) {
					selected.mouseEntered(x / scaling, y / scaling);
					if (this.selected != null) this.selected.mouseExited(x / scaling, y / scaling);
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
			getTopScreen().mouseClickedLeft(x / scaling, y / scaling);
		}
	}

	/**
	 * Left press event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mousePressedLeft(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mousePressedLeft(x / scaling, y / scaling);
		}
	}

	/**
	 * Left unpress event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mouseUnpressedLeft(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseUnpressedLeft(x / scaling, y / scaling);
		}
	}
	
	/**
	 * Right click event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mouseClickedRight(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseClickedRight(x / scaling, y / scaling);
		}
	}

	/**
	 * Right press event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mousePressedRight(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mousePressedRight(x / scaling, y / scaling);
		}
	}

	/**
	 * Right unpress event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mouseUnpressedRight(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseUnpressedRight(x / scaling, y / scaling);
		}
	}
	
	/**
	 * Middle click event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mouseClickedMiddle(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseClickedMiddle(x / scaling, y / scaling);
		}
	}

	/**
	 * Middle press event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mousePressedMiddle(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mousePressedMiddle(x / scaling, y / scaling);
		}
	}

	/**
	 * Middle unpress event.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse.
	 */
	protected void mouseUnpressedMiddle(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseUnpressedMiddle(x / scaling, y / scaling);
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
			getTopScreen().mouseMoved(ox / scaling, oy / scaling, nx / scaling, ny / scaling);
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
			getTopScreen().mouseDraggedLeft(ox / scaling, oy / scaling, nx / scaling, ny / scaling);
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
			getTopScreen().mouseDraggedRight(ox / scaling, oy / scaling, nx / scaling, ny / scaling);
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
			getTopScreen().mouseDraggedMiddle(ox / scaling, oy / scaling, nx / scaling, ny / scaling);
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
		for (Screen screen : screens) {
			g.pushTransform();
			screen.paint(g, scaling);
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
}
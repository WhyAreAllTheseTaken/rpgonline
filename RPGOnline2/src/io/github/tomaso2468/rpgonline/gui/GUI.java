package io.github.tomaso2468.rpgonline.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class GUI {
	private final List<Screen> screens = new ArrayList<Screen>();
	private Component selected = null;
	private boolean mouseLeft = false;
	private boolean mouseClick = false;
	private boolean mouseRight = false;
	private boolean mouseRightClick = false;
	private boolean mouseMiddle = false;
	private boolean mouseMiddleClick = false;
	private float mx;
	private float my;
	private float scaling = 1;

	public void add(Screen screen) {
		if (screens.size() > 0) {
			getTopScreen().mouseExited(mx / scaling, my / scaling);
		}
		screens.add(screen);
		selected = null;
	}
	
	public void remove(Screen screen) {
		if (screens.size() > 0) {
			getTopScreen().mouseExited(mx / scaling, my / scaling);
		}
		screens.remove(screen);
		selected = null;
	}

	private Screen getTopScreen() {
		return screens.get(screens.size() - 1);
	}

	public void mouseState(float x, float y, boolean left, boolean right, boolean middle) {
		mouseStateLeft(x, y, left);
		mouseStateRight(x, y, right);
		mouseStateMiddle(x, y, middle);
	}
	
	public void mouseStateLeft(float x, float y, boolean left) {
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
	
	public void mouseStateRight(float x, float y, boolean right) {
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
	
	public void mouseStateMiddle(float x, float y, boolean middle) {
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

	public void mouseClickedLeft(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseClickedLeft(x / scaling, y / scaling);
		}
	}

	public void mousePressedLeft(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mousePressedLeft(x / scaling, y / scaling);
		}
	}

	public void mouseUnpressedLeft(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseUnpressedLeft(x / scaling, y / scaling);
		}
	}
	
	public void mouseClickedRight(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseClickedRight(x / scaling, y / scaling);
		}
	}

	public void mousePressedRight(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mousePressedRight(x / scaling, y / scaling);
		}
	}

	public void mouseUnpressedRight(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseUnpressedRight(x / scaling, y / scaling);
		}
	}
	
	public void mouseClickedMiddle(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseClickedMiddle(x / scaling, y / scaling);
		}
	}

	public void mousePressedMiddle(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mousePressedMiddle(x / scaling, y / scaling);
		}
	}

	public void mouseUnpressedMiddle(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseUnpressedMiddle(x / scaling, y / scaling);
		}
	}

	public void mouseMoved(float ox, float oy, float nx, float ny) {
		if (screens.size() > 0) {
			getTopScreen().mouseMoved(ox / scaling, oy / scaling, nx / scaling, ny / scaling);
		}
	}

	public void mouseDraggedLeft(float ox, float oy, float nx, float ny) {
		if (screens.size() > 0) {
			getTopScreen().mouseDraggedLeft(ox / scaling, oy / scaling, nx / scaling, ny / scaling);
		}
	}
	
	public void mouseDraggedRight(float ox, float oy, float nx, float ny) {
		if (screens.size() > 0) {
			getTopScreen().mouseDraggedRight(ox / scaling, oy / scaling, nx / scaling, ny / scaling);
		}
	}
	
	public void mouseDraggedMiddle(float ox, float oy, float nx, float ny) {
		if (screens.size() > 0) {
			getTopScreen().mouseDraggedMiddle(ox / scaling, oy / scaling, nx / scaling, ny / scaling);
		}
	}

	public void mouseWheel(float scroll) {
		if (selected == null) {
			if (screens.size() > 0) {
				getTopScreen().mouseWheel(scroll);
			}
		} else {
			selected.mouseWheel(scroll);
		}
	}
	
	public void init(int w, int h, float scaling) {
		for (Screen screen : screens) {
			screen.setBounds(0, 0, w / scaling, h / scaling);
		}
		this.scaling = scaling;
		selected = null;
	}

	public void paint(Graphics g, float scaling) throws SlickException {
		this.scaling = scaling;
		for (Screen screen : screens) {
			g.pushTransform();
			screen.paint(g, scaling);
			g.popTransform();
		}
	}
	
	public void update() {
		if (selected != null) {
			selected.update();
		}
	}
}
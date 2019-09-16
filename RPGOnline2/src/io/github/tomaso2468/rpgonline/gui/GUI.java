package io.github.tomaso2468.rpgonline.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class GUI {
	private final List<Screen> screens = new ArrayList<Screen>();
	private Component selected = null;
	private boolean mouseState = false;
	private boolean mouseClick = false;
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

	public void mouseState(float x, float y, boolean state) {
		if (mouseState == state) {
			return;
		}

		mouseState = state;

		if (state) {
			mousePressed(x / scaling, y / scaling);
			mouseClick = true;
		} else {
			mouseUnpressed(x / scaling, y / scaling);
			if (mouseClick) {
				mouseClick = false;
				mouseClicked(x / scaling, y / scaling);
			}
		}
	}

	public void mouseMoved(float x, float y) {
		if (mouseState) {
			mouseDragged(mx, my, x / scaling, y / scaling);
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

	public void mouseClicked(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseClicked(x / scaling, y / scaling);
		}
	}

	public void mousePressed(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mousePressed(x / scaling, y / scaling);
		}
	}

	public void mouseUnpressed(float x, float y) {
		if (screens.size() > 0) {
			getTopScreen().mouseUnpressed(x / scaling, y / scaling);
		}
	}

	public void mouseMoved(float ox, float oy, float nx, float ny) {
		if (screens.size() > 0) {
			getTopScreen().mouseMoved(ox / scaling, oy / scaling, nx / scaling, ny / scaling);
		}
	}

	public void mouseDragged(float ox, float oy, float nx, float ny) {
		if (screens.size() > 0) {
			getTopScreen().mouseDragged(ox / scaling, oy / scaling, nx / scaling, ny / scaling);
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
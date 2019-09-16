package io.github.tomaso2468.rpgonline.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class Container extends Component {
	protected List<Component> components = new ArrayList<>();

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

	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintContainer(g, scaling, this);
		paintComponents(g, scaling);
	}

	public void paintComponents(Graphics g, float scaling) throws SlickException {
		for (Component component : components) {
			g.pushTransform();
			g.translate(-component.getX() * scaling, -component.getY() * scaling);
			component.paint(g, scaling);
			g.popTransform();
		}
	}

	public void add(Component c) {
		components.add(c);
		c.setBounds(0, 0, c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
	}

	public void remove(Component c) {
		components.remove(c);
	}

	@Override
	public void onResize(float x, float y, float w, float h) {
		for (Component component : components) {
			component.setBounds(0, 0, w, h);
		}
	}

	@Override
	public void mouseClicked(float x, float y) {
		Component selected = getSelected(x, y);

		if (selected != this) {
			selected.mouseClicked(x - selected.getX(), y - selected.getY());
		}
	}

	@Override
	public void mousePressed(float x, float y) {
		Component selected = getSelected(x, y);

		if (selected != this) {
			selected.mousePressed(x - selected.getX(), y - selected.getY());
		}
	}

	@Override
	public void mouseUnpressed(float x, float y) {
		Component selected = getSelected(x, y);

		if (selected != this) {
			selected.mouseUnpressed(x - selected.getX(), y - selected.getY());
		}
	}

	@Override
	public void mouseEntered(float x, float y) {
		Component selected = getSelected(x, y);

		if (selected != this) {
			selected.mouseEntered(x - selected.getX(), y - selected.getY());
		}
	}

	@Override
	public void mouseExited(float x, float y) {
		Component selected = getSelected(x, y);

		if (selected != this) {
			selected.mouseExited(x - selected.getX(), y - selected.getY());
		}
	}

	@Override
	public void mouseMoved(float ox, float oy, float nx, float ny) {
		Component selected = getSelected(nx, ny);

		if (selected != this) {
			selected.mouseMoved(ox - selected.getX(), oy - selected.getY(), nx - selected.getX(), ny - selected.getY());
		}
	}

	@Override
	public void mouseDragged(float ox, float oy, float nx, float ny) {
		Component selected = getSelected(nx, ny);

		if (selected != this) {
			selected.mouseDragged(ox - selected.getX(), oy - selected.getY(), nx - selected.getX(),
					ny - selected.getY());
		}
	}
}

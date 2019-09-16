package io.github.tomaso2468.rpgonline.gui.layout;

import java.util.HashMap;
import java.util.Map;

import io.github.tomaso2468.rpgonline.gui.Component;
import io.github.tomaso2468.rpgonline.gui.Container;

public class CornerLayout extends Container {
	private final Map<Component, Corner> map = new HashMap<>();

	public void add(Component c, Corner corner) {
		components.add(c);
		map.put(c, corner);

		switch (corner) {
		case BOTTOM_LEFT:
			c.setBounds(0, getH() - c.getDefaultBounds(this).getHeight(), c.getDefaultBounds(this).getWidth(),
					c.getDefaultBounds(this).getHeight());
			break;
		case BOTTOM_RIGHT:
			c.setBounds(getW() - c.getDefaultBounds(this).getWidth(), getH() - c.getDefaultBounds(this).getHeight(),
					c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
			break;
		case TOP_LEFT:
			c.setBounds(0, getH() - c.getDefaultBounds(this).getHeight(), c.getDefaultBounds(this).getWidth(),
					c.getDefaultBounds(this).getHeight());
			break;
		case TOP_RIGHT:
			c.setBounds(getW() - c.getDefaultBounds(this).getWidth(), getH() - c.getDefaultBounds(this).getHeight(),
					c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
			break;
		default:
			break;
		}
	}

	public void add(Component c) {
		add(c, Corner.TOP_LEFT);
	}
	
	@Override
	public void onResize(float x, float y, float w, float h) {
		for (Component c : components) {
			Corner corner = map.get(c);

			switch (corner) {
			case BOTTOM_LEFT:
				c.setBounds(0, getH() - c.getDefaultBounds(this).getHeight(), c.getDefaultBounds(this).getWidth(),
						c.getDefaultBounds(this).getHeight());
				break;
			case BOTTOM_RIGHT:
				c.setBounds(getW() - c.getDefaultBounds(this).getWidth(), getH() - c.getDefaultBounds(this).getHeight(),
						c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
				break;
			case TOP_LEFT:
				c.setBounds(0, getH() - c.getDefaultBounds(this).getHeight(), c.getDefaultBounds(this).getWidth(),
						c.getDefaultBounds(this).getHeight());
				break;
			case TOP_RIGHT:
				c.setBounds(getW() - c.getDefaultBounds(this).getWidth(), getH() - c.getDefaultBounds(this).getHeight(),
						c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
				break;
			default:
				break;
			}
		}
	}
}

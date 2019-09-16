package io.github.tomaso2468.rpgonline.gui.layout;

import io.github.tomaso2468.rpgonline.gui.Component;
import io.github.tomaso2468.rpgonline.gui.Container;

public class Border extends Container {
	private float border;
	public Border(float border) {
		this.border = border;
	}
	
	@Override
	public void add(Component c) {
		components.add(c);
		c.setBounds(border, border, c.getDefaultBounds(this).getWidth() - 2 * border, c.getDefaultBounds(this).getHeight() - 2 * border);
	}
	
	@Override
	public void onResize(float x, float y, float w, float h) {
		for (Component c : components) {
			c.setBounds(border, border, c.getDefaultBounds(this).getWidth() - 2 * border, c.getDefaultBounds(this).getHeight() - 2 * border);
		}
	}
}

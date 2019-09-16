package io.github.tomaso2468.rpgonline.gui.layout;

import java.util.HashMap;
import java.util.Map;

import io.github.tomaso2468.rpgonline.gui.Component;
import io.github.tomaso2468.rpgonline.gui.Container;

public class GridLayout extends Container {
	private final Map<Component, Integer> x = new HashMap<>();
	private final Map<Component, Integer> y = new HashMap<>();
	private final int grid_x;
	private final int grid_y;

	public GridLayout(int grid_x, int grid_y) {
		super();
		this.grid_x = grid_x;
		this.grid_y = grid_y;
	}

	public void add(Component c, int x, int y) {
		components.add(c);
		this.x.put(c, x);
		this.y.put(c, y);

		float spacingX = getW() / (grid_x + 1);
		float spacingY = getW() / (grid_y + 1);
		
		c.setBounds(spacingX * (x + 1) - c.getDefaultBounds(this).getWidth() / 2,
				spacingY * (y + 1) - c.getDefaultBounds(this).getHeight() / 2,
				c.getDefaultBounds(this).getWidth(),
				c.getDefaultBounds(this).getHeight());
	}

	public void add(Component c) {
		add(c, 0, 0);
	}

	@Override
	public void onResize(float ox, float oy, float w, float h) {
		for (Component c : components) {
			int x = this.x.get(c);
			int y = this.y.get(c);

			float spacingX = getW() / (grid_x + 1);
			float spacingY = getW() / (grid_y + 1);
			
			c.setBounds(spacingX * (x + 1) - c.getDefaultBounds(this).getWidth() / 2,
					spacingY * (y + 1) - c.getDefaultBounds(this).getHeight() / 2,
					c.getDefaultBounds(this).getWidth(),
					c.getDefaultBounds(this).getHeight());
		}
	}
}

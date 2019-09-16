package io.github.tomaso2468.rpgonline.gui.layout;

import java.util.HashMap;
import java.util.Map;

import io.github.tomaso2468.rpgonline.gui.Component;
import io.github.tomaso2468.rpgonline.gui.Container;

public class OuterGridLayout extends Container {
	private final Map<Component, Integer> x = new HashMap<>();
	private final Map<Component, Integer> y = new HashMap<>();
	private final int grid_x;
	private final int grid_y;

	public OuterGridLayout(int grid_x, int grid_y) {
		super();
		this.grid_x = grid_x;
		this.grid_y = grid_y;
	}

	public void add(Component c, int x, int y) {
		components.add(c);
		this.x.put(c, x);
		this.y.put(c, y);

		if (y == 0) {
			float spacing = getW() / (grid_x + 1);

			c.setBounds(spacing * (x + 1) - c.getDefaultBounds(this).getWidth() / 2, 0,
					c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
		} else if (y == grid_y - 1) {
			float spacing = getW() / (grid_x + 1);

			c.setBounds(spacing * (x + 1) - c.getDefaultBounds(this).getWidth() / 2,
					getH() - c.getDefaultBounds(this).getHeight(), c.getDefaultBounds(this).getWidth(),
					c.getDefaultBounds(this).getHeight());
		} else if (x == 0) {
			float spacing = getH() / (grid_y + 1);

			c.setBounds(0, spacing * (y + 1) - c.getDefaultBounds(this).getHeight() / 2, c.getDefaultBounds(this).getWidth(),
					c.getDefaultBounds(this).getHeight());
		} else if (x == grid_x - 1) {
			float spacing = getH() / (grid_y + 1);

			c.setBounds(0, spacing * (y + 1) - c.getDefaultBounds(this).getHeight() / 2, c.getDefaultBounds(this).getWidth(),
					c.getDefaultBounds(this).getHeight());
		} else {
			throw new IllegalArgumentException("Grid locations can only be placed on the outside of the layout.");
		}
	}

	public void add(Component c) {
		add(c, 0, 0);
	}

	@Override
	public void onResize(float ox, float oy, float w, float h) {
		for (Component c : components) {
			int x = this.x.get(c);
			int y = this.y.get(c);

			if (y == 0) {
				float spacing = getW() / (grid_x + 1);

				c.setBounds(spacing * (x + 1) - c.getDefaultBounds(this).getWidth() / 2, 0,
						c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
			} else if (y == grid_y - 1) {
				float spacing = getW() / (grid_x + 1);

				c.setBounds(spacing * (x + 1) - c.getDefaultBounds(this).getWidth() / 2,
						getH() - c.getDefaultBounds(this).getHeight(), c.getDefaultBounds(this).getWidth(),
						c.getDefaultBounds(this).getHeight());
			} else if (x == 0) {
				float spacing = getH() / (grid_y + 1);

				c.setBounds(0, spacing * (y + 1) - c.getDefaultBounds(this).getHeight() / 2, c.getDefaultBounds(this).getWidth(),
						c.getDefaultBounds(this).getHeight());
			} else if (x == grid_x - 1) {
				float spacing = getH() / (grid_y + 1);

				c.setBounds(0, spacing * (y + 1) - c.getDefaultBounds(this).getHeight() / 2, c.getDefaultBounds(this).getWidth(),
						c.getDefaultBounds(this).getHeight());
			} else {
				throw new IllegalArgumentException("Grid locations can only be placed on the outside of the layout.");
			}
		}
	}
}

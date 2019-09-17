package io.github.tomaso2468.rpgonline.gui.layout;

import java.util.HashMap;
import java.util.Map;

import io.github.tomaso2468.rpgonline.gui.Component;

public class FillGridLayout extends Layout {
	protected final Map<Component, Integer> x = new HashMap<>();
	protected final Map<Component, Integer> y = new HashMap<>();
	protected int grid_x;
	protected int grid_y;

	public FillGridLayout(int grid_x, int grid_y) {
		super();
		this.grid_x = grid_x;
		this.grid_y = grid_y;
	}

	public void add(Component c, int x, int y) {
		components.add(c);
		this.x.put(c, x);
		this.y.put(c, y);

		float spacingX = getW() / (grid_x);
		float spacingY = getW() / (grid_y);
		
		c.setBounds(spacingX * x,
				spacingY * y,
				spacingX,
				spacingY);
	}

	public void add(Component c) {
		add(c, 0, 0);
	}

	@Override
	public void onResize(float ox, float oy, float w, float h) {
		for (Component c : components) {
			int x = this.x.get(c);
			int y = this.y.get(c);

			float spacingX = getW() / (grid_x);
			float spacingY = getW() / (grid_y);
			
			c.setBounds(spacingX * x,
					spacingY * y,
					spacingX,
					spacingY);
		}
	}
}

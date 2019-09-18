package io.github.tomaso2468.rpgonline.gui.layout;

import java.util.HashMap;
import java.util.Map;

import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.Component;

/**
 * A layout that spaces components on the outside grid of the screen. The components are pushed to the edge of the screen but the dimensions are not changed.
 * @author Tomas
 *
 */
public class OuterGridLayout extends Layout {
	/**
	 * The X position of the components.
	 */
	protected final Map<Component, Integer> x = new HashMap<>();
	/**
	 * The Y position of the components.
	 */
	protected final Map<Component, Integer> y = new HashMap<>();
	/**
	 * The grid width.
	 */
	protected int grid_x;
	/**
	 * The grid height.
	 */
	protected int grid_y;

	/**
	 * Constructs a new OuterGridLayout
	 * @param grid_x The grid width.
	 * @param grid_y The grid height.
	 */
	public OuterGridLayout(int grid_x, int grid_y) {
		super();
		this.grid_x = grid_x;
		this.grid_y = grid_y;
	}

	/**
	 * Adds a component to this container.
	 * @param c The component to add.
	 * @param x The X position of the component.
	 * @param y The Y position of the component.
	 */
	public void add(Component c, int x, int y) {
		components.add(c);
		this.x.put(c, x);
		this.y.put(c, y);

		Debugger.start("gui-layout");
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
		Debugger.stop("gui-layout");
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(Component c) {
		add(c, 0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResize(float ox, float oy, float w, float h) {
		Debugger.start("gui-layout");
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
		Debugger.stop("gui-layout");
	}
}

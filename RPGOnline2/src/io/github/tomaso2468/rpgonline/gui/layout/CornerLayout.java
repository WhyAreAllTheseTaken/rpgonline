package io.github.tomaso2468.rpgonline.gui.layout;

import java.util.HashMap;
import java.util.Map;

import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.Component;

/**
 * A layout that places UI elements in the corner.
 * @author Tomas
 *
 */
public class CornerLayout extends Layout {
	/**
	 * The map of components to their corners.
	 */
	private final Map<Component, Corner> map = new HashMap<>();

	/**
	 * Adds a component to the specified corner.
	 * @param c The component to add.
	 * @param corner The corner to add the component to.
	 */
	public void add(Component c, Corner corner) {
		components.add(c);
		map.put(c, corner);

		Debugger.start("gui-layout");
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
		Debugger.stop("gui-layout");
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(Component c) {
		add(c, Corner.TOP_LEFT);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResize(float x, float y, float w, float h) {
		Debugger.start("gui-layout");
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
		Debugger.stop("gui-layout");
	}
}

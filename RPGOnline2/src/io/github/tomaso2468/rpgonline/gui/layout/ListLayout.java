package io.github.tomaso2468.rpgonline.gui.layout;

import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.Component;

/**
 * A layout that places components in a vertical list.
 * @author Tomas
 *
 */
public class ListLayout extends Layout {
	/**
	 * The spacing of the layout.
	 */
	private final float spacing;
	
	/**
	 * Constructs a new ListLayout.
	 * @param spacing The spacing of the layout.
	 */
	public ListLayout(float spacing) {
		super();
		this.spacing = spacing;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Component c) {
		components.add(c);
		layout();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Component c) {
		super.remove(c);
		layout();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResize(float x, float y, float w, float h) {
		super.onResize(x, y, w, h);
		layout();
	}
	
	/**
	 * Updates the layout of this component.
	 */
	protected void layout() {
		Debugger.start("gui-layout");
		float y = 0;
		
		for (Component c : components) {
			c.setBounds(0, y, c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
			y += c.getDefaultBounds(this).getHeight() + spacing;
		}
		Debugger.stop("gui-layout");
	}
}

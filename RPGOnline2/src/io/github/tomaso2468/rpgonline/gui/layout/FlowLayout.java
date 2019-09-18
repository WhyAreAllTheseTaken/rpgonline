package io.github.tomaso2468.rpgonline.gui.layout;

import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.Component;

/**
 * A layout that fills components in the same way English text is written (left to right, going down to the next line when a line is full).
 * @author Tomas
 *
 */
public class FlowLayout extends Layout {
	/**
	 * The spacing of the layout.
	 */
	private final float spacing;
	
	/**
	 * Constructs a new FlowLayout.
	 * @param spacing The spacing of the layout.
	 */
	public FlowLayout(float spacing) {
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
		float x = 0;
		float y = 0;
		float rh = 0;
		
		for (Component c : components) {
			rh = Math.max(rh, c.getDefaultBounds(this).getHeight());
			
			if (c.getDefaultBounds(this).getWidth() + x > getW()) {
				y += rh + spacing;
				x = 0;
			}
			
			c.setBounds(x, y, c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
			x += c.getDefaultBounds(this).getWidth() + spacing;
		}
		Debugger.stop("gui-layout");
	}
}

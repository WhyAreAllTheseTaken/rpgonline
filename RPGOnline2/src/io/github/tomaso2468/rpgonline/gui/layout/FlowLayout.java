package io.github.tomaso2468.rpgonline.gui.layout;

import io.github.tomaso2468.rpgonline.gui.Component;

public class FlowLayout extends Layout {
	private final float spacing;
	
	public FlowLayout(float spacing) {
		super();
		this.spacing = spacing;
	}

	@Override
	public void add(Component c) {
		components.add(c);
		layout();
	}
	
	@Override
	public void remove(Component c) {
		super.remove(c);
		layout();
	}
	
	@Override
	public void onResize(float x, float y, float w, float h) {
		super.onResize(x, y, w, h);
		layout();
	}
	
	protected void layout() {
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
	}
}

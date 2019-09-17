package io.github.tomaso2468.rpgonline.gui.layout;

import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.Component;

public class ListLayout extends Layout {
	private final float spacing;
	
	public ListLayout(float spacing) {
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
		Debugger.start("gui-layout");
		float y = 0;
		
		for (Component c : components) {
			c.setBounds(0, y, c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
			y += c.getDefaultBounds(this).getHeight() + spacing;
		}
		Debugger.stop("gui-layout");
	}
}

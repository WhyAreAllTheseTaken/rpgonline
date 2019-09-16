package io.github.tomaso2468.rpgonline.gui;

import io.github.tomaso2468.rpgonline.gui.layout.Border;
import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class ScrollableContainer extends Container {
	private Container container;
	private ScrollBar bar;
	
	public ScrollableContainer() {
		Border border = new Border(ThemeManager.getTheme().getScrollableBorder());
		components.add(border);
		
		Container base_container = new Container() {
			@Override
			public void onResize(float x, float y, float w, float h) {
				super.onResize(x, y, w, h);
				
				if (container != null) {
					container.setBounds(0, -bar.getPos(), w - bar.getDefaultBounds(this).getWidth(), h);
					bar.setBounds(w - bar.getDefaultBounds(this).getWidth(), 0, bar.getDefaultBounds(this).getWidth(), h);
				}
			}
		};
		border.add(base_container);
		
		bar = new ScrollBar(0, 1) {
			@Override
			public void onUpdate(float pos, float max) {
				base_container.setBounds(base_container.getBounds());
			}
		};
		
		container = new Border(ThemeManager.getTheme().getScrollableBorder()) {
			@Override
			public void onResize(float x, float y, float w, float h) {
				super.onResize(x, y, w, h);
				
				bar.setMax(container.getH());
			}
		};
		base_container.add(container);
		
		base_container.setBounds(base_container.getBounds());
	}

}

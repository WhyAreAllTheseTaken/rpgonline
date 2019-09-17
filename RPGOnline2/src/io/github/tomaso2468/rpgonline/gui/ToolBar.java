package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.layout.FlowLayout;
import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class ToolBar extends FlowLayout {
	public ToolBar() {
		super(1);
	}
	
	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateToolBarBounds(c, this);
	}

}

package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.layout.FlowLayout;
import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A horizontal tool bar.
 * @author Tomas
 *
 */
public class ToolBar extends FlowLayout {
	/**
	 * Constructs a new ToolBar.
	 */
	public ToolBar() {
		super(1);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateToolBarBounds(c, this);
	}

}

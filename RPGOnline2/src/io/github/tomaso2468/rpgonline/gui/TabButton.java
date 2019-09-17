package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A button that acts as a tab.
 * @author Tomas
 *
 */
public class TabButton extends Button {

	/**
	 * Constructs a new tabbed buttons.
	 * @param text The text of the tab.
	 */
	public TabButton(String text) {
		super(text);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintTab(g, scaling, this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateTabBounds(c, this);
	}

}

package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A box that can be toggled.
 * @author Tomas
 *
 */
public class CheckBox extends ToggleButton {
	/**
	 * Constructs a new check box.
	 * @param text The text of the box.
	 * @param state The state of the box.
	 */
	public CheckBox(String text, boolean state) {
		super(text, state);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintCheckBox(g, scaling, this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateCheckBoxBounds(c, this);
	}
}

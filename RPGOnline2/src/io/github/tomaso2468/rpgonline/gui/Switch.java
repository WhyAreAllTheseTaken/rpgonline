package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A togglable switch.
 * @author Tomas
 *
 */
public class Switch extends ToggleButton {
	/**
	 * Constructs a new switch.
	 * @param state The state of the switch.
	 */
	public Switch(boolean state) {
		super("", state);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintSwitch(g, scaling, this);
	}

}

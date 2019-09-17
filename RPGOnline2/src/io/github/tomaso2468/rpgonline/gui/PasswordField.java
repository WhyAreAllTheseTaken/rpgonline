package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A text field that displays a password without showing text.
 * @author Tomas
 *
 */
public class PasswordField extends TextField {

	/**
	 * Constructs a new password field.
	 * @param text The text of the password field.
	 */
	public PasswordField(String text) {
		super(text);
	}

	/**
	 * Constructs a new password field.
	 */
	public PasswordField() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintPassword(g, scaling, this);
	}
}

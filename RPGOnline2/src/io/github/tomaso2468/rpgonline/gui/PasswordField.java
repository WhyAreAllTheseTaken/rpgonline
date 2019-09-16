package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class PasswordField extends TextField {

	public PasswordField(String text) {
		super(text);
	}

	public PasswordField() {
	}

	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintPassword(g, scaling, this);
	}
}

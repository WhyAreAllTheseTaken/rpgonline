package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class Switch extends ToggleButton {
	public Switch(boolean state) {
		super("", state);
	}
	
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintSwitch(g, scaling, this);
	}

}

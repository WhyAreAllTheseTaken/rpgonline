package io.github.tomaso2468.rpgonline.gui.layout;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.Container;

public class Layout extends Container {
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		paintComponents(g, scaling);
	}
}

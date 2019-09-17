package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Screen extends Container {

	public Screen() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		paintComponents(g, scaling);
	}

}

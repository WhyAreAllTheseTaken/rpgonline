package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * A representation of a GUI screen.
 * @author Tomas
 *
 */
public class Screen extends Container {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		paintComponents(g, scaling);
	}

}

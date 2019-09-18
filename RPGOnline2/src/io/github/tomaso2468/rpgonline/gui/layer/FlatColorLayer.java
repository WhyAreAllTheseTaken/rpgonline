package io.github.tomaso2468.rpgonline.gui.layer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * A layer with a flat color.
 * @author Tomas
 *
 */
public class FlatColorLayer extends Layer {
	/**
	 * The color of the layer.
	 */
	private Color c;

	/**
	 * Constructs a new flat color layer.
	 * @param c A color object.
	 */
	public FlatColorLayer(Color c) {
		super();
		this.c = c;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) {
		g.setColor(c);
		g.fillRect(0, 0, getW() * scaling, getH() * scaling);
	}
}

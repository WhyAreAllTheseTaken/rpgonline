package io.github.tomaso2468.rpgonline.gui.layer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * A layer that applies a fade effect.
 * @author Tomas
 *
 */
public class FadeLayer extends Layer {
	/**
	 * The color of the fade.
	 */
	private Color c;
	/**
	 * The amount to fade by.
	 */
	private float fade;
	
	/**
	 * Constructs a new fade layer.
	 * @param c The color of the fade layer.
	 * @param fade The strength of the fade 0..1.
	 */
	public FadeLayer(Color c, float fade) {
		super();
		this.c = c;
		this.fade = fade;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) {
		g.setColor(new Color(c.r, c.g, c.b, fade));
		g.fillRect(0, 0, getW() * scaling, getH() * scaling);
	}
}

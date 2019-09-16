package io.github.tomaso2468.rpgonline.gui.layer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class FlatColorLayer extends Layer {
	private Color c;

	public FlatColorLayer(Color c) {
		super();
		this.c = c;
	}
	
	@Override
	public void paint(Graphics g, float scaling) {
		g.setColor(c);
		g.fillRect(0, 0, getW() * scaling, getH() * scaling);
	}
}

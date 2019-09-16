package io.github.tomaso2468.rpgonline.gui.layer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class FadeLayer extends Layer {
	private Color c;
	private float fade;
	
	public FadeLayer(Color c, float fade) {
		super();
		this.c = c;
		this.fade = fade;
	}
	
	@Override
	public void paint(Graphics g, float scaling) {
		g.setColor(new Color(c.r, c.g, c.b, fade));
		g.fillRect(0, 0, getW() * scaling, getH() * scaling);
	}
}

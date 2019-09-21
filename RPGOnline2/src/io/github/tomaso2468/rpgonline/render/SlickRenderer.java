package io.github.tomaso2468.rpgonline.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class SlickRenderer implements Renderer {
	@Override
	public void renderEmbedded(Image img, float x, float y, float w, float h) {
		img.drawEmbedded(x, y, w, h);
	}

	@Override
	public void render(Image img, float x, float y, float w, float h) {
		img.draw(x, y, w, h);
	}

	@Override
	public void startUse(Image img) {
		img.startUse();
	}

	@Override
	public void endUse(Image img) {
		img.endUse();
	}

	@Override
	public void renderFiltered(Image img, float x, float y, float w, float h, Color c) {
		img.draw(x, y, w, h, c);
	}

	@Override
	public void renderSheared(Image img, float x, float y, float w, float h, float hshear, float vshear) {
		img.drawSheared(x, y, hshear, vshear);
	}

	@Override
	public void translate(Graphics g, float x, float y) {
		g.translate(x, y);
	}

	@Override
	public void scale(Graphics g, float x, float y) {
		g.scale(x, y);
	}

	@Override
	public SlickGraphics getGUIGraphics(Graphics g) {
		return new SlickGraphics(g);
	}

}

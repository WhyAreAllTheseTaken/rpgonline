package io.github.tomaso2468.rpgonline.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

class SlickGraphics implements Graphics {
	private org.newdawn.slick.Graphics g;

	public SlickGraphics(org.newdawn.slick.Graphics g) {
		super();
		this.g = g;
	}

	@Override
	public void setColor(Color c) {
		g.setColor(c);
	}

	@Override
	public void drawRect(float x, float y, float w, float h) {
		g.drawRect(x, y, w, h);
	}

	@Override
	public void translate(float x, float y) {
		g.translate(x, y);
	}

	@Override
	public void pushTransform() {
		g.pushTransform();
	}

	@Override
	public void popTransform() {
		g.popTransform();
	}

	@Override
	public void scale(float x, float y) {
		g.scale(x, y);
	}

	@Override
	public void fillRect(float x, float y, float w, float h) {
		g.fillRect(x, y, w, h);
	}

	@Override
	public void drawImage(Image img, float x, float y) {
		g.drawImage(img, x, y);
	}

	@Override
	public org.newdawn.slick.Graphics beginSlick() {
		return g;
	}

	@Override
	public void endSlick() {
		
	}

	@Override
	public Font getFont() {
		return g.getFont();
	}

	@Override
	public void drawString(String str, float x, float y) {
		g.drawString(str, x, y);
	}

	@Override
	public void fillOval(float x, float y, float w, float h) {
		g.fillOval(x, y, w, h);
	}

	@Override
	public void drawOval(float x, float y, float w, float h) {
		g.drawOval(x, y, w, h);
	}

	@Override
	public void rotate(float a) {
		g.rotate(0, 0, a);
	}

	@Override
	public void drawLine(float x, float y, float x2, float y2) {
		g.drawLine(x, y, x2, y2);
	}

}

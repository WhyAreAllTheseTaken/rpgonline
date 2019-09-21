package io.github.tomaso2468.rpgonline.render;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

public class GL11Graphics implements Graphics {
	private org.newdawn.slick.Graphics g;
	private Color c;

	public GL11Graphics(org.newdawn.slick.Graphics g) {
		super();
		this.g = g;
		this.c = g.getColor();
	}

	@Override
	public void setColor(Color c) {
		this.c = c;
		GL11.glColor4f(c.r, c.g, c.b, c.a);
		g.setColor(c);
	}

	@Override
	public void drawRect(float x, float y, float w, float h) {
		drawLine(x, y, x + w, y);
		drawLine(x + w, y, x + w, y + h);
		drawLine(x + w, y + h, x, y + h);
		drawLine(x, y + h, x, y);
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
		GL11.glBegin(GL11.GL_QUADS);
		c.bind();
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x + w, y);
		GL11.glVertex2f(x + w, y + h);
		GL11.glVertex2f(x, y + h);
		GL11.glEnd();
	}

	@Override
	public void drawImage(Image img, float x, float y) {
		RenderManager.getRenderer().render(img, x, y, img.getWidth(), img.getHeight());
	}

	@Override
	public org.newdawn.slick.Graphics beginSlick() {
		return g;
	}

	@Override
	public void endSlick() {
		c.bind();
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
		GL11.glRotatef(a, 0, 0, 0);
	}

	@Override
	public void drawLine(float x, float y, float x2, float y2) {
		GL11.glBegin(GL11.GL_LINE_STRIP);
		
		GL11.glColor4f(c.r, c.g, c.b, c.a);
		
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x2, y2);
		
		GL11.glEnd();
	}
}

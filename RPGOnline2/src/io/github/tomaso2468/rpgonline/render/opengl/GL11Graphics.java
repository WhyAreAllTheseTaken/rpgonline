package io.github.tomaso2468.rpgonline.render.opengl;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.Font;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderMode;
import io.github.tomaso2468.rpgonline.render.Renderer;

class GL11Graphics implements Graphics {
	private GL11Renderer renderer;
	private Color color;
	public GL11Graphics(GL11Renderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public void setColor(Color c) {
		this.color = c;
	}
	
	private RenderMode mode;
	protected void store() {
		mode = renderer.getMode();
	}
	protected void load() {
		renderer.setMode(mode);
	}

	@Override
	public void drawRect(float x, float y, float w, float h) {
		store();
		renderer.setMode(RenderMode.MODE_2D_LINES_NOVBO);
		renderer.draw(new Rectangle(x, y, w, h), color);
		load();
	}

	@Override
	public void fillRect(float x, float y, float w, float h) {
		store();
		renderer.setMode(RenderMode.MODE_2D_COLOR_NOVBO);
		renderer.fill(new Rectangle(x, y, w, h), color);
		load();
	}

	@Override
	public void drawOval(float x, float y, float w, float h) {
		store();
		renderer.setMode(RenderMode.MODE_2D_LINES_NOVBO);
		renderer.draw(new Ellipse(x, y, w, h), color);
		load();
	}

	@Override
	public void fillOval(float x, float y, float w, float h) {
		store();
		renderer.setMode(RenderMode.MODE_2D_COLOR_NOVBO);
		renderer.fill(new Ellipse(x, y, w, h), color);
		load();
	}

	@Override
	public void drawLine(float x, float y, float x2, float y2) {
		store();
		renderer.setMode(RenderMode.MODE_2D_LINES_NOVBO);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x2, y2);
		GL11.glEnd();
		load();
	}

	@Override
	public void translate(float x, float y) {
		renderer.translate2D(x, y);
	}

	@Override
	public void scale(float x, float y) {
		renderer.scale2D(x, y);
	}

	@Override
	public void rotate(float a) {
		renderer.rotate2D(0, 0, a);
	}

	@Override
	public void pushTransform() {
		renderer.pushTransform();
	}

	@Override
	public void popTransform() {
		renderer.popTransform();
	}

	@Override
	public void drawImage(Image img, float x, float y) {
		store();
		renderer.setMode(RenderMode.MODE_2D_SPRITE_NOVBO);
		renderer.drawImage(img, x, y);
		load();
	}

	@Override
	public Font getFont() {
		return renderer.getFont();
	}
	
	@Override
	public void setFont(Font font) {
		renderer.setFont(font);
	}

	@Override
	public void drawString(String str, float x, float y) {
		store();
		renderer.setMode(RenderMode.MODE_2D_SPRITE_NOVBO);
		((SlickFont) renderer.getFont()).font.drawString(x, y, str);
		load();
	}

	@Override
	public void copyArea(Image buffer, int x, int y) {
		renderer.copyArea(buffer, x, y);
	}

	@Override
	public Renderer getRenderer() {
		return renderer;
	}

	@Override
	public void resetTransform() {
		renderer.resetTransform();
	}

}

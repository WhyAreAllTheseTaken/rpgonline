package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public abstract class Component {
	private float x = 0;
	private float y = 0;
	private float w = 360;
	private float h = 360;
	
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintComponent(g, scaling, this);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getW() {
		return w;
	}

	public float getH() {
		return h;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}
	
	public Rectangle getDefaultBounds(Container c) {
		return c.getBounds();
	}
	
	public void mouseClicked(float x, float y) {
		
	}
	
	public void mousePressed(float x, float y) {
		
	}
	
	public void mouseUnpressed(float x, float y) {
		
	}
	
	public void mouseEntered(float x, float y) {
		
	}
	
	public void mouseExited(float x, float y) {
		
	}
	
	public void mouseMoved(float ox, float oy, float nx, float ny) {
		
	}
	
	public void mouseDragged(float ox, float oy, float nx, float ny) {
		
	}
	
	public void mouseWheel(float scroll) {
		
	}

	public void update() {
		
	}

	public void setBounds(Rectangle r) {
		setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}
	
	public void setBounds(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		onResize(x, y, w, h);
	}
	
	public void onResize(float x, float y, float w, float h) {
		
	}
}

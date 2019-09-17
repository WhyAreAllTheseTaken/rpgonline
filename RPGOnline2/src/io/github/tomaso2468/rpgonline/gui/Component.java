package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A class representing a GUI component.
 * @author Tomas
 *
 */
public abstract class Component {
	/**
	 * The X position of the component.
	 */
	private float x = 0;
	/**
	 * The y position of the component.
	 */
	private float y = 0;
	/**
	 * The width of the component.
	 */
	private float w = 360;
	/**
	 * The height of the component.
	 */
	private float h = 360;
	
	/**
	 * Paints the component.
	 * @param g The graphics context.
	 * @param scaling The scaling factor for rendering.
	 * @throws SlickException If an error occurs rendering graphics.
	 */
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintComponent(g, scaling, this);
	}

	/**
	 * Gets the X position of this component.
	 * @return A float value.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Gets the Y position of this component.
	 * @return A float value.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Gets the width of this component.
	 * @return A positive float value.
	 */
	public float getW() {
		return w;
	}

	/**
	 * Gets the height of this component.
	 * @return A positive float value.
	 */
	public float getH() {
		return h;
	}
	
	/**
	 * Gets the bounds of this component.
	 * @return A rectangle object.
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}
	
	/**
	 * Gets the default bounds of this component.
	 * @param c The container holding this component.
	 * @return A rectangle object.
	 */
	public Rectangle getDefaultBounds(Container c) {
		return c.getBounds();
	}
	
	/**
	 * Mouse click event for button 0 (left click).
	 * @param x The X position of the mouse with scaling.
	 * @param y The Y position of the mouse with scaling.
	 */
	public void mouseClickedLeft(float x, float y) {
		
	}
	
	/**
	 * Mouse press event for button 0 (left click).
	 * @param x The X position of the mouse with scaling.
	 * @param y The Y position of the mouse with scaling.
	 */
	public void mousePressedLeft(float x, float y) {
		
	}
	
	/**
	 * Mouse unpress event for button 0 (left click).
	 * @param x The X position of the mouse with scaling.
	 * @param y The Y position of the mouse with scaling.
	 */
	public void mouseUnpressedLeft(float x, float y) {
		
	}
	
	/**
	 * Mouse drag event for button 0 (left click).
	 * @param ox The previous X position of the mouse with scaling.
	 * @param oy The previous Y position of the mouse with scaling.
	 * @param nx The new X position of the mouse with scaling.
	 * @param ny The new Y position of the mouse with scaling.
	 */
	public void mouseDraggedLeft(float ox, float oy, float nx, float ny) {
		
	}
	
	/**
	 * Mouse click event for button 1 (right click).
	 * @param x The X position of the mouse with scaling.
	 * @param y The Y position of the mouse with scaling.
	 */
	public void mouseClickedRight(float x, float y) {
		
	}
	
	/**
	 * Mouse press event for button 1 (right click).
	 * @param x The X position of the mouse with scaling.
	 * @param y The Y position of the mouse with scaling.
	 */
	public void mousePressedRight(float x, float y) {
		
	}
	
	/**
	 * Mouse unpress event for button 1 (right click).
	 * @param x The X position of the mouse with scaling.
	 * @param y The Y position of the mouse with scaling.
	 */
	public void mouseUnpressedRight(float x, float y) {
		
	}
	
	/**
	 * Mouse drag event for button 1 (right click).
	 * @param ox The previous X position of the mouse with scaling.
	 * @param oy The previous Y position of the mouse with scaling.
	 * @param nx The new X position of the mouse with scaling.
	 * @param ny The new Y position of the mouse with scaling.
	 */
	public void mouseDraggedRight(float ox, float oy, float nx, float ny) {
		
	}
	
	/**
	 * Mouse click event for button 2 (middle click).
	 * @param x The X position of the mouse with scaling.
	 * @param y The Y position of the mouse with scaling.
	 */
	public void mouseClickedMiddle(float x, float y) {
		
	}
	
	/**
	 * Mouse press event for button 2 (middle click).
	 * @param x The X position of the mouse with scaling.
	 * @param y The Y position of the mouse with scaling.
	 */
	public void mousePressedMiddle(float x, float y) {
		
	}
	
	/**
	 * Mouse unpress event for button 2 (middle click).
	 * @param x The X position of the mouse with scaling.
	 * @param y The Y position of the mouse with scaling.
	 */
	public void mouseUnpressedMiddle(float x, float y) {
		
	}
	
	/**
	 * Mouse drag event for button 2 (middle click).
	 * @param ox The previous X position of the mouse with scaling.
	 * @param oy The previous Y position of the mouse with scaling.
	 * @param nx The new X position of the mouse with scaling.
	 * @param ny The new Y position of the mouse with scaling.
	 */
	public void mouseDraggedMiddle(float ox, float oy, float nx, float ny) {
		
	}
	
	/**
	 * Mouse event for a component being selected.
	 * @param x The X position of the mouse with scaling.
	 * @param y The Y position of the mouse with scaling.
	 */
	public void mouseEntered(float x, float y) {
		
	}
	
	/**
	 * Mouse event for a component being deselected.
	 * @param x The X position of the mouse with scaling.
	 * @param y The Y position of the mouse with scaling.
	 */
	public void mouseExited(float x, float y) {
		
	}
	
	/**
	 * Mouse move event.
	 * @param ox The previous X position of the mouse with scaling.
	 * @param oy The previous Y position of the mouse with scaling.
	 * @param nx The new X position of the mouse with scaling.
	 * @param ny The new Y position of the mouse with scaling.
	 */
	public void mouseMoved(float ox, float oy, float nx, float ny) {
		
	}
	
	/**
	 * Mouse scroll event.
	 * @param scroll The amount to scroll by.
	 */
	public void mouseWheel(float scroll) {
		
	}

	/**
	 * Component update (for keyboard profiling and real-time updates).
	 */
	public void update() {
		
	}

	/**
	 * Sets the bounds of this component.
	 * @param r A rectangle object.
	 */
	public void setBounds(Rectangle r) {
		setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}
	
	/**
	 * Sets the bounds of this component.
	 * @param x The X position of this component.
	 * @param y The Y position of this component.
	 * @param w The width of this component.
	 * @param h The height of this component.
	 */
	public void setBounds(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		onResize(x, y, w, h);
	}
	
	/**
	 * The resize event for this component.
	 * @param x The X position of this component.
	 * @param y The Y position of this component.
	 * @param w The width of this component.
	 * @param h The height of this component.
	 */
	public void onResize(float x, float y, float w, float h) {
		
	}
}

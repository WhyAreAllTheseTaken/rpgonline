/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
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
	 * Paints a debug of the component.
	 * @param g The graphics context.
	 * @param scaling The scaling factor for rendering.
	 * @throws SlickException If an error occurs rendering graphics.
	 */
	public void debug(Graphics g, float scaling) {
		g.setColor(Color.white);
		g.drawRect(0, 0, getW() * scaling, getH() * scaling);
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
	public void update(float delta) {
		
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
	
	/**
	 * Updates the game container.
	 * @param c A game container object.
	 */
	public void containerUpdate(GameContainer c) {
		
	}
}

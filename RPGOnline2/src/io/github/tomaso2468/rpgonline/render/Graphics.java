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
package io.github.tomaso2468.rpgonline.render;

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.Font;
import io.github.tomaso2468.rpgonline.Image;

/**
 * The interface for GUI graphics.
 * @author Tomaso2468
 *
 */
public interface Graphics {
	/**
	 * Sets the graphics color.
	 * @param c The color.
	 */
	public void setColor(Color c);
	
	/**
	 * Draws a rectangle as lines.
	 * @param x The X position of the rectangle.
	 * @param y The Y position of the rectangle.
	 * @param w The width of the rectangle.
	 * @param h The height of the rectangle.
	 */
	public void drawRect(float x, float y, float w, float h);
	/**
	 * Fills a rectangle.
	 * @param x The X position of the rectangle.
	 * @param y The Y position of the rectangle.
	 * @param w The width of the rectangle.
	 * @param h The height of the rectangle.
	 */
	public void fillRect(float x, float y, float w, float h);
	
	/**
	 * Draws an oval as lines.
	 * @param x The X position of the rectangle.
	 * @param y The Y position of the rectangle.
	 * @param w The width of the rectangle.
	 * @param h The height of the rectangle.
	 */
	public void drawOval(float x, float y, float w, float h);
	/**
	 * Fills an oval.
	 * @param x The X position of the rectangle.
	 * @param y The Y position of the rectangle.
	 * @param w The width of the rectangle.
	 * @param h The height of the rectangle.
	 */
	public void fillOval(float x, float y, float w, float h);
	
	/**
	 * Draws a line.
	 * @param x The first X position.
	 * @param y The first Y position.
	 * @param x2 The second X position.
	 * @param y2 The second Y position.
	 */
	public void drawLine(float x, float y, float x2, float y2);
	
	/**
	 * Translates the graphics.
	 * @param x The X translation.
	 * @param y The Y translation.
	 */
	public void translate(float x, float y);
	/**
	 * Scales the graphics.
	 * @param x The X scale.
	 * @param y The Y scale.
	 */
	public void scale(float x, float y);
	/**
	 * Rotates the graphics.
	 * @param a An angle in degrees.
	 */
	public void rotate(float a);
	/**
	 * Pushes the transform.
	 */
	public void pushTransform();
	/**
	 * Pops the transform.
	 */
	public void popTransform();
	
	/**
	 * Draws an image.
	 * @param img The image to draw.
	 * @param x The X position of the image.
	 * @param y The Y position of the image.
	 */
	public void drawImage(Image img, float x, float y);
	
	/**
	 * Gets the current font.
	 * @return A font object.
	 */
	public Font getFont();
	
	/**
	 * Sets the current font.
	 */
	public void setFont(Font font);
	/**
	 * Draws the string.
	 * @param str The text to draw.
	 * @param x The X position.
	 * @param y The Y position.
	 */
	public void drawString(String str, float x, float y);

	public void copyArea(Image buffer, int x, int y);

	public Renderer getRenderer();

	public void resetTransform();
}

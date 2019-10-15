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

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

/**
 * OpenGL graphics.
 * @author Tomaso2468
 *
 */
class GL11Graphics implements Graphics {
	/**
	 * The color of the graphics.
	 */
	private Color c;

	/**
	 * Constructs a new GL11Graphics context.
	 */
	public GL11Graphics() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setColor(Color c) {
		this.c = c;
		GL11.glColor4f(c.r, c.g, c.b, c.a);
		g.setColor(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void drawRect(float x, float y, float w, float h) {
		drawLine(x, y, x + w, y);
		drawLine(x + w, y, x + w, y + h);
		drawLine(x + w, y + h, x, y + h);
		drawLine(x, y + h, x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void translate(float x, float y) {
		g.translate(x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pushTransform() {
		g.pushTransform();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void popTransform() {
		g.popTransform();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void scale(float x, float y) {
		g.scale(x, y);
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void drawImage(Image img, float x, float y) {
		RenderManager.getRenderer().render(img, x, y, img.getWidth(), img.getHeight());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.newdawn.slick.Graphics beginSlick() {
		return g;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endSlick() {
		c.bind();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Font getFont() {
		return g.getFont();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void drawString(String str, float x, float y) {
		g.drawString(str, x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fillOval(float x, float y, float w, float h) {
		g.fillOval(x, y, w, h);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void drawOval(float x, float y, float w, float h) {
		g.drawOval(x, y, w, h);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void rotate(float a) {
		GL11.glRotatef(a, 0, 0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void drawLine(float x, float y, float x2, float y2) {
		GL11.glBegin(GL11.GL_LINE_STRIP);
		
		GL11.glColor4f(c.r, c.g, c.b, c.a);
		
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x2, y2);
		
		GL11.glEnd();
	}
}

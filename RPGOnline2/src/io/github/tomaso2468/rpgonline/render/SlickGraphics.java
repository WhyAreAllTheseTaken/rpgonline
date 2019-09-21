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
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

/**
 * Slick2D graphics.
 * @author Tomas
 *
 */
class SlickGraphics implements Graphics {
	/**
	 * The Slick2D graphics.
	 */
	private org.newdawn.slick.Graphics g;

	/**
	 * Constructs a new graphics context.
	 * @param g The Slick2D graphics.
	 */
	public SlickGraphics(org.newdawn.slick.Graphics g) {
		super();
		this.g = g;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setColor(Color c) {
		g.setColor(c);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void drawRect(float x, float y, float w, float h) {
		g.drawRect(x, y, w, h);
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
		g.fillRect(x, y, w, h);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void drawImage(Image img, float x, float y) {
		g.drawImage(img, x, y);
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
		g.rotate(0, 0, a);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void drawLine(float x, float y, float x2, float y2) {
		g.drawLine(x, y, x2, y2);
	}

}

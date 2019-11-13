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
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.Font;
import io.github.tomaso2468.rpgonline.Image;

public class BasicGraphics implements Graphics {
	private Renderer renderer;
	private Color color;
	public BasicGraphics(Renderer renderer) {
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
		renderer.renderLine(x, y, x2, y2, color);
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
		renderer.drawFont(renderer.getFont(), x, y, str);
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

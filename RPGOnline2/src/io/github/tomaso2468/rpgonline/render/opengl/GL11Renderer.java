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
package io.github.tomaso2468.rpgonline.render.opengl;

import java.awt.FontFormatException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.TextureLoader;

import io.github.tomaso2468.rpgonline.Font;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.TextureReference;
import io.github.tomaso2468.rpgonline.render.BasicGraphics;
import io.github.tomaso2468.rpgonline.render.ColorMode;
import io.github.tomaso2468.rpgonline.render.FeatureException;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.RenderMode;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.render.Shader;
import io.github.tomaso2468.rpgonline.render.TextureCreateException;
import io.github.tomaso2468.rpgonline.render.java2d.AWTResourceException;

public abstract class GL11Renderer implements Renderer {
	private RenderMode mode = RenderMode.MODE_NONE;
	private ColorMode colorMode = ColorMode.NORMAL;
	private boolean pushed;
	private List<FloatBuffer> stack = new ArrayList<>();
	private int stackIndex;
	private float sx, sy;
	private Font font;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderEmbedded(Image img, float x, float y, float w, float h) {
		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY());
		GL11.glVertex3f(x, y, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY() + img.getTextureHeight());
		GL11.glVertex3f(x, y + h, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(),
				img.getTextureOffsetY() + img.getTextureHeight());
		GL11.glVertex3f(x + w, y + h, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(), img.getTextureOffsetY());
		GL11.glVertex3f(x + w, y, 0);
	}
	
	@Override
	public void renderLine(float x, float y, float x2, float y2, Color c) {
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor4f(c.r, c.g, c.b, c.a);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x2, y2);
		GL11.glEnd();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Image img, float x, float y, float w, float h) {
		startUse(img);
		renderEmbedded(img, x, y, w, h);
		endUse(img);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startUse(Image img) {
		GL11.glColor4f(1, 1, 1, 1);
		img.getTexture().bind();
		GL11.glBegin(GL11.GL_QUADS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endUse(Image img) {
		GL11.glEnd();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderFiltered(Image img, float x, float y, float w, float h, Color c) {
		startUse(img);

		GL11.glColor4f(c.r, c.g, c.b, c.a);

		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY());
		GL11.glVertex3f(x, y, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY() + img.getTextureHeight());
		GL11.glVertex3f(x, y + h, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(),
				img.getTextureOffsetY() + img.getTextureHeight());
		GL11.glVertex3f(x + w, y + h, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(), img.getTextureOffsetY());
		GL11.glVertex3f(x + w, y, 0);

		endUse(img);
	}

	@Override
	public void renderSheared(Image img, float x, float y, float w, float h, float hshear, float vshear) {
		startUse(img);

		renderShearedEmbedded(img, x, y, w, h, hshear, vshear);

		endUse(img);
	}
	
	@Override
	public void renderShearedEmbedded(Image img, float x, float y, float w, float h, float hshear, float vshear) {
		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY());
		GL11.glVertex3f(x, y, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX(), img.getTextureOffsetY() + img.getTextureHeight());
		GL11.glVertex3f(x, y + h, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(),
				img.getTextureOffsetY() + img.getTextureHeight());
		GL11.glVertex3f(x + w + hshear, y + h + vshear, 0);
		GL11.glTexCoord2f(img.getTextureOffsetX() + img.getTextureWidth(), img.getTextureOffsetY());
		GL11.glVertex3f(x + w, y, 0);
	}

	@Override
	public void drawQuad(float x, float y, float w, float h, Color c) {
		// Fallthrough is used here. Later java versions could use a newer switch
		// statement.
		switch (mode) {
		case MODE_2D_COLOR_NOVBO:
		case MODE_2D_LINES_NOVBO:
		case MODE_2D_SPRITE_NOVBO:
			GL11.glColor4f(c.r, c.g, c.b, c.a);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + w, y);
			GL11.glVertex2f(x + w, y + h);
			GL11.glVertex2f(x, y + h);
			GL11.glEnd();
			break;
		case MODE_NONE:
			break;
		default:
			break;
		}
	}

	@Override
	public void translate2D(float x, float y) {
		if (!pushed) {
			GL11.glPushMatrix();
			pushed = true;
		}
		GL11.glTranslatef(x, y, 0);
	}

	@Override
	public void scale2D(float x, float y) {
		if (!pushed) {
			GL11.glPushMatrix();
			pushed = true;
		}
		GL11.glScalef(x, y, 1);
		this.sx *= x;
		this.sy *= y;
	}

	@Override
	public void rotate2D(float x, float y, float a) {
		if (!pushed) {
			GL11.glPushMatrix();
			pushed = true;
		}
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(a, 0, 0, a);
		GL11.glTranslatef(-x, -y, 0);
	}

	@Override
	public void transform2D(Transform trans) {
		float[] tm = trans.getMatrixPosition(); // get the transform matrix

		// pad the transform to get a 4x4 3d affine transform
		float[] toBuffer = { tm[0], tm[3], 0, tm[6], tm[1], tm[4], 0, tm[7], 0, 0, 1, 0, tm[2], tm[5], 0, 1 };

		// GL11 wants a "direct" FloatBuffer, but you can only get that by creating a
		// direct ByteBuffer
		// and then creating a FloatBuffer as a view of that ByteBuffer.
		// the ByteBuffer is allocated 16*4 bytes, because there are 16 floats and each
		// float needs 4 bytes
		ByteBuffer bb = ByteBuffer.allocateDirect(16 * 4);

		// this has something to do with the default byte order setting in Java being
		// inappropriate
		bb.order(ByteOrder.nativeOrder());

		for (float f : toBuffer) {
			bb.putFloat(f);
		}
		bb.rewind();
		FloatBuffer transformBuffer = bb.asFloatBuffer();

		GL11.glMultMatrix(transformBuffer);
	}

	@Override
	public void resetTransform() {
		sx = 1;
		sy = 1;

		if (pushed) {
			GL11.glPopMatrix();
			pushed = false;
		}
	}

	@Override
	public void pushTransform() {
		FloatBuffer buffer;
		if (stackIndex >= stack.size()) {
			buffer = BufferUtils.createFloatBuffer(18);
			stack.add(buffer);
		} else {
			buffer = (FloatBuffer) stack.get(stackIndex);
		}

		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, buffer);
		buffer.put(16, sx);
		buffer.put(17, sy);
		stackIndex += 1;
	}

	@Override
	public void popTransform() {
		stackIndex -= 1;
		FloatBuffer oldBuffer = (FloatBuffer) stack.get(stackIndex);
		GL11.glLoadMatrix(oldBuffer);
		sx = oldBuffer.get(16);
		sy = oldBuffer.get(17);
	}

	@Override
	public Graphics getGUIGraphics() {
		return new BasicGraphics(this);
	}

	@Override
	public void clear() {
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public RenderMode getMode() {
		return mode;
	}

	@Override
	public void setMode(RenderMode mode) {
		if (this.mode == mode) {
			return;
		}
		this.mode = mode;
		if (this.mode == RenderMode.MODE_2D_COLOR_NOVBO && mode == RenderMode.MODE_2D_LINES_NOVBO) {
			return;
		}
		if (this.mode == RenderMode.MODE_2D_LINES_NOVBO && mode == RenderMode.MODE_2D_COLOR_NOVBO) {
			return;
		}

		if (mode == RenderMode.MODE_2D_COLOR_NOVBO || mode == RenderMode.MODE_2D_LINES_NOVBO) {
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, getRenderWidth(), getRenderHeight(), 0, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);

			GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			GL11.glClearDepth(1);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		} else if (mode == RenderMode.MODE_2D_SPRITE_NOVBO) {
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, getRenderWidth(), getRenderHeight(), 0, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);

			GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			GL11.glClearDepth(1);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		} else if (mode == RenderMode.MODE_NONE) {
			// Do nothing
		}
	}

	@Override
	public TextureReference getPNG(URL url) throws IOException {
		return new SlickTexture(TextureLoader.getTexture("PNG", new BufferedInputStream(url.openStream())));
	}

	@Override
	public void copyArea(Image buffer, int x, int y) {
		Texture texture = ((SlickTexture) buffer.getTexture()).texture;
		int format = texture.hasAlpha() ? GL11.GL_RGBA : GL11.GL_RGB;
		texture.bind();
		GL11.glCopyTexImage2D(GL11.GL_TEXTURE_2D, 0, format, x, /*getHeight() - (y + (int) buffer.getHeight())*/0,
				texture.getTextureWidth(), texture.getTextureHeight(), 0);
		buffer.ensureInverted();
	}

	@Override
	public TextureReference createEmptyTexture(int width, int height) throws TextureCreateException {
		try {
			return new SlickTexture(InternalTextureLoader.get().createTexture(width, height, Image.FILTER_NEAREST));
		} catch (IOException e) {
			throw new TextureCreateException("Failed to create empty image", e);
		}
	}

	@Override
	public String getVendor() {
		return GL11.glGetString(GL11.GL_VENDOR);
	}

	@Override
	public String getVersion() {
		return GL11.glGetString(GL11.GL_VERSION);
	}

	@Override
	public String getRendererGL() {
		return "OpenGL";
	}

	@Override
	public void setColorMode(ColorMode mode) {
		if (this.colorMode == mode) {
			return;
		}
		this.colorMode = mode;

		switch (mode) {
		case ADD:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColorMask(true, true, true, true);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			break;
		case ALPHA_BLEND:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColorMask(true, true, true, false);
			GL11.glBlendFunc(GL11.GL_DST_ALPHA, GL11.GL_ONE_MINUS_DST_ALPHA);
			break;
		case ALPHA_MAP:
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColorMask(false, false, false, true);
			break;
		case MULTIPLY:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColorMask(true, true, true, true);
			GL11.glBlendFunc(GL11.GL_ONE_MINUS_SRC_COLOR, GL11.GL_SRC_COLOR);
			break;
		case NORMAL:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColorMask(true, true, true, true);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			break;
		case SCREEN:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColorMask(true, true, true, true);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_COLOR);
			break;
		default:
			break;
		}
	}

	@Override
	public ColorMode getColorMode() {
		return colorMode;
	}

	@Override
	public void draw(Shape shape, Color color) {
		TextureImpl.bindNone();
		color.bind();
		ShapeRenderer.draw(shape);
	}

	@Override
	public void fill(Shape shape, Color color) {
		TextureImpl.bindNone();
		color.bind();
		ShapeRenderer.fill(shape);
	}

	@Override
	public Font getFont() {
		return font;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Font loadFont(String name, int type, float size, int[] codepages) throws RenderException {
		java.awt.Font awtFont = new java.awt.Font(name, type, (int) size);

		UnicodeFont uniFont = new UnicodeFont(awtFont);
		uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
		uniFont.addAsciiGlyphs();
		for (int i = 0; i < codepages.length; i++) {
			uniFont.addGlyphs(codepages[i] << 8, codepages[i] << 8 + 0xFF);
		}
		try {
			uniFont.loadGlyphs();
		} catch (SlickException e) {
			throw new SlickResourceException("Error loading font", e);
		}
		
		return new SlickFont(uniFont);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Font loadFont(URL url, int type, float size, int[] codepages) throws RenderException {
		java.awt.Font awtFont;
		try {
			awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, url.openStream()).deriveFont(type, size);
		} catch (FontFormatException | IOException e) {
			throw new AWTResourceException("Error loading font", e);
		}
		
		UnicodeFont uniFont = new UnicodeFont(awtFont);
		uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
		uniFont.addAsciiGlyphs();
		for (int i = 0; i < codepages.length; i++) {
			uniFont.addGlyphs(codepages[i] << 8, codepages[i] << 8 + 0xFF);
		}
		try {
			uniFont.loadGlyphs();
		} catch (SlickException e) {
			throw new SlickResourceException("Error loading font", e);
		}
		
		return new SlickFont(uniFont);
	}
	
	@Override
	public void setFilter(TextureReference texture, int filterMode) {
		int filter = filterMode == Image.FILTER_LINEAR ? GL11.GL_LINEAR : GL11.GL_NEAREST;
		((SlickTexture) texture).texture.bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter); 
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
	}
	
	@Override
	public Shader createShader(URL vertex, URL fragment) throws RenderException {
		throw new FeatureException("Shaders are not supported by OpenGL 1.1");
	}
	
	@Override
	public void useShader(Shader shader) throws RenderException {
		throw new FeatureException("Shaders are not supported by OpenGL 1.1");
	}
	
	@Override
	public void deleteShader(Shader shader) throws RenderException {
		throw new FeatureException("Shaders are not supported by OpenGL 1.1");
	}
	
	@Override
	public void drawFont(Font font, float x, float y, String str) {
		((SlickFont) font).font.drawString(x, y, str);
	}
}

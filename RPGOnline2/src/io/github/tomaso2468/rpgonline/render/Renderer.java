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

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import io.github.tomaso2468.rpgonline.Font;
import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.TextureReference;
import io.github.tomaso2468.rpgonline.input.Input;
import io.github.tomaso2468.rpgonline.post.LUT;

/**
 * An interface representing a renderer.
 * @author Tomaso2468
 *
 */
public interface Renderer {
	/**
	 * Renders a texture.
	 * @param img The texture to render.
	 * @param x The X position of the texture.
	 * @param y The Y position of the texture.
	 * @param w The width of the texture.
	 * @param h The height of the texture.
	 */
	public void renderEmbedded(Image img, float x, float y, float w, float h);
	/**
	 * Renders a texture in embedded mode.
	 * @param img The texture to render.
	 * @param x The X position of the texture.
	 * @param y The Y position of the texture.
	 * @param w The width of the texture.
	 * @param h The height of the texture.
	 */
	public default void render(Image img, float x, float y, float w, float h) {
		startUse(img);
		renderEmbedded(img, x, y, w, h);
		endUse(img);
	}
	/**
	 * Binds a texture.
	 * @param img The texture to bind.
	 */
	public void startUse(Image img);
	/**
	 * Unbinds a texture.
	 * @param img The texture to unbind.
	 */
	public void endUse(Image img);
	/**
	 * Renders a texture with a color filter.
	 * @param img The texture to render.
	 * @param x The X position of the texture.
	 * @param y The Y position of the texture.
	 * @param w The width of the texture.
	 * @param h The height of the texture.
	 * @param c The color of the texture.
	 */
	public void renderFiltered(Image img, float x, float y, float w, float h, Color c);
	/**
	 * Renders a texture sheared.
	 * @param img The texture to render.
	 * @param x The X position of the texture.
	 * @param y The Y position of the texture.
	 * @param w The width of the texture.
	 * @param h The height of the texture.
	 * @param hshear The horizontal shear.
	 * @param vshear The vertical shear.
	 */
	public default void renderSheared(Image img, float x, float y, float w, float h, float hshear, float vshear) {
		startUse(img);
		renderShearedEmbedded(img, x, y, w, h, hshear, vshear);
		endUse(img);
	}
	/**
	 * Renders a texture sheared.
	 * @param img The texture to render.
	 * @param x The X position of the texture.
	 * @param y The Y position of the texture.
	 * @param w The width of the texture.
	 * @param h The height of the texture.
	 * @param hshear The horizontal shear.
	 * @param vshear The vertical shear.
	 */
	public void renderShearedEmbedded(Image img, float x, float y, float w, float h, float hshear, float vshear);
	public void drawQuad(float x, float y, float w, float h, Color c);
	
	public void translate2D(float x, float y);
	public void scale2D(float x, float y);
	public void rotate2D(float x, float y, float a);
	public void transform2D(Transform transform);
	public void resetTransform();
	public void pushTransform();
	public void popTransform();
	
	public void clear();
	
	public Graphics getGUIGraphics();

	public int getScreenWidth();
	public int getScreenHeight();
	public int getWidth();
	public int getHeight();
	public default int getRenderWidth() {
		return getWidth();
	}
	public default int getRenderHeight() {
		return getHeight();
	}
	
	public void init(Game game) throws RenderException;
	public void exit(Game game);

	public void setFullscreen(boolean fullscreen) throws RenderException;
	public void setResolution(int width, int height) throws RenderException;
	public default void setDisplay(int width, int height, boolean fullscreen) throws RenderException {
		setResolution(width, height);
		setFullscreen(fullscreen);
	}
	public void setVSync(boolean vsync);
	public void setWindowTitle(String title);
	
	public void doUpdate();
	public void sync(float fps);
	
	public boolean displayClosePressed();
	
	public void setMode(RenderMode mode);
	public RenderMode getMode();
	
	public void setColorMode(ColorMode mode);
	public ColorMode getColorMode();
	
	public TextureReference getPNG(URL url) throws RenderException, IOException;
	public void copyArea(Image buffer, int x, int y);
	public TextureReference createEmptyTexture(int width, int height) throws RenderException;
	public default TextureReference createHDRTexture(int width, int height) throws RenderException {
		throw new RenderException("An error occured creating a texture.", new UnsupportedOperationException("HDR is not supported by this renderer."));
	}
	public default void useHDRBuffers(boolean hdr) throws RenderException {
		throw new RenderException("An error occured using HDR.", new UnsupportedOperationException("HDR is not supported by this renderer."));
	}
	public default boolean isHDR() {
		return false;
	}
	
	public String getVersion();
	public String getVendor();
	public default String getRendererName() {
		return getClass().getSimpleName();
	}
	public String getRendererGL();
	public default void drawImage(Image img, float x, float y) {
		render(img, x, y, img.getWidth(), img.getHeight());
	}
	public default void drawImage(Image img, float x, float y, Color light) {
		renderFiltered(img, x, y, img.getWidth(), img.getHeight(), light);
	}
	
	public default void setRenderTarget(Image img) throws RenderException {
		throw new RenderException("An error occured creating a render target.", new UnsupportedOperationException("Render targets are not supported in this renderer."));
	}
	public default Image getCurrentTarget() throws RenderException {
		throw new RenderException("An error occured getting a render target.", new UnsupportedOperationException("Render targets are not supported in this renderer."));
	}
	
	public void draw(Shape shape, Color color);
	public void fill(Shape shape, Color color);
	
	public void setFont(Font font);
	public Font getFont();
	public static final int FONT_NORMAL = java.awt.Font.PLAIN;
	public static final int FONT_BOLD = java.awt.Font.BOLD;
	public static final int FONT_ITALIC = java.awt.Font.ITALIC;
	public Font loadFont(String name, int type, float size, int[] codepages) throws RenderException;
	public Font loadFont(URL url, int type, float size, int[] codepages) throws RenderException;
	public default Font loadFont(String name, int type, float size) throws RenderException {
		return loadFont(name, type, size, new int[0]);
	}
	public default Font loadFont(URL url, int type, float size) throws RenderException {
		return loadFont(url, type, size, new int[0]);
	}
	public void drawFont(Font font, float x, float y, String str);
	
	public Input getInput();
	
	public void setFilter(TextureReference texture, int filterMode);
	public void writeImage(Image image, OutputStream out, boolean writeAlpha) throws IOException, RenderException;
	
	public Shader createShader(URL vertex, URL fragment) throws RenderException;
	public void useShader(Shader shader) throws RenderException;
	public void deleteShader(Shader shader) throws RenderException;
	
	public default void renderLine(float x, float y, float x2, float y2) {
		renderLine(x, y, x2, y2, Color.white);
	}
	public void renderLine(float x, float y, float x2, float y2, Color color);
	public void setAntialias(boolean antialias);
	public void setMouseGrab(boolean mouseGrabbed);
	public void setIcon(URL icon);
	
	public default boolean isBuiltInTonemapping() {
		return false;
	}
	
	public LUT loadLUT(URL loc) throws IOException, RenderException;
	public void setLUT(LUT lut);
	public String getGPU();
}

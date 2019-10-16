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
import java.net.URL;

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.RenderException;
import io.github.tomaso2468.rpgonline.TextureReference;

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
	public void render(Image img, float x, float y, float w, float h);
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
	public void renderSheared(Image img, float x, float y, float w, float h, float hshear, float vshear);
	public void drawQuad(float x, float y, float w, float h, Color c);
	
	public void translate2D(float x, float y);
	public void scale2D(float x, float y);
	public void rotate2D(float x, float y, float a);
	public void resetTransform();
	public void pushTransform();
	public void popTransform();
	
	public void clear();
	
	public Graphics getGUIGraphics();

	public int getScreenWidth();
	public int getScreenHeight();
	public int getWidth();
	public int getHeight();
	
	public void init(Game game) throws RenderException;
	public void exit(Game game);

	public void setFullscreen(boolean fullscreen) throws RenderException;
	public void setResolution(int width, int height) throws RenderException;
	public void setVSync(boolean vsync);
	public void setWindowTitle(String title);
	
	public void doUpdate();
	public void sync(float fps);
	
	public boolean displayClosePressed();
	
	public void setMode(RenderMode mode);
	public RenderMode getMode(RenderMode mode);
	
	public TextureReference getPNG(URL url) throws RenderException, IOException;
	public void copyArea(Image buffer, int x, int y);
	public TextureReference createEmptyTexture(int width, int height) throws RenderException;
	
	public String getVersion();
	public String getVendor();
	public default String getRendererName() {
		return getClass().getSimpleName();
	}
	public String getRendererGL();
}

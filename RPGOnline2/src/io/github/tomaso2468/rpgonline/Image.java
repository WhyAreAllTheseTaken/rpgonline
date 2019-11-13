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
package io.github.tomaso2468.rpgonline;

import java.io.File;
import java.io.IOException;

import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;

/**
 * A representation of a 2D image.
 * @author Tomaso2468
 *
 */
public class Image implements Cloneable {
	/**
	 * A filter that uses nearest neighbour interpolation.
	 */
	public static final int FILTER_NEAREST = 0;
	/**
	 * A filter that uses linear interpolation.
	 */
	public static final int FILTER_LINEAR = 1;
	
	/**
	 * The renderer for this image.
	 */
	private Renderer renderer;
	/**
	 * The renderer compatible texture for this image.
	 */
	private TextureReference texture;
	/**
	 * Texture X coord. This uses OpenGL texture coords.
	 */
	private float textureX;
	/**
	 * Texture Y coord. This uses OpenGL texture coords.
	 */
	private float textureY;
	/**
	 * Horizontal texture width.
	 */
	private float textureWidth;
	/**
	 * Vertical texture height.
	 */
	private float textureHeight;
	/**
	 * The width of the image.
	 */
	private float width;
	/**
	 * The height of the image.
	 */
	private float height;
	/**
	 * The filter this image uses.
	 */
	private int filter = FILTER_LINEAR;

	/**
	 * Constructs a new blank image.
	 * @param renderer The renderer to use for image commands.
	 * @param width The width of the image.
	 * @param height The height of the image.
	 * @throws RenderException If an error occurs creating an empty image.
	 */
	public Image(Renderer renderer, int width, int height) throws RenderException {
		this(renderer, renderer.createEmptyTexture(width, height));
	}
	
	/**
	 * Constructs a new Image using texture information.
	 * @param renderer The renderer to use for image commands.
	 * @param texture The texture to use for this image.
	 * @param texture_x The texture X coord using OpenGL texture coords.
	 * @param texture_y The texture Y coord using OpenGL texture coords.
	 * @param texture_w The width of the area to use from the texture.
	 * @param texture_h The height of the area to use from the texture.
	 * @param width The width of the image.
	 * @param height The height of the image.
	 */
	public Image(Renderer renderer, TextureReference texture, float texture_x, float texture_y, float texture_w,
			float texture_h, float width, float height) {
		super();
		this.renderer = renderer;
		this.texture = texture;
		this.textureX = texture_x;
		this.textureY = texture_y;
		this.textureWidth = texture_w;
		this.textureHeight = texture_h;
		this.width = width;
		this.height = height;
	}

	/**
	 * Constructs a new Image using texture information.
	 * @param renderer The renderer to use for image commands.
	 * @param texture The texture to use for this image.
	 */
	public Image(Renderer renderer, TextureReference texture) {
		super();
		this.renderer = renderer;
		this.texture = texture;
		this.textureX = 0;
		this.textureY = 0;
		this.textureWidth = texture.getLibraryWidth();
		this.textureHeight = texture.getLibraryHeight();
		this.width = texture.getWidth();
		this.height = texture.getHeight();
	}

	/**
	 * Constructs a new image from an image. This does not create a new copy of the underlying texture.
	 * @param renderer The renderer to use for image commands.
	 * @param img The image to copy data from.
	 */
	public Image(Renderer renderer, Image img) {
		this(renderer, img.getTexture(), img.getTextureOffsetX(), img.getTextureOffsetY(), img.getTextureWidth(),
				img.getTextureHeight(), img.getWidth(), img.getHeight());
	}

	/**
	 * Sets the filter for this image.
	 * @param filterMode The filter mode.
	 * 
	 * @see #getFilter()
	 * @see #FILTER_LINEAR
	 * @see #FILTER_NEAREST
	 */
	public void setFilter(int filterMode) {
		renderer.setFilter(texture, filterMode);
		this.filter = filterMode;
	}

	/**
	 * Gets the texture for this image.
	 * @return A texture reference object compatible with the game's renderer.
	 */
	public TextureReference getTexture() {
		return texture;
	}

	/**
	 * Writes an image to a file.
	 * @param dest The location to write to.
	 * @param writeAlpha Whether or not the alpha channel should be used when writing this image.
	 * @throws IOException If an error occurs writing the image.
	 * @throws RenderException If an error occurs in the renderer.
	 */
	public void write(String dest, boolean writeAlpha) throws IOException, RenderException {
		renderer.writeImage(this, new File(dest), writeAlpha);
	}

	/**
	 * Writes an image to a file including the alpha channel.
	 * @param dest The location to write to.
	 * @throws IOException If an error occurs writing the image.
	 * @throws RenderException If an error occurs in the renderer.
	 */
	public void write(String dest) throws IOException, RenderException {
		write(dest, true);
	}

	/**
	 * Gets the X position from the source texture.
	 * @return A float value.
	 */
	public float getTextureOffsetX() {
		return textureX;
	}

	public float getTextureWidth() {
		return textureWidth;
	}

	public float getTextureOffsetY() {
		return textureY;
	}

	public float getTextureHeight() {
		return textureHeight;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public Image getScaledCopy(float w, float h) {
		return new Image(renderer, texture, textureX, textureY, textureWidth, textureHeight, w, h);
	}

	public Image getScaledCopy(float scale) {
		return getScaledCopy(width * scale, height * scale);
	}

	public Image getSubImage(float x, float y, float w, float h) {
		float newTextureOffsetX = ((x / (float) this.width) * textureWidth) + textureX;
		float newTextureOffsetY = ((y / (float) this.height) * textureHeight) + textureY;
		float newTextureWidth = ((w / (float) this.width) * textureWidth);
		float newTextureHeight = ((h / (float) this.height) * textureHeight);

		Image sub = new Image(renderer, texture, newTextureOffsetX, newTextureOffsetY, newTextureWidth,
				newTextureHeight, w, h);

		return sub;
	}
	
	public Image clone() {
		return new Image(renderer, texture, textureX, textureY, textureWidth, textureHeight, width, height);
	}

	public void destroy() {
		texture.destroy();
	}
	
	public void ensureInverted() {
		if (textureHeight > 0) {
			textureY = textureY + textureHeight;
			textureHeight = -textureHeight;
		}
	}

	public int getFilter() {
		return filter;
	}
}

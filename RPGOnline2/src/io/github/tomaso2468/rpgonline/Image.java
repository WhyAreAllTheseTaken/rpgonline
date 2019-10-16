package io.github.tomaso2468.rpgonline;

import io.github.tomaso2468.rpgonline.render.Renderer;

public class Image implements Cloneable {
	public static final int FILTER_NEAREST = 0;
	public static final int FILTER_LINEAR = 1;

	private Renderer renderer;
	private TextureReference texture;
	private float textureX;
	private float textureY;
	private float textureWidth;
	private float textureHeight;
	private float width;
	private float height;

	public Image(Renderer renderer, int width, int height) throws RenderException {
		this(renderer, renderer.createEmptyTexture(width, height));
	}
	
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

	public Image(Renderer renderer, Image img) {
		this(renderer, img.getTexture(), img.getTextureOffsetX(), img.getTextureOffsetY(), img.getTextureWidth(),
				img.getTextureHeight(), img.getWidth(), img.getHeight());
	}

	public void setFilter(int filterMode) {
		// TODO Auto-generated method stub

	}

	public TextureReference getTexture() {
		return texture;
	}

	public void write(String dest, boolean writeAlpha) {

	}

	public void write(String dest) {
		write(dest, true);
	}

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
		float newTextureWidth = ((width / (float) this.width) * textureWidth);
		float newTextureHeight = ((height / (float) this.height) * textureHeight);

		Image sub = new Image(renderer, texture, newTextureOffsetX, newTextureOffsetY, newTextureWidth,
				newTextureHeight, w, h);

		return sub;
	}
	
	public Image clone() {
		return new Image(renderer, texture, textureX, textureY, textureWidth, textureHeight, width, height);
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public void ensureInverted() {
		if (textureHeight > 0) {
			textureY = textureY + textureHeight;
			textureHeight = -textureHeight;
		}
	}

	public int getFilter() {
		// TODO Auto-generated method stub
		return 0;
	}
}

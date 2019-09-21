package io.github.tomaso2468.rpgonline.render;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * An interface representing a renderer.
 * @author Tomas
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

	public void renderFiltered(Image img, float x, float y, float w, float h, Color c);
	
	public void renderSheared(Image img, float x, float y, float w, float h, float hshear, float vshear);
	
	public void translate(Graphics g, float x, float y);
	
	public void scale(Graphics g, float x, float y);
	
	public io.github.tomaso2468.rpgonline.render.Graphics getGUIGraphics(Graphics g);
}

package io.github.tomaso2468.rpgonline.gui.layer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.texture.TextureMap;

/**
 * A layer with a single image.
 * @author Tomas
 *
 */
public class ImageLayer extends Layer {
	/**
	 * The texture of the layer.
	 */
	private String texture;

	/**
	 * Constructs a new ImageLayer.
	 * @param texture The texture of the layer.
	 */
	public ImageLayer(String texture) {
		super();
		this.texture = texture;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		g.drawImage(TextureMap.getTexture(texture).getScaledCopy((int) getW(), (int) getH()), 0, 0);
	}
}

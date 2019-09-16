package io.github.tomaso2468.rpgonline.gui.layer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.texture.TextureMap;

public class ImageLayer extends Layer {
	private String texture;

	public ImageLayer(String texture) {
		super();
		this.texture = texture;
	}
	
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		g.drawImage(TextureMap.getTexture(texture).getScaledCopy((int) getW(), (int) getH()), 0, 0);
	}
}

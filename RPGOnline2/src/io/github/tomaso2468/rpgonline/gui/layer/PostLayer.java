package io.github.tomaso2468.rpgonline.gui.layer;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.post.PostEffect;

public class PostLayer extends Layer {
	private PostEffect post;
	private Image buffer;
	
	public PostLayer(PostEffect post) {
		super();
		this.post = post;
	}

	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		if (buffer == null) {
			buffer = new Image((int) FastMath.ceil(getW()), (int) FastMath.ceil(getH()));
		}
		if (buffer.getWidth() != (int) FastMath.ceil(getW())
				|| buffer.getHeight() != (int) FastMath.ceil(getH())) {
			buffer.destroy();
			buffer = new Image((int) FastMath.ceil(getW()), (int) FastMath.ceil(getH()));
		}
		
		g.copyArea(buffer, 0, 0);
		g.pushTransform();
		post.doPostProcess(null, null, buffer, g);
		g.popTransform();
	}
}

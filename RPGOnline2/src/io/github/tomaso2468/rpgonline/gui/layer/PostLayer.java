package io.github.tomaso2468.rpgonline.gui.layer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.post.PostEffect;

/**
 * A layer that applies a post-processing effect.
 * @author Tomas
 *
 */
public class PostLayer extends Layer {
	/**
	 * The effect to apply.
	 */
	private PostEffect post;
	/**
	 * A buffer used for processing.
	 */
	private Image buffer;
	/**
	 * The game container object.
	 */
	private GameContainer c;
	
	/**
	 * Constructs a new PostLayer.
	 * @param post The effect to apply.
	 */
	public PostLayer(PostEffect post) {
		super();
		this.post = post;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		if (buffer == null) {
			buffer = new Image(c.getWidth(), c.getHeight());
		}
		if (buffer.getWidth() != c.getWidth()
				|| buffer.getHeight() != c.getHeight()) {
			buffer.destroy();
			buffer = new Image(c.getWidth(), c.getHeight());
		}
		
		g.copyArea(buffer, 0, 0);
		g.pushTransform();
		post.doPostProcess(null, null, buffer, g);
		g.popTransform();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void containerUpdate(GameContainer c) {
		this.c = c;
	}
}

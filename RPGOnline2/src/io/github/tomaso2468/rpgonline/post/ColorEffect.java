package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Multiplies the current image by a colour.
 * 
 * @author Tomas
 */
public class ColorEffect implements PostEffect {
	/**
	 * The colour to multiply by.
	 */
	private final Color c;

	/**
	 * Creates a {@code ColorEffect}.
	 * 
	 * @param c The colour to multiply by.
	 */
	public ColorEffect(Color c) {
		this.c = c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		g.drawImage(buffer, 0, 0, c);
	}

}

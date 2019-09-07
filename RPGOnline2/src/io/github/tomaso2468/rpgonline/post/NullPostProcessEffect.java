package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * An effect that does nothing.
 * 
 * @author Tomas
 */
public class NullPostProcessEffect implements PostEffect {
	/**
	 * A singleton instance.
	 */
	public static final PostEffect INSTANCE = new NullPostProcessEffect();

	/**
	 * A private constructor to ensure only one instance is created.
	 */
	private NullPostProcessEffect() {
		// Singleton class for performance.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		g.drawImage(buffer, 0, 0);
	}
}

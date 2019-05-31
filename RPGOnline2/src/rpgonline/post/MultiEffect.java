package rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Allows for the chaining of multiple effects together.
 * 
 * @author Tomas
 */
public class MultiEffect implements PostEffect {
	/**
	 * An array of effects.
	 */
	private final PostEffect[] effects;

	/**
	 * Create a {@code MultiEffect} object
	 * 
	 * @param effects The effects to apply in order.
	 */
	public MultiEffect(PostEffect... effects) {
		super();
		this.effects = effects;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		if (effects.length == 0) {
			NullPostProcessEffect.INSTANCE.doPostProcess(container, game, buffer, g);
		}
		for (PostEffect e : effects) {
			e.doPostProcess(container, game, buffer, g);

			buffer.flushPixelData();

			g.resetTransform();

			g.copyArea(buffer, 0, 0);
		}
	}

	@Override
	public void dispose() throws SlickException {
		for (PostEffect e : effects) {
			e.dispose();
		}
	}

}

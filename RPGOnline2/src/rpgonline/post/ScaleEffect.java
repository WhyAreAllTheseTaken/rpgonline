package rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * An effect that scales by a certain amount.
 * 
 * @author Tomas
 */
public class ScaleEffect implements PostEffect {
	/**
	 * The amount to scale by.
	 */
	private final float scale;

	/**
	 * Creates a new {@code ScaleEffect}.
	 * 
	 * @param scale The amount to scale by.
	 */
	public ScaleEffect(float scale) {
		super();
		this.scale = scale;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		g.clear();
		g.translate(container.getWidth() / 2, container.getHeight() / 2);
		g.scale(scale, scale);

		g.drawImage(buffer, -buffer.getWidth() / 2, -buffer.getHeight() / 2);

		g.resetTransform();
	}

}

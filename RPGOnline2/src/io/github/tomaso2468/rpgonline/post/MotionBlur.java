package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * An effect that provides a motion blur.
 * 
 * @author Tomas
 */
public class MotionBlur implements PostEffect {
	/**
	 * The previous frame.
	 */
	private Image last;

	/**
	 * The alpha value of the previous frame.
	 */
	public float amount;

	/**
	 * Creates a {@code MotionBlur} effect.
	 * 
	 * @param amount The alpha value of the previous frame.
	 */
	public MotionBlur(float amount) {
		this.amount = amount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		g.drawImage(buffer, 0, 0);

		if (last == null) {
			last = new Image(buffer.getWidth(), buffer.getHeight());
		}
		if (container.getWidth() != last.getWidth() || container.getHeight() != last.getHeight()) {
			last.destroy();
			last = new Image(container.getWidth(), container.getHeight());
		}

		last.setImageColor(1, 1, 1, amount * container.getFPS());

		g.drawImage(last, 0, 0);

		g.copyArea(last, 0, 0);

		last.flushPixelData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() throws SlickException {
		if (last != null) {
			last.destroy();
		}
	}
}

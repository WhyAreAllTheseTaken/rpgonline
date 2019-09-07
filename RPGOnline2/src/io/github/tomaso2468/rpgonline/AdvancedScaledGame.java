package io.github.tomaso2468.rpgonline;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

/**
 * <p>
 * A game with support for better scaling.
 * </p>
 * <p>
 * This game will change the aspect ratio but the scale will always be the same
 * for both axis. The resolution on at least one axis will be the same as the
 * target resolution
 * </p>
 * <p>
 * This class was created to prevent stretching or black bars when playing a
 * wide screen game.
 * </p>
 * 
 * @author Tomas
 */
public class AdvancedScaledGame implements Game {
	/**
	 * <p>
	 * The game to delegate to
	 * </p>
	 */
	private Game game;
	/**
	 * The target width
	 */
	private float w;
	/**
	 * The target height
	 */
	private float h;

	/**
	 * Creates a new game
	 * 
	 * @param game The game to delegate to
	 * @param w    The target width in pixels
	 * @param h    The target height in pixels
	 */
	public AdvancedScaledGame(@Nonnull Game game, float w, float h) {
		this.game = game;
		Validate.finite(w, "Width must be finite: " + w);
		Validate.finite(h, "Height must be finite: " + h);
		if (w < 0) {
			throw new IllegalArgumentException("Width cannot be negative: " + w);
		}
		if (h < 0) {
			throw new IllegalArgumentException("Height cannot be negative: " + h);
		}
		this.w = w;
		this.h = h;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(GameContainer container) throws SlickException {
		game.init(container);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		game.update(container, delta);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		if (container.getWidth() != w || container.getHeight() != h) {
			Log.info("Container size: " + container.getWidth() + " " + container.getHeight());
			float scaleX = container.getWidth() / w;
			float scaleY = container.getHeight() / h;
			float scale = FastMath.min(scaleX, scaleY);
			Log.info("Scaling " + scale);
			g.pushTransform();
			g.scale(scale, scale);
		}
		game.render(container, g);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean closeRequested() {
		return game.closeRequested();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return game.getTitle();
	}

}

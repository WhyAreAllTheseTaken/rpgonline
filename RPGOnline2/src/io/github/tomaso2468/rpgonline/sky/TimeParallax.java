package io.github.tomaso2468.rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import io.github.tomaso2468.rpgonline.world.World;

/**
 * A sky layer that applies a movement effect based on time.
 * @author Tomas
 *
 */
public class TimeParallax implements SkyLayer {
	/**
	 * The X parallax factor.
	 */
	private final double factorX;
	/**
	 * The Y parallax factor.
	 */
	private final double factorY;
	/**
	 * The sky layer to apply the time parallax effect to.
	 */
	private final SkyLayer l;
	
	/**
	 * Constructs a new TimeParralax object.
	 * @param factorX The X parallax factor.
	 * @param factorY The Y parallax factor.
	 * @param l The sky layer to apply the time parallax effect to.
	 */
	public TimeParallax(double factorX, double factorY, SkyLayer l) {
		super();
		this.factorX = factorX;
		this.factorY = factorY;
		this.l = l;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		l.render(g, c, x + ((System.currentTimeMillis() - 1561284966000L) * factorX), y + ((System.currentTimeMillis() - 1561284966000L) * factorY), z, world, light);
	}

} 

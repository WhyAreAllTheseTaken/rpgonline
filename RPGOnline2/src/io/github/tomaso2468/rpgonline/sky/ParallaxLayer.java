package io.github.tomaso2468.rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import io.github.tomaso2468.rpgonline.world.World;

/**
 * A layer that adds a parallax effect.
 * @author Tomas
 *
 */
public class ParallaxLayer implements SkyLayer {
	/**
	 * The X parallax factor.
	 */
	private final double factorX;
	/**
	 * The Y parallax factor.
	 */
	private final double factorY;
	/**
	 * The sky layer to apply the effect to.
	 */
	private final SkyLayer l;
	
	/**
	 * Constructs a new parallax layer.
	 * @param factorX The X parallax factor.
	 * @param factorY The Y parallax factor.
	 * @param l The sky layer to apply the effect to.
	 */
	public ParallaxLayer(double factorX, double factorY, SkyLayer l) {
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
		l.render(g, c, x * factorX, y * factorY, z, world, light);
	}
	
}

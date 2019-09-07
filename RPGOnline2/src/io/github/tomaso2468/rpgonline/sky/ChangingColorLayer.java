package io.github.tomaso2468.rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import io.github.tomaso2468.rpgonline.world.World;

/**
 * A sky layer with a changing color.
 * @author Tomas
 *
 */
public abstract class ChangingColorLayer implements SkyLayer {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		g.setColor(getColor(g, c, x, y, z, world));
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
	}
	
	/**
	 * Gets the color of the sky layer.
	 * @param g The current graphics context.
	 * @param c The current game container.
	 * @param x The player X position.
	 * @param y The player Y position.
	 * @param z The player Z position.
	 * @param world The current world.
	 * @return A color object.
	 */
	public abstract Color getColor(Graphics g, GameContainer c, double x, double y, double z, World world);
}

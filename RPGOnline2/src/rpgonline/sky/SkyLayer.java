package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import rpgonline.world.World;

/**
 * An interface for classes that allow a sky to be rendered.
 * @author Tomas
 *
 */
public interface SkyLayer {
	/**
	 * Renders the sky.
	 * @param g The current graphics context.
	 * @param c The current game container.
	 * @param x The player X position.
	 * @param y The player Y position.
	 * @param z The player Z position.
	 * @param world The current world.
	 * @param light The current sky light.
	 */
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light);
}

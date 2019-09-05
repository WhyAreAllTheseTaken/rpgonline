package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import rpgonline.world.World;

/**
 * A layer with a flat color.
 * @author Tomas
 *
 */
public class ColorLayer implements SkyLayer {
	/**
	 * The layer color.
	 */
	private final Color c;
	
	/**
	 * Constructs a new ColorLayer.
	 * @param c A color object.
	 */
	public ColorLayer(Color c) {
		super();
		this.c = c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		g.setColor(this.c);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
	}

}

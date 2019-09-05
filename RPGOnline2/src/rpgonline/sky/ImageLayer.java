package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import rpgonline.world.World;

/**
 * A sky layer with a static image.
 * @author Tomas
 *
 */
public class ImageLayer implements SkyLayer {
	/**
	 * The layer image.
	 */
	private final Image img;
	
	/**
	 * Constructs a new image layer.
	 * @param img The image to use.
	 */
	public ImageLayer(Image img) {
		super();
		this.img = img;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		g.drawImage(img, (float) x, (float) y, light);
	}

}

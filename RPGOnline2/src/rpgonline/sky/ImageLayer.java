package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import rpgonline.world.World;

public class ImageLayer implements SkyLayer {
	private final Image img;
	
	public ImageLayer(Image img) {
		super();
		this.img = img;
	}

	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		g.drawImage(img, (float) x, (float) y, light);
	}

}

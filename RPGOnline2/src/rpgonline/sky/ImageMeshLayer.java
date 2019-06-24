package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import rpgonline.world.World;

public abstract class ImageMeshLayer implements SkyLayer {
	private final int imageWidth;
	private final int imageHeight;
	
	public ImageMeshLayer(int imageWidth, int imageHeight) {
		super();
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}
	
	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		g.pushTransform();
		
		g.translate(c.getWidth() / 2, c.getHeight() / 2);
		g.translate((float) x % imageWidth, (float) y % imageHeight);
		
		for (long tx = -2; tx <= 1; tx++) {
			for (long ty = -2; ty <= 1; ty++) {
				Image img = getImageAt(tx - (long) (x / imageWidth), ty - (long) (y / imageHeight));
				if (img != null) {
					g.drawImage(img, tx * imageWidth, ty * imageHeight, light);
				}
			}
		}
		
		g.popTransform();
	}
	
	public abstract Image getImageAt(long x, long y);
}

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
		g.translate((float) x, (float) y);
		
		for (int tx = ((int) x + imageWidth / 2 - imageWidth) / imageWidth; tx <= ((int) x + imageWidth / 2 + imageWidth) / imageWidth; tx++) {
			for (int ty = ((int) y + imageHeight / 2 - imageHeight) / imageHeight; ty <= ((int) y + imageHeight / 2 + imageHeight) / imageHeight; ty++) {
				Image img = getImageAt(tx, ty);
				
				g.drawImage(img, tx * imageWidth, ty * imageWidth, light);
			}
		}
	}
	
	public abstract Image getImageAt(int x, int y);
}

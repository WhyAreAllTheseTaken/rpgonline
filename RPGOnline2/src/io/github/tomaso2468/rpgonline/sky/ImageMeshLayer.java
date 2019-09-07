package io.github.tomaso2468.rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import io.github.tomaso2468.rpgonline.texture.TextureMap;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * A sky layer made of a grid of images.
 * @author Tomas
 *
 */
public abstract class ImageMeshLayer implements SkyLayer {
	/**
	 * The width of one image.
	 */
	private final int imageWidth;
	/**
	 * The height of one image.
	 */
	private final int imageHeight;
	
	/**
	 * Constructs a new ImageMeshLayer.
	 * @param imageWidth The width of one image.
	 * @param imageHeight The height of one image.
	 */
	public ImageMeshLayer(int imageWidth, int imageHeight) {
		super();
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		g.pushTransform();
		
		g.translate(c.getWidth() / 2, c.getHeight() / 2);
		g.translate((float) x % imageWidth, (float) y % imageHeight);
		
		long dist_x = c.getWidth() / 2 / imageWidth + 1;
		long dist_y = c.getHeight() / 2 / imageHeight + 1;
		
		Image current = null;
		for (long tx = -dist_x; tx <= dist_x; tx++) {
			for (long ty = -dist_y; ty <= dist_y; ty++) {
				Image img = getImageAt(tx - (long) (x / imageWidth), ty - (long) (y / imageHeight));
				if (img != null) {
					if (TextureMap.getSheet(img) != current) {
						if (current != null) current.endUse();
						current = TextureMap.getSheet(img);
						current.startUse();
					}
					light.bind();
					img.drawEmbedded(tx * imageWidth, ty * imageHeight, imageWidth, imageHeight);
				}
			}
		}
		if (current != null) current.endUse();
		
		g.popTransform();
	}
	
	/**
	 * Gets the image at the specified position.
	 * @param x The X position of the image.
	 * @param y The Y position of the image.
	 * @return An image object.
	 */
	public abstract Image getImageAt(long x, long y);
}

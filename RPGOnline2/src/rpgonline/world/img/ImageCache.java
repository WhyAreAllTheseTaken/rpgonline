package rpgonline.world.img;

import java.awt.image.BufferedImage;

public class ImageCache {
	private final BufferedImage img;
	private long time = System.currentTimeMillis();
	private final long x;
	private final long y;

	public ImageCache(BufferedImage img, long x, long y) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
	}

	public BufferedImage getImage() {
		time = System.currentTimeMillis();
		return img;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() - time > 1000 * 60 * 2;
	}

	public long getX() {
		return x;
	}

	public long getY() {
		return y;
	}
}

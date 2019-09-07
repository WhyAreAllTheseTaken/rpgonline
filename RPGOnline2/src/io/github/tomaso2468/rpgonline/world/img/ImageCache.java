package io.github.tomaso2468.rpgonline.world.img;

import java.awt.image.BufferedImage;

/**
 * A class that stores an image for use in {@code WorldFromImage}.
 * @author Tomas
 *
 * @see io.github.tomaso2468.rpgonline.world.img.WorldFromImage
 */
public class ImageCache {
	/**
	 * The image to cache.
	 */
	private final BufferedImage img;
	/**
	 * The time of the last access of this image.
	 */
	private long time = System.currentTimeMillis();
	/**
	 * The X position of the image.
	 */
	private final long x;
	/**
	 * The Y position of the image.
	 */
	private final long y;

	/**
	 * Constructs a new ImageCache
	 * @param img The image to cache.
	 * @param x The X position of the image.
	 * @param y The Y position of the image.
	 */
	public ImageCache(BufferedImage img, long x, long y) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the image held in this cache.
	 * @return A buffered image object.
	 */
	public BufferedImage getImage() {
		time = System.currentTimeMillis();
		return img;
	}

	/**
	 * Determines if this cache is expired.
	 * @return
	 */
	public boolean isExpired() {
		return System.currentTimeMillis() - time > 1000 * 60 * 10;
	}

	/**
	 * Gets the X position of this image.
	 * @return A long value.
	 */
	public long getX() {
		return x;
	}

	/**
	 * Gets the Y position of this image.
	 * @return A long value.
	 */
	public long getY() {
		return y;
	}
}

package io.github.tomaso2468.rpgonline.sky;

import org.newdawn.slick.Image;

/**
 * A sky layer mesh made of a top, middle and bottom image.
 * @author Tomas
 *
 */
public class ThreeImageMesh extends ImageMeshLayer {
	/**
	 * The top image.
	 */
	private final Image top;
	/**
	 * The middle image.
	 */
	private final Image middle;
	/**
	 * The bottom image.
	 */
	private final Image bottom;
	
	/**
	 * Constructs a new ThreeImageMesh
	 * @param imageWidth The width of one image.
	 * @param imageHeight The height of one image.
	 * @param top The top image.
	 * @param middle The middle image.
	 * @param bottom The bottom image.
	 */
	public ThreeImageMesh(int imageWidth, int imageHeight, Image top, Image middle, Image bottom) {
		super(imageWidth, imageHeight);
		this.top = top;
		this.middle = middle;
		this.bottom = bottom;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getImageAt(long x, long y) {
		if (y < 0) {
			return top;
		} else if (y == 0) {
			return middle;
		} else {
			return bottom;
		}
	}

}

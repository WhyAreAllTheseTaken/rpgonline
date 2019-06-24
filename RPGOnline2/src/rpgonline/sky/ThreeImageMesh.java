package rpgonline.sky;

import org.newdawn.slick.Image;

public class ThreeImageMesh extends ImageMeshLayer {
	private final Image top;
	private final Image middle;
	private final Image bottom;
	
	public ThreeImageMesh(int imageWidth, int imageHeight, Image top, Image middle, Image bottom) {
		super(imageWidth, imageHeight);
		this.top = top;
		this.middle = middle;
		this.bottom = bottom;
	}

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

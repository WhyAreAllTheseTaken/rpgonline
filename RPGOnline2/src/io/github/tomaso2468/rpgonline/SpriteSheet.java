package io.github.tomaso2468.rpgonline;

import io.github.tomaso2468.rpgonline.render.Renderer;

public class SpriteSheet extends Image {
	private int tw;
	private int th;
	private Image[][] images;
	public SpriteSheet(Renderer renderer, Image img, int tw, int th) {
		super(renderer, img);
		this.tw = tw;
		this.th = th;
		images = new Image[getVerticalCount()][getHorizontalCount()];
		for (int y = 0; y < getVerticalCount(); y++) {
			for (int x = 0; x < getHorizontalCount(); x++) {
				images[y][x] = getSubImage(x, y);
			}
		}
	}

	public int getHorizontalCount() {
		return (int) Math.ceil(getWidth() / tw);
	}

	public int getVerticalCount() {
		return (int) Math.ceil(getHeight() / th);
	}

	public Image getSprite(int x, int y) {
		return images[y][x];
	}

	public Image getSubImage(int x, int y) {
		return getSubImage(x * tw, y * th, tw, th);
	}

}

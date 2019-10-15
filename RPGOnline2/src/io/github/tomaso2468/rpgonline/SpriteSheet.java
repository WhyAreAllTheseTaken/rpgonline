package io.github.tomaso2468.rpgonline;

import org.newdawn.slick.opengl.Texture;

public class SpriteSheet extends Image {

	public SpriteSheet(Texture tex) {
		super(tex);
		// TODO Auto-generated constructor stub
	}

	public SpriteSheet(Image img, int tw, int th) {
		super(img.getTexture());
	}

	public int getHorizontalCount() {
		return 0;
	}

	public int getVerticalCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Image getSprite(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	public Image getSubImage(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

}

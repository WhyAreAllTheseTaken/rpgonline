package io.github.tomaso2468.rpgonline.render.opengl;

import org.newdawn.slick.UnicodeFont;

import io.github.tomaso2468.rpgonline.Font;

public class SlickFont implements Font {
	UnicodeFont font;

	public SlickFont(UnicodeFont font) {
		super();
		this.font = font;
	}

	@Override
	public float getHeight(String text) {
		return font.getHeight(text);
	}

	@Override
	public float getWidth(String text) {
		return font.getWidth(text);
	}

}

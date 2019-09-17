package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class List extends RadioGroup {

	public List(String[] data) {
		for (int i = 0; i < data.length; i++) {
			add(new ListElement(data[i], i == 0 ? true : false));
		}
	}

	public static class ListElement extends RadioButton {
		public ListElement(String text, boolean state) {
			super(text, state);
		}
		
		@Override
		public void paint(Graphics g, float scaling) throws SlickException {
			ThemeManager.getTheme().paintListElement(g, scaling, this);
		}
		
		@Override
		public Rectangle getDefaultBounds(Container c) {
			return ThemeManager.getTheme().calculateListElementBounds(c, this);
		}
	}
}

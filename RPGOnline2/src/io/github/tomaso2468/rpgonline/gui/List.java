package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A list of selectable values.
 * @author Tomas
 *
 */
public class List extends RadioGroup {

	/**
	 * Constructs a new list.
	 * @param data The values of the list,
	 */
	public List(String[] data) {
		for (int i = 0; i < data.length; i++) {
			add(new ListElement(data[i], i == 0 ? true : false));
		}
	}

	/**
	 * A single element in the list.
	 * @author Tomas
	 *
	 */
	public static class ListElement extends RadioButton {
		/**
		 * Constructs a new ListElement.
		 * @param text The text of the list element.
		 * @param state The state of the element.
		 */
		public ListElement(String text, boolean state) {
			super(text, state);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void paint(Graphics g, float scaling) throws SlickException {
			ThemeManager.getTheme().paintListElement(g, scaling, this);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Rectangle getDefaultBounds(Container c) {
			return ThemeManager.getTheme().calculateListElementBounds(c, this);
		}
	}
}

package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A non-editable label.
 * @author Tomas
 *
 */
public class Label extends Component {
	/**
	 * The text of the label.
	 */
	private String text;

	/**
	 * Constructs a new Label.
	 * @param text The text of the label.
	 */
	public Label(String text) {
		super();
		this.text = text;
	}

	/**
	 * Gets the text of this label.
	 * @return A string.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of this label.
	 * @param text A string,
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintLabel(g, scaling, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateLabelBounds(c, this);
	}
}

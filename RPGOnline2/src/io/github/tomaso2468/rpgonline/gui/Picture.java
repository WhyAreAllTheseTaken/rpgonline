package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A component that displays a single image.
 * @author Tomas
 *
 */
public class Picture extends Component {
	/**
	 * The image displayed in this component.
	 */
	private Image img;

	/**
	 * Constructs a new Picture.
	 * @param img The image to display.
	 */
	public Picture(Image img) {
		super();
		this.img = img;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintPicture(g, scaling, this);
	}

	/**
	 * Gets the image of this picture.
	 * @return An image object.
	 */
	public Image getImage() {
		return img;
	}

	/**
	 * Sets the image of this picture.
	 * @param img An image object.
	 */
	public void setImage(Image img) {
		this.img = img;
	}

}

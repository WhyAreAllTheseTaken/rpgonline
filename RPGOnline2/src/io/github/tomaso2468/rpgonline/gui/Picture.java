package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class Picture extends Component {
	private Image img;

	public Picture(Image img) {
		super();
		this.img = img;
	}
	
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintPicture(g, scaling, this);
	}

	public Image getImage() {
		return img;
	}

	public void setImage(Image img) {
		this.img = img;
	}

}

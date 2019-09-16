package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class Label extends Component {
	private String text;

	public Label(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintLabel(g, scaling, this);
	}

}

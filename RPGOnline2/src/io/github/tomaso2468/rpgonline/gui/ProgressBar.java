package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class ProgressBar extends Component {
	private int max;
	private int value;
	
	public ProgressBar(int max, int value) {
		super();
		this.max = max;
		this.value = value;
	}

	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintProgressBar(g, scaling, this);
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}

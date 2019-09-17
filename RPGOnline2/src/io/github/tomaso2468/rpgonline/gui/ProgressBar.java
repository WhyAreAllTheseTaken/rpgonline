package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A progress bar.
 * @author Tomas
 *
 */
public class ProgressBar extends Component {
	/**
	 * The maximum value of this progress bar.
	 */
	private int max;
	/**
	 * The value of this progress bar.
	 */
	private int value;
	
	/**
	 * Constructs a new ProgressBar.
	 * @param max The maximum value of this progress bar.
	 * @param value The value of this progress bar.
	 */
	public ProgressBar(int max, int value) {
		super();
		this.max = max;
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintProgressBar(g, scaling, this);
	}

	/**
	 * Gets the maximum value of this progress bar.
	 * @return An int value.
	 */
	public int getMax() {
		return max;
	}

	/**
	 * Sets the maximum value of this progress bar.
	 * @param max An int value.
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * Gets the value of this progress bar.
	 * @return An int value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value of this progress bar.
	 * @param value An int value.
	 */
	public void setValue(int value) {
		this.value = value;
	}
}

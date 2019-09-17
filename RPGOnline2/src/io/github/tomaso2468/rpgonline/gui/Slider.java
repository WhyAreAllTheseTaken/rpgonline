package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A slider component.
 * @author Tomas
 *
 */
public class Slider extends Component {
	/**
	 * The position of the slider.
	 */
	private float pos;
	/**
	 * The maximum position of the slider.
	 */
	private float max;

	/**
	 * Constructs a new slider.
	 * @param pos The default position of the slider.
	 * @param max The maximum position of the slider.
	 */
	public Slider(float pos, float max) {
		super();
		this.setPos(pos);
		this.max = max;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateSliderBounds(c, this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseDraggedLeft(float ox, float oy, float nx, float ny) {
		pos = nx / getW() * max;
	}

	/**
	 * Gets the position of this slider.
	 * @return A float value.
	 */
	public float getPos() {
		return pos;
	}

	/**
	 * Sets the position of this slider.
	 * @param pos A float value.
	 */
	public void setPos(float pos) {
		this.pos = pos;
		onUpdate(pos, max);
	}
	
	/**
	 * Gets the maximum value of this slider.
	 * @return A float value.
	 */
	public float getMax() {
		return max;
	}

	/**
	 * Sets the maximum value of this slider.
	 * @param max A float value.
	 */
	public void setMax(float max) {
		this.max = max;
	}
	
	/**
	 * Updates the slider.
	 * @param pos The position of the slider.
	 * @param max The maximum position of the slider.
	 */
	public void onUpdate(float pos, float max) {
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintSlider(g, scaling, this);
	}
}

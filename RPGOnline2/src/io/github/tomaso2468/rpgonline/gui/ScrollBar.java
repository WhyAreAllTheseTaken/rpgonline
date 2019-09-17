package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A vertical scroll bar.
 * @author Tomas
 *
 */
public class ScrollBar extends Component {
	/**
	 * The position of the scroll bar.
	 */
	private float pos;
	/**
	 * The maximum value of the scroll bar.
	 */
	private float max;

	/**
	 * Constructs a new scroll bar.
	 * @param pos The position of the scroll bar.
	 * @param max The maximum value of the scroll bar.
	 */
	public ScrollBar(float pos, float max) {
		super();
		this.setPos(pos);
		this.max = max;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateScrollBarBounds(c, this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClickedLeft(float x, float y) {
		if (y < getH() / 20) {
			setPos(0);
		}
		if (y > getH() / 20 * 19) {
			setPos(max);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseDraggedLeft(float ox, float oy, float nx, float ny) {
		pos = ny / getH() * max;
		if (pos < 0) {
			pos = 0;
		}
		if (pos > max) {
			pos = max;
		}
	}

	/**
	 * Gets the position of this scroll bar.
	 * @return A float value,
	 */
	public float getPos() {
		return pos;
	}

	/**
	 * Sets the position of this scroll bar.
	 * @param pos A float value.
	 */
	public void setPos(float pos) {
		this.pos = pos;
		onUpdate(pos, max);
	}
	
	/**
	 * Gets the maximum value of this scroll bar.
	 * @return A float value.
	 */
	public float getMax() {
		return max;
	}

	/**
	 * Sets the maximum value of this scroll bar.
	 * @param max A float value.
	 */
	public void setMax(float max) {
		this.max = max;
	}
	
	/**
	 * Called when a scroll bar updates.
	 * @param pos The position of the scroll bar
	 * @param max The maximum value of the scroll bar.
	 */
	public void onUpdate(float pos, float max) {
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintScrollBar(g, scaling, this);
	}
}

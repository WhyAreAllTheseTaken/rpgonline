package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class ScrollBar extends Component {
	private float pos;
	private float max;

	public ScrollBar(float pos, float max) {
		super();
		this.setPos(pos);
		this.max = max;
	}

	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateScrollBarBounds(c, this);
	}
	
	@Override
	public void mouseClickedLeft(float x, float y) {
		if (y < getH() / 20) {
			setPos(0);
		}
		if (y > getH() / 20 * 19) {
			setPos(max);
		}
	}
	
	@Override
	public void mouseDraggedLeft(float ox, float oy, float nx, float ny) {
		pos = ny / getH() * max;
	}

	public float getPos() {
		return pos;
	}

	public void setPos(float pos) {
		this.pos = pos;
		onUpdate(pos, max);
	}
	
	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}
	
	public void onUpdate(float pos, float max) {
		
	}
	
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintScrollBar(g, scaling, this);
	}
}

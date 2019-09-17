package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class Slider extends Component {
	private float pos;
	private float max;

	public Slider(float pos, float max) {
		super();
		this.setPos(pos);
		this.max = max;
	}

	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateSliderBounds(c, this);
	}
	
	@Override
	public void mouseDraggedLeft(float ox, float oy, float nx, float ny) {
		pos = nx / getW() * max;
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
		ThemeManager.getTheme().paintSlider(g, scaling, this);
	}
}

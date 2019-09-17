package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

public class Button extends Component {
	private boolean state;
	private String text;
	
	public Button(String text) {
		super();
		this.text = text;
	}

	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintButton(g, scaling, this);
	}
	
	@Override
	public void mouseClickedLeft(float x, float y) {
		setState(true);
		onAction(x, y, state);
	}
	
	@Override
	public void mouseUnpressedLeft(float x, float y) {
		setState(false);
	}
	
	@Override
	public void mouseExited(float x, float y) {
		setState(false);
	}
	
	public boolean isState() {
		return state;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
	
	public void onAction(float x, float y, boolean state) {
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateButtonBounds(c, this);
	}
}

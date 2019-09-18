package io.github.tomaso2468.rpgonline.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A clickable button.
 * @author Tomas
 *
 */
public class Button extends Component {
	/**
	 * The state of the button.
	 */
	private boolean state;
	/**
	 * The text of the button.
	 */
	private String text;
	
	/**
	 * Constructs a new button.
	 * @param text The text of the button.
	 */
	public Button(String text) {
		super();
		this.text = text;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintButton(g, scaling, this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClickedLeft(float x, float y) {
		setState(true);
		onAction(x, y, state);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseExited(float x, float y) {
		setState(false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float delta) {
		setState(false);
	}
	
	/**
	 * Gets the state of the button.
	 * @return {@code true} if the button is pressed down, {@code false} otherwise.
	 */
	public boolean isState() {
		return state;
	}
	
	/**
	 * Sets the state of the button.
	 * @param state {@code true} if the button is pressed down, {@code false} otherwise.
	 */
	public void setState(boolean state) {
		this.state = state;
	}
	
	/**
	 * The event triggered for the button action.
	 * @param x The X position of the mouse.
	 * @param y The Y position of the mouse,
	 * @param state {@code true} if the button is pressed down, {@code false} otherwise.
	 */
	public void onAction(float x, float y, boolean state) {
		
	}

	/**
	 * Gets the text of this button.
	 * @return A string.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of this button.
	 * @param text A string.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateButtonBounds(c, this);
	}
}

package io.github.tomaso2468.rpgonline.gui;

/**
 * A button that can be toggled on and off.
 * @author Tomas
 *
 */
public class ToggleButton extends Button {
	/**
	 * Constructs a new ToggleButton.
	 * @param text The label of the button.
	 * @param state The state of the button.
	 */
	public ToggleButton(String text, boolean state) {
		super(text);
		setState(state);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClickedLeft(float x, float y) {
		setState(!isState());
		onAction(x, y, isState());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseExited(float x, float y) {
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float delta) {
		
	}
}

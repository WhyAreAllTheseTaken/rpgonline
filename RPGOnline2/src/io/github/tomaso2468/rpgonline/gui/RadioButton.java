package io.github.tomaso2468.rpgonline.gui;

/**
 * A button used to create a series of selectable options. This should be added to a RadioGroup.
 * @author Tomas
 * @see io.github.tomaso2468.rpgonline.gui.RadioGroup
 */
public class RadioButton extends CheckBox {
	/**
	 * The group this button belongs to.
	 */
	RadioGroup group;
	/**
	 * Constructs a new RadioButton.
	 * @param text The text label of this button.
	 * @param state The state of this button.
	 */
	public RadioButton(String text, boolean state) {
		super(text, state);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onAction(float x, float y, boolean state) {
		group.buttonPressed(this);
	}
}

package io.github.tomaso2468.rpgonline.gui;

import io.github.tomaso2468.rpgonline.gui.layout.ListLayout;

/**
 * A group of radio buttons.
 * @author Tomas
 *
 */
public class RadioGroup extends ListLayout {
	/**
	 * The index of the selected button.
	 */
	private int index = 0;
	
	/**
	 * Constructs a new radio button group.
	 */
	public RadioGroup() {
		super(0);
	}
	
	/**
	 * Adds a new radio button to this radio group.
	 */
	@Override
	public void add(Component c) {
		if (!(c instanceof RadioButton)) {
			throw new IllegalArgumentException(c.getClass() + " is not an instance of " + RadioButton.class);
		}
		super.add(c);
		
		((RadioButton) c).group = this;
	}
	
	/**
	 * The event for when a new button is selected
	 * @param index The index of the selected button.
	 */
	public void onAction(int index) {
		
	}
	
	/**
	 * Gets the index of the selected option.
	 * @return An int value.
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Gets the name of the button of the specified index.
	 * @param index The index to get the name of.
	 * @return A string value.
	 */
	public String getButton(int index) {
		return ((RadioButton) components.get(index)).getText();
	}

	/**
	 * Called by a radio button when that button is pressed.
	 * @param radioButton The button that was pressed.
	 */
	public void buttonPressed(RadioButton radioButton) {
		index = components.indexOf(radioButton);
		
		int i = 0;
		for (Component component : components) {
			if (i == index) {
				((RadioButton) component).setState(true);
			} else {
				((RadioButton) component).setState(false);
			}
			i += 1;
		}
		
		onAction(index);
	}
}

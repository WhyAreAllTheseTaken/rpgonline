package io.github.tomaso2468.rpgonline.gui;

/**
 * A button that, when pressed, cycles through a series of values.
 * @author Tomas
 *
 */
public class CycleButton extends Button {
	/**
	 * The index of the current value.
	 */
	private int index;
	/**
	 * The values for this button.
	 */
	private String[] states;
	/**
	 * The prefix of this button.
	 */
	private String prefix;
	
	/**
	 * Constructs a new CycleButton.
	 * @param prefix The prefix of this button.
	 * @param states The values of this button.
	 * @param index The default index of the state of this button.
	 */
	public CycleButton(String prefix, String[] states, int index) {
		super(prefix + states[index]);
		this.prefix = prefix;
		this.states = states;
		this.index = index;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onAction(float x, float y, boolean state) {
		index = (index + 1) % states.length;
		setText(prefix + states[index]);
	}
	
	/**
	 * Gets the index of the current state of this button.
	 * @return An int value.
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Gets the state of this button.
	 * @return An array of strings.
	 */
	public String[] getStates() {
		return states;
	}
	
	/**
	 * Sets the index of this button.
	 * @param index An int value.
	 */
	public void setIndex(int index) {
		this.index = index;
		setText(prefix + states[index]);
	}
	
	/**
	 * Sets the possible states of this button.
	 * @param states An array of strings.
	 */
	public void setStates(String[] states) {
		this.states = states;
	}
	
	/**
	 * Sets the prefix of this button.
	 * @param prefix A string value.
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * Gets the prefix of this button.
	 * @return A string value.
	 */
	public String getPrefix() {
		return prefix;
	}
}

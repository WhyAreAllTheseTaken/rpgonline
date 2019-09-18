package io.github.tomaso2468.rpgonline.gui;

/**
 * A mutliline unformatted text box.
 * @author Tomas
 *
 */
public class TextBox extends TextComponent {

	/**
	 * Constructs a new TextBox.
	 * @param text The default text of the text box.
	 */
	public TextBox(String text) {
		super(text);
	}

	/**
	 * Constructs a new TextBox
	 */
	public TextBox() {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isMultiLine() {
		return false;
	}

}

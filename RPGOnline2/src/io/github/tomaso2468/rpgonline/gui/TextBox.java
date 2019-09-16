package io.github.tomaso2468.rpgonline.gui;

public class TextBox extends TextComponent {

	public TextBox(String text) {
		super(text);
	}

	public TextBox() {
	}
	
	@Override
	protected boolean isMultiLine() {
		return false;
	}

}

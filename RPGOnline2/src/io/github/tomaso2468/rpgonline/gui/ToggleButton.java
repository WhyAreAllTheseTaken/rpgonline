package io.github.tomaso2468.rpgonline.gui;

public class ToggleButton extends Button {
	public ToggleButton(String text, boolean state) {
		super(text);
		setState(state);
	}

	@Override
	public void mouseClicked(float x, float y) {
		setState(!isState());
		onAction(x, y, isState());
	}
}

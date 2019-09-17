package io.github.tomaso2468.rpgonline.gui;

public class RadioButton extends CheckBox {
	RadioGroup group;
	public RadioButton(String text, boolean state) {
		super(text, state);
	}
	
	@Override
	public void onAction(float x, float y, boolean state) {
		group.buttonPressed(this);
	}
}

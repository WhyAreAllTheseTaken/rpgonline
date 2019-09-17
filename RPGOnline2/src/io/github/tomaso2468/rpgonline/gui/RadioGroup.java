package io.github.tomaso2468.rpgonline.gui;

import io.github.tomaso2468.rpgonline.gui.layout.ListLayout;

public class RadioGroup extends ListLayout {
	private int index = 0;
	@Override
	public void add(Component c) {
		if (!(c instanceof RadioButton)) {
			throw new IllegalArgumentException(c.getClass() + " is not an instance of " + RadioButton.class);
		}
		super.add(c);
		
		((RadioButton) c).group = this;
	}
	
	public void onAction(int index) {
		
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getButton(int index) {
		return ((RadioButton) components.get(index)).getText();
	}

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

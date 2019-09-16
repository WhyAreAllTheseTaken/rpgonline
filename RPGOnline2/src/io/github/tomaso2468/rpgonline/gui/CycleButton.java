package io.github.tomaso2468.rpgonline.gui;

public class CycleButton extends Button {
	private int index;
	private String[] states;
	private String prefix;
	
	public CycleButton(String prefix, String[] states, int index) {
		super(prefix + states[index]);
		this.prefix = prefix;
		this.states = states;
		this.index = index;
	}

	@Override
	public void onAction(float x, float y, boolean state) {
		index = (index + 1) % states.length;
		setText(prefix + states[index]);
	}
	
	public int getIndex() {
		return index;
	}
	
	public String[] getStates() {
		return states;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setStates(String[] states) {
		this.states = states;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}
}

package io.github.tomaso2468.rpgonline.gui;

import java.util.HashMap;
import java.util.Map;

public class TabbedContainer extends Container {
	private Map<String, Component> tabs = new HashMap<>();
	private ToolBar topbar;
	private Component current;
	
	public TabbedContainer() {
		topbar = new ToolBar();
		components.add(topbar);
		
		current = null;
		
		setBounds(0, 0, 1, 1);
	}
	
	@Override
	public void onResize(float x, float y, float w, float h) {
		topbar.setBounds(0, 0, getW(), topbar.getDefaultBounds(this).getHeight());
		
		if (current != null) {
			current.setBounds(0, topbar.getDefaultBounds(this).getHeight(), getW(), getH() - topbar.getDefaultBounds(this).getHeight());
		}
	}
	
	public void addTab(String name, Component tab) {
		if (tabs.containsKey(name)) {
			if (current == tabs.get(name)) {
				components.remove(current);
				
				for (Component c : topbar.components) {
					if (((TabButton) c).getText().equals(name)) {
						topbar.remove(c);
					}
				}
				
				current = null;
			}
		}
		
		tabs.put(name, tab);
		
		TabButton b = new TabButton(name) {
			@Override
			public void onAction(float x, float y, boolean state) {
				if (current != null) {
					components.remove(current);
				}
				current = tab;
				components.add(current);
				TabbedContainer.this.setBounds(TabbedContainer.this.getBounds());
			}
		};
		topbar.add(b);
	}

}

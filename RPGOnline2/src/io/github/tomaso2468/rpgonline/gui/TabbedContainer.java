package io.github.tomaso2468.rpgonline.gui;

import java.util.HashMap;
import java.util.Map;

/**
 * A container that separates various components into tabs.
 * @author Tomas
 *
 */
public class TabbedContainer extends Container {
	/**
	 * The map of names to the tab components.
	 */
	private Map<String, Component> tabs = new HashMap<>();
	/**
	 * The tab bar of the this container.
	 */
	private ToolBar topbar;
	/**
	 * The currently displaying tab.
	 */
	private Component current;
	
	/**
	 * Constructs a new tabbed container.
	 */
	public TabbedContainer() {
		topbar = new ToolBar();
		components.add(topbar);
		
		current = null;
		
		setBounds(0, 0, 1, 1);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResize(float x, float y, float w, float h) {
		topbar.setBounds(0, 0, getW(), topbar.getDefaultBounds(this).getHeight());
		
		if (current != null) {
			current.setBounds(0, topbar.getDefaultBounds(this).getHeight(), getW(), getH() - topbar.getDefaultBounds(this).getHeight());
		}
	}
	
	/**
	 * Adds a tab to this container.
	 * @param name The name of this tab.
	 * @param tab The component to add as a tab.
	 */
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

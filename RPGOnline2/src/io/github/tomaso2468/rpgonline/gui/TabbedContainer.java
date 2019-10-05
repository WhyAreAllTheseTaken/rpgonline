/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.gui;

import java.util.HashMap;
import java.util.Map;

/**
 * A container that separates various components into tabs.
 * @author Tomaso2468
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

package io.github.tomaso2468.rpgonline.gui;

import io.github.tomaso2468.rpgonline.gui.layout.FillGridLayout;

public class Table extends FillGridLayout {
	public Table(String[][] data) {
		super(data.length, data[0].length);
		
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[0].length; y++) {
				add(new Label(data[x][y]), x, y);
			}
		}
	}
	
	public void setText(int x, int y, String s) {
		for (Component c : components) {
			if(this.x.get(c) == x && this.y.get(c) == y) {
				((Label) c).setText(s);
				return;
			}
		}
		
		// No component found add a new one.
		
		if (grid_y < y + 1) {
			grid_y = y + 1;
		}
		if (grid_x < x + 1) {
			grid_x = x + 1;
		}
		
		// Cause the system to recalculate bounds.
		setBounds(getBounds());
		
		add(new Label(s), x, y);
	}
}

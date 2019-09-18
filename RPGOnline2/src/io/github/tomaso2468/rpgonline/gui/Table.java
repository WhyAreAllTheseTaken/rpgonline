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

import io.github.tomaso2468.rpgonline.gui.layout.FillGridLayout;

/**
 * A table component.
 * @author Tomas
 *
 */
public class Table extends FillGridLayout {
	/**
	 * Constructs a new Table.
	 * @param data The data of the table with the first index being columns.
	 */
	public Table(String[][] data) {
		super(data.length, data[0].length);
		
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[0].length; y++) {
				add(new Label(data[x][y]), x, y);
			}
		}
	}
	
	/**
	 * Sets the text at a specified position.
	 * @param x The X position of the cell.
	 * @param y The Y position of the cell.
	 * @param s The text of the cell.
	 */
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

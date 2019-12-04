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

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.gui.layout.ListLayout;

/**
 * A group of radio buttons.
 * @author Tomaso2468
 *
 */
public class RadioGroup extends ListLayout {
	/**
	 * The index of the selected button.
	 */
	private int index = 0;
	
	/**
	 * Constructs a new radio button group.
	 */
	public RadioGroup() {
		super(0);
	}
	
	/**
	 * Adds a new radio button to this radio group.
	 */
	@Override
	public void add(Game game, Component c) {
		if (!(c instanceof RadioButton)) {
			throw new IllegalArgumentException(c.getClass() + " is not an instance of " + RadioButton.class);
		}
		super.add(game, c);
		
		((RadioButton) c).group = this;
	}
	
	/**
	 * The event for when a new button is selected
	 * @param index The index of the selected button.
	 */
	public void onAction(int index) {
		
	}
	
	/**
	 * Gets the index of the selected option.
	 * @return An int value.
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Gets the name of the button of the specified index.
	 * @param index The index to get the name of.
	 * @return A string value.
	 */
	public String getButton(int index) {
		return ((RadioButton) components.get(index)).getText();
	}

	/**
	 * Called by a radio button when that button is pressed.
	 * @param radioButton The button that was pressed.
	 */
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

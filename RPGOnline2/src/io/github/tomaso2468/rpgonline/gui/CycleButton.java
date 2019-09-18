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

/**
 * A button that, when pressed, cycles through a series of values.
 * @author Tomas
 *
 */
public class CycleButton extends Button {
	/**
	 * The index of the current value.
	 */
	private int index;
	/**
	 * The values for this button.
	 */
	private String[] states;
	/**
	 * The prefix of this button.
	 */
	private String prefix;
	
	/**
	 * Constructs a new CycleButton.
	 * @param prefix The prefix of this button.
	 * @param states The values of this button.
	 * @param index The default index of the state of this button.
	 */
	public CycleButton(String prefix, String[] states, int index) {
		super(prefix + states[index]);
		this.prefix = prefix;
		this.states = states;
		this.index = index;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onAction(float x, float y, boolean state) {
		index = (index + 1) % states.length;
		setText(prefix + states[index]);
	}
	
	/**
	 * Gets the index of the current state of this button.
	 * @return An int value.
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Gets the state of this button.
	 * @return An array of strings.
	 */
	public String[] getStates() {
		return states;
	}
	
	/**
	 * Sets the index of this button.
	 * @param index An int value.
	 */
	public void setIndex(int index) {
		this.index = index;
		setText(prefix + states[index]);
	}
	
	/**
	 * Sets the possible states of this button.
	 * @param states An array of strings.
	 */
	public void setStates(String[] states) {
		this.states = states;
	}
	
	/**
	 * Sets the prefix of this button.
	 * @param prefix A string value.
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * Gets the prefix of this button.
	 * @return A string value.
	 */
	public String getPrefix() {
		return prefix;
	}
}

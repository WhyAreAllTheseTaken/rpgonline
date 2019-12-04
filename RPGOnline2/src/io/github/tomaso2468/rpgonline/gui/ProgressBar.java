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
import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderException;

/**
 * A progress bar.
 * @author Tomaso2468
 *
 */
public class ProgressBar extends Component {
	/**
	 * The maximum value of this progress bar.
	 */
	private int max;
	/**
	 * The value of this progress bar.
	 */
	private int value;
	/**
	 * Determines if the progress bar is in the intermediate mode.
	 */
	private boolean intermediate;
	
	/**
	 * Constructs a new ProgressBar.
	 * @param max The maximum value of this progress bar.
	 * @param value The value of this progress bar.
	 */
	public ProgressBar(int max, int value) {
		super();
		this.max = max;
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Game game, Graphics g, float scaling) throws RenderException {
		ThemeManager.getTheme().paintProgressBar(game, g, scaling, this);
	}

	/**
	 * Gets the maximum value of this progress bar.
	 * @return An int value.
	 */
	public int getMax() {
		return max;
	}

	/**
	 * Sets the maximum value of this progress bar.
	 * @param max An int value.
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * Gets the value of this progress bar.
	 * @return An int value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value of this progress bar.
	 * @param value An int value.
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Determines if the progress bar is in the intermediate mode.
	 * @return {@code true} if the progress bar is in intermediate mode, {@code false otherwise}.
	 */
	public boolean isIntermediate() {
		return intermediate;
	}

	/**
	 * Sets if the progress bar is in the intermediate mode.
	 * @param intermediate {@code true} if the progress bar is in intermediate mode, {@code false otherwise}.
	 */
	public void setIntermediate(boolean intermediate) {
		this.intermediate = intermediate;
	}
}

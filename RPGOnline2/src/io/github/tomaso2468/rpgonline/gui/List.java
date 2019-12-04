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

import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderException;

/**
 * A list of selectable values.
 * @author Tomaso2468
 *
 */
public class List extends RadioGroup {

	/**
	 * Constructs a new list.
	 * @param data The values of the list,
	 */
	public List(String[] data) {
		for (int i = 0; i < data.length; i++) {
			add(new ListElement(data[i], i == 0 ? true : false));
		}
	}

	/**
	 * A single element in the list.
	 * @author Tomas
	 *
	 */
	public static class ListElement extends RadioButton {
		/**
		 * Constructs a new ListElement.
		 * @param text The text of the list element.
		 * @param state The state of the element.
		 */
		public ListElement(String text, boolean state) {
			super(text, state);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void paint(Game game, Graphics g, float scaling) throws RenderException {
			ThemeManager.getTheme().paintListElement(game, g, scaling, this);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Rectangle getDefaultBounds(Container c) {
			return ThemeManager.getTheme().calculateListElementBounds(c, this);
		}
	}
}

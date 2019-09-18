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
package io.github.tomaso2468.rpgonline;

import org.newdawn.slick.Color;

/**
 * A group of constants providing basic colors suitable for different backgrounds.
 * @author Tomas
 */
public final class TextColor {
	/**
	 * Prevent instantiation
	 */
	private TextColor() {
		
	}
	/**
	 * The color themes for a dark background.
	 */
	public static ColorList DARK = new ColorList(new Color(255, 32, 32), Color.yellow, Color.orange, Color.green,
			Color.cyan, new Color(16, 128, 255), new Color(128, 32, 255), Color.magenta, Color.pink, Color.white,
			Color.lightGray);
	/**
	 * The color themes for a light background.
	 */
	public static ColorList LIGHT = new ColorList(Color.red.darker(), Color.yellow.darker(), Color.orange.darker(),
			Color.green.darker(), Color.cyan.darker(), Color.blue, new Color(128, 32, 255).darker(),
			Color.magenta.darker(), Color.pink.darker(), Color.black, Color.darkGray);

	/**
	 * A class containing all constants for a specific background.
	 * @author Tomas
	 *
	 */
	public static class ColorList {
		/**
		 * Red text.
		 */
		public final Color RED;
		/**
		 *  Yellow text.
		 */
		public final Color YELLOW;
		/**
		 * Orange text.
		 */
		public final Color ORANGE;
		/**
		 * Green text.
		 */
		public final Color GREEN;
		/**
		 * Aqua/Cyan text.
		 */
		public final Color AQUA;
		/**
		 * Blue text.
		 */
		public final Color BLUE;
		/**
		 * Purple text.
		 */
		public final Color PURPLE;
		/**
		 * Magenta text.
		 */
		public final Color MAGENTA;
		/**
		 * Pink text.
		 */
		public final Color PINK;
		/**
		 * Black or white text. This should be the opposite of the background color.
		 */
		public final Color BLACK_WHITE;
		/**
		 * The color gray.
		 */
		public final Color GRAY = Color.gray;
		/**
		 * A lighter/darker gray.
		 */
		public final Color GRAY2;

		/**
		 * Constructs a new ColorList
		 * @param rED Red text
		 * @param yELLOW Yellow text
		 * @param oRANGE Orange text
		 * @param gREEN Green text
		 * @param aQUA Aqua text
		 * @param bLUE Blue text
		 * @param pURPLE Purple text
		 * @param mAGENTA Magenta text
		 * @param pINK Pink text
		 * @param bLACK_WHITE Black or white text. This should be the opposite of the background color.
		 * @param gRAY A lighter/darker gray.
		 */
		private ColorList(Color rED, Color yELLOW, Color oRANGE, Color gREEN, Color aQUA, Color bLUE, Color pURPLE,
				Color mAGENTA, Color pINK, Color bLACK_WHITE, Color gRAY) {
			super();
			RED = rED;
			YELLOW = yELLOW;
			ORANGE = oRANGE;
			GREEN = gREEN;
			AQUA = aQUA;
			BLUE = bLUE;
			PURPLE = pURPLE;
			MAGENTA = mAGENTA;
			PINK = pINK;
			BLACK_WHITE = bLACK_WHITE;
			GRAY2 = gRAY;
		}
	}
}

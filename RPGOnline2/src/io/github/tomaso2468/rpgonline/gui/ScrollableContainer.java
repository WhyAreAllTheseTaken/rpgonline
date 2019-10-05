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

import io.github.tomaso2468.rpgonline.gui.layout.Border;
import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;

/**
 * A container that is scrollable.
 * @author Tomaso2468
 *
 */
public class ScrollableContainer extends Container {
	/**
	 * The internal container of this component.
	 */
	private Container container;
	/**
	 * The scroll bar for this component.
	 */
	private ScrollBar bar;
	
	/**
	 * Constructs a new scrollable container.
	 */
	public ScrollableContainer() {
		Border border = new Border(ThemeManager.getTheme().getScrollableBorder());
		components.add(border);
		
		Container base_container = new Container() {
			@Override
			public void onResize(float x, float y, float w, float h) {
				super.onResize(x, y, w, h);
				
				if (container != null) {
					container.setBounds(0, -bar.getPos(), w - bar.getDefaultBounds(this).getWidth(), h);
					bar.setBounds(w - bar.getDefaultBounds(this).getWidth(), 0, bar.getDefaultBounds(this).getWidth(), h);
				}
			}
		};
		border.add(base_container);
		
		bar = new ScrollBar(0, 1) {
			@Override
			public void onUpdate(float pos, float max) {
				base_container.setBounds(base_container.getBounds());
			}
		};
		
		container = new Border(ThemeManager.getTheme().getScrollableBorder()) {
			@Override
			public void onResize(float x, float y, float w, float h) {
				super.onResize(x, y, w, h);
				
				bar.setMax(container.getH());
			}
		};
		base_container.add(container);
		
		base_container.setBounds(base_container.getBounds());
	}

}

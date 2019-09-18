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
package io.github.tomaso2468.rpgonline.gui.layout;

import java.util.HashMap;
import java.util.Map;

import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.Component;

/**
 * A layout that places UI elements in the corner.
 * @author Tomas
 *
 */
public class CornerLayout extends Layout {
	/**
	 * The map of components to their corners.
	 */
	private final Map<Component, Corner> map = new HashMap<>();

	/**
	 * Adds a component to the specified corner.
	 * @param c The component to add.
	 * @param corner The corner to add the component to.
	 */
	public void add(Component c, Corner corner) {
		components.add(c);
		map.put(c, corner);

		Debugger.start("gui-layout");
		switch (corner) {
		case BOTTOM_LEFT:
			c.setBounds(0, getH() - c.getDefaultBounds(this).getHeight(), c.getDefaultBounds(this).getWidth(),
					c.getDefaultBounds(this).getHeight());
			break;
		case BOTTOM_RIGHT:
			c.setBounds(getW() - c.getDefaultBounds(this).getWidth(), getH() - c.getDefaultBounds(this).getHeight(),
					c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
			break;
		case TOP_LEFT:
			c.setBounds(0, getH() - c.getDefaultBounds(this).getHeight(), c.getDefaultBounds(this).getWidth(),
					c.getDefaultBounds(this).getHeight());
			break;
		case TOP_RIGHT:
			c.setBounds(getW() - c.getDefaultBounds(this).getWidth(), getH() - c.getDefaultBounds(this).getHeight(),
					c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
			break;
		default:
			break;
		}
		Debugger.stop("gui-layout");
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(Component c) {
		add(c, Corner.TOP_LEFT);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResize(float x, float y, float w, float h) {
		Debugger.start("gui-layout");
		for (Component c : components) {
			Corner corner = map.get(c);

			switch (corner) {
			case BOTTOM_LEFT:
				c.setBounds(0, getH() - c.getDefaultBounds(this).getHeight(), c.getDefaultBounds(this).getWidth(),
						c.getDefaultBounds(this).getHeight());
				break;
			case BOTTOM_RIGHT:
				c.setBounds(getW() - c.getDefaultBounds(this).getWidth(), getH() - c.getDefaultBounds(this).getHeight(),
						c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
				break;
			case TOP_LEFT:
				c.setBounds(0, getH() - c.getDefaultBounds(this).getHeight(), c.getDefaultBounds(this).getWidth(),
						c.getDefaultBounds(this).getHeight());
				break;
			case TOP_RIGHT:
				c.setBounds(getW() - c.getDefaultBounds(this).getWidth(), getH() - c.getDefaultBounds(this).getHeight(),
						c.getDefaultBounds(this).getWidth(), c.getDefaultBounds(this).getHeight());
				break;
			default:
				break;
			}
		}
		Debugger.stop("gui-layout");
	}
}

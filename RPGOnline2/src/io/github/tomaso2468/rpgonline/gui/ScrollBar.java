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

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;
import io.github.tomaso2468.rpgonline.render.Graphics;

/**
 * A vertical scroll bar.
 * @author Tomaso2468
 *
 */
public class ScrollBar extends Component {
	/**
	 * The position of the scroll bar.
	 */
	private float pos;
	/**
	 * The maximum value of the scroll bar.
	 */
	private float max;

	/**
	 * Constructs a new scroll bar.
	 * @param pos The position of the scroll bar.
	 * @param max The maximum value of the scroll bar.
	 */
	public ScrollBar(float pos, float max) {
		super();
		this.setPos(pos);
		this.max = max;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getDefaultBounds(Container c) {
		return ThemeManager.getTheme().calculateScrollBarBounds(c, this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseClickedLeft(float x, float y) {
		if (y < getH() / 20) {
			setPos(0);
		}
		if (y > getH() / 20 * 19) {
			setPos(max);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseDraggedLeft(float ox, float oy, float nx, float ny) {
		pos = ny / getH() * max;
		if (pos < 0) {
			pos = 0;
		}
		if (pos > max) {
			pos = max;
		}
	}

	/**
	 * Gets the position of this scroll bar.
	 * @return A float value,
	 */
	public float getPos() {
		return pos;
	}

	/**
	 * Sets the position of this scroll bar.
	 * @param pos A float value.
	 */
	public void setPos(float pos) {
		this.pos = pos;
		onUpdate(pos, max);
	}
	
	/**
	 * Gets the maximum value of this scroll bar.
	 * @return A float value.
	 */
	public float getMax() {
		return max;
	}

	/**
	 * Sets the maximum value of this scroll bar.
	 * @param max A float value.
	 */
	public void setMax(float max) {
		this.max = max;
	}
	
	/**
	 * Called when a scroll bar updates.
	 * @param pos The position of the scroll bar
	 * @param max The maximum value of the scroll bar.
	 */
	public void onUpdate(float pos, float max) {
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		ThemeManager.getTheme().paintScrollBar(g, scaling, this);
	}
}

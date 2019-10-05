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
package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A fast (but very bad) blur algorithm.
 * @author Tomaso2468
 */
public class FastBlur implements PostEffect {
	/**
	 * The buffer used for this effect.
	 */
	private Image buffer2;
	/**
	 * The size of the blur.
	 */
	private int size;
	
	/**
	 * Constructs a new FastBlur instance
	 * @param size The radius of the blur.
	 */
	public FastBlur(int size) {
		this.size = size * 2 + 1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		if (buffer2 == null) {
			buffer2 = new Image((int) container.getWidth() / (int) (size), container.getHeight() / (int) (size));
		} else if (container.getWidth() / (int) (size) != buffer2.getWidth() || container.getHeight() / (int) (size) != buffer2.getHeight()) {
			buffer2.destroy();
			buffer2 = new Image((int) container.getWidth() / (int) (size), container.getHeight() / (int) (size));
		}
		
		buffer.setFilter(Image.FILTER_LINEAR);
		buffer2.setFilter(Image.FILTER_LINEAR);
		
		g.drawImage(buffer.getScaledCopy(buffer2.getWidth(), buffer2.getHeight()), 0, 0);
		g.copyArea(buffer2, 0, 0);
		g.drawImage(buffer2.getScaledCopy(buffer.getWidth(), buffer.getHeight()), 0, 0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() throws SlickException {
		buffer2.destroy();
	}

}

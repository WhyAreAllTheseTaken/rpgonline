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

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * An effect that causes bright areas of the screen to bleed.
 * @author Tomas
 * 
 * @see io.github.tomaso2468.rpgonline.post.Blur
 * @see io.github.tomaso2468.rpgonline.post.HighPass
 */
public class Bloom extends MultiEffect {
	/**
	 * A second buffer used for storing data.
	 */
	private Image buffer2;
	
	/**
	 * Constructs a new bloom effect.
	 * @param value The threshold for the bloom effect.
	 * @param blur The amount to blur by.
	 * @param sigma used by the blur effect.
	 */
	public Bloom(float value, int blur, float sigma) {
		super(new HighPass(value), new Blur(blur, sigma));
	}
	/**
	 * Constructs a new bloom effect.
	 * @param value The threshold for the bloom effect.
	 */
	public Bloom(float value) {
		this(value, 5, 2);
	}
	
	/**
	 * Constructs a new bloom effect with a threshold of 0.95f.
	 */
	public Bloom() {
		this(0.95f, 5, 2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		if (buffer2 == null) {
			buffer2 = new Image(container.getWidth(), container.getHeight());
		} else if (container.getWidth() != buffer2.getWidth() || container.getHeight() != buffer2.getHeight()) {
			buffer2.destroy();
			buffer2 = new Image(container.getWidth(), container.getHeight());
		}
		
		g.copyArea(buffer2, 0, 0);
		
		super.doPostProcess(container, game, buffer, g);
		
		if (!Keyboard.isKeyDown(Keyboard.KEY_F10)) {
			g.setDrawMode(Graphics.MODE_ADD);
			g.drawImage(buffer2, 0, 0);
			g.setDrawMode(Graphics.MODE_NORMAL);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() throws SlickException {
		super.dispose();
		buffer2.destroy();
	}
}

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
package io.github.tomaso2468.rpgonline.gui.layer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.post.PostEffect;

/**
 * A layer that applies a post-processing effect.
 * @author Tomas
 *
 */
public class PostLayer extends Layer {
	/**
	 * The effect to apply.
	 */
	private PostEffect post;
	/**
	 * A buffer used for processing.
	 */
	private Image buffer;
	/**
	 * The game container object.
	 */
	private GameContainer c;
	
	/**
	 * Constructs a new PostLayer.
	 * @param post The effect to apply.
	 */
	public PostLayer(PostEffect post) {
		super();
		this.post = post;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, float scaling) throws SlickException {
		if (buffer == null) {
			buffer = new Image(c.getWidth(), c.getHeight());
		}
		if (buffer.getWidth() != c.getWidth()
				|| buffer.getHeight() != c.getHeight()) {
			buffer.destroy();
			buffer = new Image(c.getWidth(), c.getHeight());
		}
		
		g.copyArea(buffer, 0, 0);
		g.pushTransform();
		post.doPostProcess(null, null, buffer, g);
		g.popTransform();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void containerUpdate(GameContainer c) {
		this.c = c;
	}
}

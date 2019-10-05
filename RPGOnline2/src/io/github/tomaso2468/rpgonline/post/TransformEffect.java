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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.StateBasedGame;

/**
 * An effect based on a transform.
 * 
 * @author Tomaso2468
 */
public class TransformEffect implements PostEffect {
	/**
	 * The transform.
	 */
	private final Transform trans;

	/**
	 * Creates a {@code TransformEffect}.
	 * 
	 * @param trans The transform.
	 */
	public TransformEffect(Transform trans) {
		this.trans = trans;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		g.clear();

		// push the current transformation matrix to the stack
		GL11.glPushMatrix();

		float[] tm = trans.getMatrixPosition(); // get the transform matrix

		// pad the transform to get a 4x4 3d affine transform
		float[] toBuffer = { tm[0], tm[3], 0, tm[6], tm[1], tm[4], 0, tm[7], 0, 0, 1, 0, tm[2], tm[5], 0, 1 };

		// GL11 wants a "direct" FloatBuffer, but you can only get that by creating a
		// direct ByteBuffer
		// and then creating a FloatBuffer as a view of that ByteBuffer.
		// the ByteBuffer is allocated 16*4 bytes, because there are 16 floats and each
		// float needs 4 bytes
		ByteBuffer bb = ByteBuffer.allocateDirect(16 * 4);

		// this has something to do with the default byte order setting in Java being
		// inappropriate
		bb.order(ByteOrder.nativeOrder());

		for (float f : toBuffer) {
			bb.putFloat(f);
		}
		bb.rewind();
		FloatBuffer transformBuffer = bb.asFloatBuffer();

		GL11.glMultMatrix(transformBuffer);

		buffer.draw(0, 0);

		// Pop the matrix so that these transformations are not applied to
		// all the other images.
		GL11.glPopMatrix();
	}

}

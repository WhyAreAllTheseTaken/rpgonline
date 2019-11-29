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
package io.github.tomaso2468.rpgonline.render.opengl;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.post.LUT;

public abstract class GL12Renderer extends GL11Renderer {
	public static final int LUT_8 = 8;
	public static final int LUT_16 = 16;
	public static final int LUT_32 = 32;
	public static final int LUT_64 = 64;

	@Override
	public LUT loadLUT(URL loc) throws IOException {
		GL11.glEnable(GL12.GL_TEXTURE_3D);
		Log.info("Loading LUT from " + loc.toString());
		DataInputStream din = new DataInputStream(loc.openStream());

		byte format1 = din.readByte();
		byte format2 = din.readByte();
		byte format3 = din.readByte();

		if (!(format1 == 'L' && format2 == 'U' && format3 == 'T')) {
			Log.error("Format bytes: " + (char) format1 + (char) format2 + (char) format3);
			throw new IOException("LUT file is not in a known format.");
		}
		
		byte compress = din.readByte();
		
		if (compress != 0) {
			// File is compressed.
			din = new DataInputStream(new GZIPInputStream(din));
		}

		int size = din.readInt();
		Log.debug("LUT sample count: " + size);

		float[][][][] data = new float[3][size][size][size];

		int type = din.readInt();
		Log.debug("LUT sample size: " + type);
		switch (type) {
		case LUT_8:
			for (int r = 0; r < size; r++) {
				for (int g = 0; g < size; g++) {
					for (int b = 0; b < size; b++) {
						data[0][r][g][b] = ((float) din.read()) / 255;
						data[1][r][g][b] = ((float) din.read()) / 255;
						data[2][r][g][b] = ((float) din.read()) / 255;
					}
				}
			}
			break;
		case LUT_16:
			float ushort_max = (float) Math.pow(2, 16) - 1;
			for (int r = 0; r < size; r++) {
				for (int g = 0; g < size; g++) {
					for (int b = 0; b < size; b++) {
						data[0][r][g][b] = ((float) (din.readShort() & 0xffff)) / ushort_max;
						data[1][r][g][b] = ((float) (din.readShort() & 0xffff)) / ushort_max;
						data[2][r][g][b] = ((float) (din.readShort() & 0xffff)) / ushort_max;
					}
				}
			}
			break;
		case LUT_32:
			for (int r = 0; r < size; r++) {
				for (int g = 0; g < size; g++) {
					for (int b = 0; b < size; b++) {
						data[0][r][g][b] = din.readFloat();
						data[1][r][g][b] = din.readFloat();
						data[2][r][g][b] = din.readFloat();
					}
				}
			}
			break;
		case LUT_64:
			Log.warn("A 64 bit floating point LUT is being used. This will be lowered to 32 bits.");
			for (int r = 0; r < size; r++) {
				for (int g = 0; g < size; g++) {
					for (int b = 0; b < size; b++) {
						data[0][r][g][b] = (float) din.readDouble();
						data[1][r][g][b] = (float) din.readDouble();
						data[2][r][g][b] = (float) din.readDouble();
					}
				}
			}
			break;
		default:
			throw new IOException("Unknown LUT sample size: " + type);
		}
		
		din.close();

		GLLUT lut = new GLLUT(size, data);
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(size * size * size * 3);
		for (int r = 0; r < size; r++) {
			for (int g = 0; g < size; g++) {
				for (int b = 0; b < size; b++) {
					buffer.put((byte) (data[0][r][g][b] * 255));
					buffer.put((byte) (data[1][r][g][b] * 255));
					buffer.put((byte) (data[2][r][g][b] * 255));
				}
			}
		}
		buffer.flip();
		
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL12.GL_TEXTURE_3D, texture);
		GL11.glTexParameteri(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL12.GL_TEXTURE_3D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL12.GL_TEXTURE_3D, GL12.GL_TEXTURE_WRAP_R, GL11.GL_CLAMP);
		
		GL12.glTexImage3D(GL12.GL_TEXTURE_3D, 0, GL11.GL_RGB8, size, size, size, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
		
		GL11.glBindTexture(GL12.GL_TEXTURE_3D, 0);
		
		lut.texture = texture;
		
		GL11.glDisable(GL12.GL_TEXTURE_3D);
		
		return lut;
		
	}
	
	@Override
	public void setLUT(LUT lut) {
		if (lut == null) {
			GL11.glBindTexture(GL12.GL_TEXTURE_3D, 0);
			GL11.glDisable(GL12.GL_TEXTURE_3D);
		} else {
			GL11.glEnable(GL12.GL_TEXTURE_3D);
			GL11.glBindTexture(GL12.GL_TEXTURE_3D, ((GLLUT) lut).texture);
		}
	}
}

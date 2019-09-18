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
package io.github.tomaso2468.rpgonline.abt;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * An extension of DataInputStream to provide functions and formats needed by
 * ABT. This format is compatible with {@code ABTDataOutputStream}
 * 
 * @author Tomas
 * 
 * @see io.github.tomaso2468.rpgonline.abt.ABTDataOutputStream
 */
public class ABTDataInputStream extends DataInputStream {
	/**
	 * Constructs a new ABTDataInputStream.
	 * 
	 * @param in The input stream to read from.
	 */
	public ABTDataInputStream(InputStream in) {
		super(in);
	}

	/**
	 * Reads the length of a string as a byte then reads that many bytes and treats
	 * them as UTF-8 data.
	 * 
	 * @return A string.
	 * @throws IOException If an error occurs reading data.
	 */
	public String readUTFByte() throws IOException {
		byte len = readByte();

		byte[] data = new byte[len];
		readFully(data);

		return new String(data);
	}

	/**
	 * Reads the length of a string as an int then reads that many bytes and treats
	 * them as UTF-8 data.
	 * 
	 * @return A string.
	 * @throws IOException If an error occurs reading data.
	 */
	public String readUTFInt() throws IOException {
		int len = readInt();

		byte[] data = new byte[len];
		readFully(data);

		return new String(data);
	}

	/**
	 * Reads the length of a string as a long then reads that many bytes and treats
	 * them as UTF-8 data.
	 * 
	 * @return A string.
	 * @throws IOException If an error occurs reading data.
	 */
	public String readUTFLong() throws IOException {
		long len = readLong();

		if (len > Integer.MAX_VALUE - 8) {
			throw new OutOfMemoryError("Requested array size exceeds VM limit");
		}

		byte[] data = new byte[(int) len];
		readFully(data);

		return new String(data);
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(short[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readShort();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(int[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readInt();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(long[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readLong();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(float[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readFloat();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(double[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readDouble();
		}
	}

	/**
	 * Reads all of the data required to fill an array. The strings are treated in
	 * the same way as {@code readUTFInt()}
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(String[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readUTFInt();
		}
	}

	/**
	 * Reads all of the data required to fill an array. The strings are treated in
	 * the same way as {@code readUTF()}
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFullyShort(String[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readUTF();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(char[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readChar();
		}
	}

	/**
	 * Reads all of the data required to fill an array.
	 * 
	 * @param s The array to fill.
	 * @throws IOException If an error occurs reading data.
	 */
	public void readFully(boolean[] s) throws IOException {
		for (int i = 0; i < s.length; i++) {
			s[i] = readBoolean();
		}
	}
}

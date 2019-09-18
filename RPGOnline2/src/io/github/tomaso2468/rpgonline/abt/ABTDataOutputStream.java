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

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

/**
 * An extension of DataOutputStream to provide functions and formats needed by ABT.
 * This format is compatible with {@code ABTDataInputStream}
 * 
 * @author Tomas
 * 
 * @see io.github.tomaso2468.rpgonline.abt.ABTDataInputStream
 */
public class ABTDataOutputStream extends DataOutputStream {
	/**
	 * Constructs a new ABTDataOutputStream
	 * @param out The output stream to write to.
	 */
	public ABTDataOutputStream(OutputStream out) {
		super(out);
	}
	
	/**
	 * Writes the length of a string as a byte then writes that many bytes of the string and treats in the UTF-8 format.
	 * @param s A string.
	 * @throws IOException If an error occurs writing data.
	 */
	public void writeUTFByte(String s) throws IOException {
		if(s.getBytes().length > 255) {
			throw new IOException("String \"" + s + "\" is too big to be written using byte length.");
		}
		writeByte(s.getBytes().length);
		write(s.getBytes());
	}
	
	/**
	 * Writes the length of a string as an int then writes that many bytes of the string and treats in the UTF-8 format.
	 * @param s A string.
	 * @throws IOException If an error occurs writing data.
	 */
	public void writeUTFInt(String s) throws IOException {
		if(s.getBytes().length > 4294967295L) {
			throw new IOException("String \"" + s + "\" is too big to be written using int length.");
		}
		writeInt(s.getBytes().length);
		write(s.getBytes());
	}
	
	/**
	 * Writes the length of a string as a long then writes that many bytes of the string and treats in the UTF-8 format.
	 * @param s A string.
	 * @throws IOException If an error occurs writing data.
	 */
	public void writeUTFLong(String s) throws IOException {
		if(new BigInteger(s.getBytes().length + "").compareTo(new BigInteger("ffffffffffffffff", 16)) > 0) {
			throw new IOException("String \"" + s + "\" is too big to be written using long length.");
		}
		writeLong(s.getBytes().length);
		write(s.getBytes());
	}
	
	/**
	 * Writes the array to a file.
	 * @param data The array to write.
	 * @throws IOException If an error occurs writing data.
	 */
	public void write(short[] data) throws IOException {
		for (short s : data) {
			writeShort(s);
		}
	}
	
	/**
	 * Writes the array to a file.
	 * @param data The array to write.
	 * @throws IOException If an error occurs writing data.
	 */
	public void write(int[] data) throws IOException {
		for (int s : data) {
			writeInt(s);
		}
	}
	
	/**
	 * Writes the array to a file.
	 * @param data The array to write.
	 * @throws IOException If an error occurs writing data.
	 */
	public void write(long[] data) throws IOException {
		for (long s : data) {
			writeLong(s);
		}
	}
	
	/**
	 * Writes the array to a file.
	 * @param data The array to write.
	 * @throws IOException If an error occurs writing data.
	 */
	public void write(float[] data) throws IOException {
		for (float s : data) {
			writeFloat(s);
		}
	}
	
	/**
	 * Writes the array to a file.
	 * @param data The array to write.
	 * @throws IOException If an error occurs writing data.
	 */
	public void write(double[] data) throws IOException {
		for (double s : data) {
			writeDouble(s);
		}
	}
	
	/**
	 * Writes the array to a file.
	 * The strings are written using {@code writeUTFInt()}
	 * @param data The array to write.
	 * @throws IOException If an error occurs writing data.
	 */
	public void write(String[] data) throws IOException {
		for (String s : data) {
			writeUTFInt(s);
		}
	}
	
	/**
	 * Writes the array to a file.
	 * The strings are written using {@code writeUTF()}
	 * @param data The array to write.
	 * @throws IOException If an error occurs writing data.
	 */
	public void writeShort(String[] data) throws IOException {
		for (String s : data) {
			writeUTF(s);
		}
	}
	
	/**
	 * Writes the array to a file.
	 * @param data The array to write.
	 * @throws IOException If an error occurs writing data.
	 */
	public void write(char[] data) throws IOException {
		for (char s : data) {
			writeChar(s);
		}
	}
	
	/**
	 * Writes the array to a file.
	 * @param data The array to write.
	 * @throws IOException If an error occurs writing data.
	 */
	public void write(boolean[] data) throws IOException {
		for (boolean s : data) {
			writeBoolean(s);
		}
	}

}

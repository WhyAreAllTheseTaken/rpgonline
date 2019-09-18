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

/**
 * An array of 128bit integer tags.
 * @author Tomas
 * @deprecated This tag should be made package access.
 */
@Deprecated
public class Tag128Array extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 1678003219817661274L;

	/**
	 * Constructs an array of 128bit tags.
	 * @param name The tag name.
	 * @param data An array of the big-endian byte data for this array.
	 */
	public Tag128Array(String name, byte[][] data) {
		super(name);
		
		setData(data);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getType() {
		return 0x11;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof Tag128)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	/**
	 * Sets the value of this tag.
	 * @param data An array of the big-endian byte data for this array.
	 */
	public void setData(byte[][] data) {
		clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new Tag128(i + "", data[i]));
		}
	}
	
	/**
	 * Gets the value of this tag.
	 * @return An array of the big-endian byte data for this array.
	 */
	public byte[][] getData() {
		byte[][] data = new byte[size()][];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((Tag128) tags.get(i)).getData();
		}
		
		return data;
	}
	
	/**
	 * Sets the value at the specified index to the specified value.
	 * @param index An index.
	 * @param v The big-endian byte data for this number.
	 */
	public void set(int index, byte[] v) {
		((Tag128) getTag(index + "")).setData(v);
	}
	
	/**
	 * Gets the value at the specified index.
	 * @param index The index to get.
	 * @return The big-endian byte data for the number at the index.
	 */
	public byte[] get(int index) {
		return ((Tag128) getTag(index + "")).getData();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag128Array clone() {
		return new Tag128Array(name, getData());
	}
}
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

import java.math.BigDecimal;

/**
 * A tag holding a BigDecimal
 * @author Tomas
 *
 */
@Deprecated
public class TagBigFloat extends TagString {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 7745491369069382304L;

	/**
	 * Constructs a TagBigFloat from a string.
	 * @param name The name of the tag.
	 * @param data The number to hold as a decimal string.
	 */
	public TagBigFloat(String name, String data) {
		super(name, data);
	}
	
	/**
	 * Constructs a TagBigFloat from a BigDecimal.
	 * @param name The name of the tag.
	 * @param data The number to hold as a BigDecimal.
	 */
	public TagBigFloat(String name, BigDecimal v) {
		super(name, v.toString());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getType() {
		return 0x0A;
	}

	/**
	 * Converts this tag to a BigDecimal.
	 * @return A BigDecimal object.
	 */
	public BigDecimal toBigDecimal() {
		return new BigDecimal(getData());
	}
	
	/**
	 * Sets the value of this tag.
	 * @param v A BigDecimal object.
	 */
	public void set(BigDecimal v) {
		setData(v.toString());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagBigFloat clone() {
		return new TagBigFloat(name, getData());
	}
}

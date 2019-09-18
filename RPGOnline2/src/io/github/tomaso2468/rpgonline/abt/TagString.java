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
 * <p>
 * A tag holding a string value with a length measured as an unsigned 64bit
 * integer. For reasons related to different languages and CPU architectures
 * some systems (including this implementation) may only be able to load up to a
 * signed 32bit integer as the length but this does not change how the data is
 * saved.
 * </p>
 * <strong>For performance reasons it may be better to use
 * {@code TagStringShort} as the 16bit size limit is useful for most uses and
 * reduces tag size.</strong>
 * 
 * @author Tomas
 * 
 * @see io.github.tomaso2468.rpgonline.abt.TagStringShort
 */
public class TagString extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 6344197038704127023L;
	/**
	 * The data held in this tag.
	 */
	private String data;

	/**
	 * Constructs a new string tag.
	 * 
	 * @param name The name of this tag.
	 * @param data The value of this tag.
	 */
	public TagString(String name, String data) {
		super(name, 0x0B);
	}

	/**
	 * Gets the data held in this tag.
	 * 
	 * @return A string value.
	 */
	public String getData() {
		return data;
	}

	/**
	 * Sets the data held in this tag.
	 * 
	 * @param data A string value.
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag getTag(String name) {
		Tag t = super.getTag(name);

		if (t == null) {
			try {
				int i = Integer.parseInt(name);
				t = new TagChar(name, data.charAt(i)) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 832728519849455689L;

					@Override
					public void setData(char data) {
						StringBuilder sb = new StringBuilder(TagString.this.data);
						sb.setCharAt(i, data);

						TagString.this.data = sb.toString();
					}

					@Override
					public char getData() {
						return data.charAt(i);
					}
				};
			} catch (NumberFormatException e) {
				return null;
			}
		}

		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{\n");

		appendStr(sb, "name", getName());
		appendSep(sb);
		appendNum(sb, "type", getType());
		appendSep(sb);
		appendStr(sb, "data", "\"" + sanitiseJSON(data) + "\"");

		sb.append("\n}");

		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagString clone() {
		return new TagString(name, getData());
	}
}

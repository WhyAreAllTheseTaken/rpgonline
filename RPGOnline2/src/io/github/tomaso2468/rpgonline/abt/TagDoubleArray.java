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

@Deprecated
public class TagDoubleArray extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 3053649142957912892L;

	public TagDoubleArray(String name, double[] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x14;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagDouble)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(double[] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagDouble(i + "", data[i]));
		}
	}
	
	public double[] getData() {
		double[] data = new double[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagDouble) tags.get(i)).getData();
		}
		
		return data;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{\n");

		appendStr(sb, "name", getName());
		appendSep(sb);
		appendNum(sb, "type", getType());
		appendSep(sb);
		
		String[] data = new String[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagDouble) tags.get(i)).getData() + "";
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	public void set(int index, double v) {
		((TagDouble) getTag(index + "")).setData(v);
	}
	
	public double get(int index) {
		return ((TagDouble) getTag(index + "")).getData();
	}
	
	@Override
	public TagDoubleArray clone() {
		return new TagDoubleArray(name, getData());
	}
}

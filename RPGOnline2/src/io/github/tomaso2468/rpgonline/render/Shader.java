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
package io.github.tomaso2468.rpgonline.render;

import org.newdawn.slick.Color;

public interface Shader {
	public default void setUniform(String name, Color c) {
		setUniform(name, c.r, c.g, c.b, c.a);
	}
	public void setUniform(String name, float value);
	public void setUniform(String name, float v1, float v2);
	public void setUniform(String name, float v1, float v2, float v3);
	public void setUniform(String name, float v1, float v2, float v3, float v4);
	public void setUniform(String name, int value);
	public void setUniform(String name, int v1, int v2);
	public void setUniform(String name, int v1, int v2, int v3);
	public void setUniform(String name, int v1, int v2, int v3, int v4);
	
	public default void setUniformArray(String name, int index, Color c) {
		setUniformArray(name, index, c.r, c.g, c.b, c.a);
	}
	public void setUniformArray(String name, int index, float value);
	public void setUniformArray(String name, int index, float v1, float v2);
	public void setUniformArray(String name, int index, float v1, float v2, float v3);
	public void setUniformArray(String name, int index, float v1, float v2, float v3, float v4);
	public void setUniformArray(String name, int index, int value);
	public void setUniformArray(String name, int index, int v1, int v2);
	public void setUniformArray(String name, int index, int v1, int v2, int v3);
	public void setUniformArray(String name, int index, int v1, int v2, int v3, int v4);
	
	public default void setUniformArrayStruct(String name, int index, String field, Color c) {
		setUniformArrayStruct(name, index, field, c.r, c.g, c.b, c.a);
	}
	public void setUniformArrayStruct(String name, int index, String field, float value);
	public void setUniformArrayStruct(String name, int index, String field, float v1, float v2);
	public void setUniformArrayStruct(String name, int index, String field, float v1, float v2, float v3);
	public void setUniformArrayStruct(String name, int index, String field, float v1, float v2, float v3, float v4);
	public void setUniformArrayStruct(String name, int index, String field, int value);
	public void setUniformArrayStruct(String name, int index, String field, int v1, int v2);
	public void setUniformArrayStruct(String name, int index, String field, int v1, int v2, int v3);
	public void setUniformArrayStruct(String name, int index, String field, int v1, int v2, int v3, int v4);
}

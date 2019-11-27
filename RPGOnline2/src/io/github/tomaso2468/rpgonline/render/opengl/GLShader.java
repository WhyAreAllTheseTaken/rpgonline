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

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL20;

import io.github.tomaso2468.rpgonline.render.Shader;
import io.github.tomaso2468.rpgonline.render.UnknownShaderVariableException;

public class GLShader implements Shader {
	final int program;
	
	private Map<String, Integer> locations = new HashMap<>();
	
	public GLShader(int program) {
		super();
		this.program = program;
	}
	
	private int getLocation(String name) {
		// Use cache if available
		Integer loc = locations.get(name);
		if (loc != null) {
			return loc;
		}
		
		// Get the location using OpenGL
		int location = GL20.glGetUniformLocation(program, name);
		
		if (location == -1) {
			throw new UnknownShaderVariableException(name);
		}
		
		locations.put(name, location);
		
		return location;
	}

	@Override
	public void setUniform(String name, float value) {
		GL20.glUniform1f(getLocation(name), value);
	}

	@Override
	public void setUniform(String name, float v1, float v2) {
		GL20.glUniform2f(getLocation(name), v1, v2);
	}

	@Override
	public void setUniform(String name, float v1, float v2, float v3) {
		GL20.glUniform3f(getLocation(name), v1, v2, v3);
	}

	@Override
	public void setUniform(String name, float v1, float v2, float v3, float v4) {
		GL20.glUniform4f(getLocation(name), v1, v2, v3, v4);
	}

	@Override
	public void setUniform(String name, int value) {
		GL20.glUniform1i(getLocation(name), value);
	}

	@Override
	public void setUniform(String name, int v1, int v2) {
		GL20.glUniform2i(getLocation(name), v1, v2);
	}

	@Override
	public void setUniform(String name, int v1, int v2, int v3) {
		GL20.glUniform3i(getLocation(name), v1, v2, v3);
	}

	@Override
	public void setUniform(String name, int v1, int v2, int v3, int v4) {
		GL20.glUniform4i(getLocation(name), v1, v2, v3, v4);
	}

	@Override
	public void setUniformArray(String name, int index, float value) {
		GL20.glUniform1f(getLocation(name + "[" + index + "]"), value);
	}

	@Override
	public void setUniformArray(String name, int index, float v1, float v2) {
		GL20.glUniform2f(getLocation(name + "[" + index + "]"), v1, v2);
	}

	@Override
	public void setUniformArray(String name, int index, float v1, float v2, float v3) {
		GL20.glUniform3f(getLocation(name + "[" + index + "]"), v1, v2, v3);
	}

	@Override
	public void setUniformArray(String name, int index, float v1, float v2, float v3, float v4) {
		GL20.glUniform4f(getLocation(name + "[" + index + "]"), v1, v2, v3, v4);
	}

	@Override
	public void setUniformArray(String name, int index, int value) {
		GL20.glUniform1i(getLocation(name + "[" + index + "]"), value);
	}

	@Override
	public void setUniformArray(String name, int index, int v1, int v2) {
		GL20.glUniform2i(getLocation(name + "[" + index + "]"), v1, v2);
	}

	@Override
	public void setUniformArray(String name, int index, int v1, int v2, int v3) {
		GL20.glUniform3i(getLocation(name + "[" + index + "]"), v1, v2, v3);
	}

	@Override
	public void setUniformArray(String name, int index, int v1, int v2, int v3, int v4) {
		GL20.glUniform4i(getLocation(name + "[" + index + "]"), v1, v2, v3, v4);
	}

	@Override
	public void setUniformArrayStruct(String name, int index, String field, float value) {
		GL20.glUniform1f(getLocation(name + "[" + index + "]" + "." + field), value);
	}

	@Override
	public void setUniformArrayStruct(String name, int index, String field, float v1, float v2) {
		GL20.glUniform2f(getLocation(name + "[" + index + "]" + "." + field), v1, v2);
	}

	@Override
	public void setUniformArrayStruct(String name, int index, String field, float v1, float v2, float v3) {
		GL20.glUniform3f(getLocation(name + "[" + index + "]" + "." + field), v1, v2, v3);
	}

	@Override
	public void setUniformArrayStruct(String name, int index, String field, float v1, float v2, float v3, float v4) {
		GL20.glUniform4f(getLocation(name + "[" + index + "]" + "." + field), v1, v2, v3, v4);
	}

	@Override
	public void setUniformArrayStruct(String name, int index, String field, int value) {
		GL20.glUniform1i(getLocation(name + "[" + index + "]" + "." + field), value);
	}

	@Override
	public void setUniformArrayStruct(String name, int index, String field, int v1, int v2) {
		GL20.glUniform2i(getLocation(name + "[" + index + "]" + "." + field), v1, v2);
	}

	@Override
	public void setUniformArrayStruct(String name, int index, String field, int v1, int v2, int v3) {
		GL20.glUniform3i(getLocation(name + "[" + index + "]" + "." + field), v1, v2, v3);
	}

	@Override
	public void setUniformArrayStruct(String name, int index, String field, int v1, int v2, int v3, int v4) {
		GL20.glUniform4i(getLocation(name + "[" + index + "]" + "." + field), v1, v2, v3, v4);
	}
	
}

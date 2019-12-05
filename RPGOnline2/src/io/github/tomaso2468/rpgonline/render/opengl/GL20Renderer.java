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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.RenderResourceException;
import io.github.tomaso2468.rpgonline.render.Shader;
import io.github.tomaso2468.rpgonline.render.ShaderCompileException;
import io.github.tomaso2468.rpgonline.render.ShaderLinkException;

public abstract class GL20Renderer extends GL12Renderer {
	@Override
	public void useShader(Shader shader) {
		if (shader == null) {
			GL20.glUseProgram(0);
		} else {
			GL20.glUseProgram(((GLShader) shader).program);
		}
	}

	protected String read(URL url) throws IOException {
		List<Byte> data = new ArrayList<>();
		InputStream in = new BufferedInputStream(url.openStream());

		while (in.available() > 0) {
			data.add((byte) in.read());
		}

		in.close();

		byte[] data2 = new byte[data.size()];
		for (int i = 0; i < data.size(); i++) {
			data2[i] = data.get(i);
		}

		return new String(data2);
	}

	public static final int MAX_LOG_LENGTH = Short.MAX_VALUE;

	protected int compileShader(int type, String src) throws RenderException {
		int shader = GL20.glCreateShader(type);
		GL20.glShaderSource(shader, src);
		GL20.glCompileShader(shader);

		int errorCheck = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
		if (errorCheck != GL11.GL_TRUE) {
			String log = GL20.glGetShaderInfoLog(shader, GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH));
			
			GL20.glDeleteShader(shader);
			throw new ShaderCompileException("Error compiling shaders:\n" + log);
		}
		return shader;
	}

	protected int createProgram(int vertex, int fragment) throws RenderException {
		int program = GL20.glCreateProgram();
		GL20.glAttachShader(program, vertex);
		GL20.glAttachShader(program, fragment);

		GL20.glLinkProgram(program);
		int errorCheck = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS);
		if (errorCheck != GL11.GL_TRUE) {
			String log = GL20.glGetProgramInfoLog(program, MAX_LOG_LENGTH);
			throw new ShaderLinkException("Error linking program:\n" + log);
		}

		GL20.glValidateProgram(program);
		int errorCheck2 = GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS);
		if (errorCheck2 != GL11.GL_TRUE) {
			String log = GL20.glGetProgramInfoLog(program, MAX_LOG_LENGTH);
			throw new ShaderLinkException("Error validating program:\n" + log);
		}

		return program;
	}

	@Override
	public Shader createShader(URL vertex, URL fragment) throws RenderException {
		Log.debug("Compiling shader v=" + vertex.toString() + " f=" + fragment.toString());
		
		String vertexData, fragmentData;
		try {
			vertexData = read(vertex);
		} catch (IOException e) {
			throw new RenderResourceException("Error reading a vertex shader ", e);
		}
		try {
			fragmentData = read(fragment);
		} catch (IOException e) {
			throw new RenderResourceException("Error reading a fragment shader", e);
		}

		int vertexShader = compileShader(GL20.GL_VERTEX_SHADER, vertexData);
		int fragmentShader = compileShader(GL20.GL_FRAGMENT_SHADER, fragmentData);

		int program = createProgram(vertexShader, fragmentShader);

		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);

		return new GLShader(program);
	}

	@Override
	public void deleteShader(Shader shader) throws RenderException {
		GL20.glDeleteShader(((GLShader) shader).program);
	}
}

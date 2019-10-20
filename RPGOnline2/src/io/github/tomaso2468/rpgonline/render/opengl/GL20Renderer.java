package io.github.tomaso2468.rpgonline.render.opengl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import io.github.tomaso2468.rpgonline.RenderException;
import io.github.tomaso2468.rpgonline.render.Shader;

public abstract class GL20Renderer extends GL11Renderer {
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
		
		while(in.available() > 0) {
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
		if (errorCheck != GL11.GL_NO_ERROR) {
			String log = GL20.glGetShaderInfoLog(shader, MAX_LOG_LENGTH);
			throw new RenderException("Error compiling shaders:\n" + log);
		}
		return 0;
	}
	
	protected int createProgram(int vertex, int fragment) throws RenderException {
		int program = GL20.glCreateProgram();
		GL20.glAttachShader(program, vertex);
		GL20.glAttachShader(program, fragment);
		
		GL20.glLinkProgram(program);
		int errorCheck = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS);
		if (errorCheck != GL11.GL_NO_ERROR) {
			String log = GL20.glGetProgramInfoLog(program, MAX_LOG_LENGTH);
			throw new RenderException("Error linking program:\n" + log);
		}
		
		GL20.glValidateProgram(program);
		int errorCheck2 = GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS);
		if (errorCheck2 != GL11.GL_NO_ERROR) {
			String log = GL20.glGetProgramInfoLog(program, MAX_LOG_LENGTH);
			throw new RenderException("Error validating program:\n" + log);
		}
		
		return program;
	}
	
	@Override
	public Shader createShader(URL vertex, URL fragment) throws IOException, RenderException {
		String vertexData, fragmentData;
		try {
			vertexData = read(vertex);
		} catch (IOException e) {
			throw new IOException("Error reading a vertex shader ", e);
		}
		try {
			fragmentData = read(vertex);
		} catch (IOException e) {
			throw new IOException("Error reading a fragment shader", e);
		}
		
		int vertexShader = compileShader(GL20.GL_VERTEX_SHADER, vertexData);
		int fragmentShader = compileShader(GL20.GL_FRAGMENT_SHADER, fragmentData);
		
		int program = createProgram(vertexShader, fragmentShader);
		
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);
		
		return new GLShader(program);
	}
}

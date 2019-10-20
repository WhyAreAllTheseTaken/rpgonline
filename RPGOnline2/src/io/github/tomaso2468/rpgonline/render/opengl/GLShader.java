package io.github.tomaso2468.rpgonline.render.opengl;

import org.lwjgl.opengl.GL20;

import io.github.tomaso2468.rpgonline.render.Shader;

public class GLShader implements Shader {
	final int program;
	
	public GLShader(int program) {
		super();
		this.program = program;
	}

	@Override
	public void setUniform(String name, float value) {
		GL20.glUniform1f(GL20.glGetUniformLocation(program, name), value);
	}

	@Override
	public void setUniform(String name, float v1, float v2) {
		GL20.glUniform2f(GL20.glGetUniformLocation(program, name), v1, v2);
	}

	@Override
	public void setUniform(String name, float v1, float v2, float v3) {
		GL20.glUniform3f(GL20.glGetUniformLocation(program, name), v1, v2, v3);
	}

	@Override
	public void setUniform(String name, float v1, float v2, float v3, float v4) {
		GL20.glUniform4f(GL20.glGetUniformLocation(program, name), v1, v2, v3, v4);
	}

	@Override
	public void setUniform(String name, int value) {
		GL20.glUniform1i(GL20.glGetUniformLocation(program, name), value);
	}

	@Override
	public void setUniform(String name, int v1, int v2) {
		GL20.glUniform2i(GL20.glGetUniformLocation(program, name), v1, v2);
	}

	@Override
	public void setUniform(String name, int v1, int v2, int v3) {
		GL20.glUniform3i(GL20.glGetUniformLocation(program, name), v1, v2, v3);
	}

	@Override
	public void setUniform(String name, int v1, int v2, int v3, int v4) {
		GL20.glUniform4i(GL20.glGetUniformLocation(program, name), v1, v2, v3, v4);
	}
	
}

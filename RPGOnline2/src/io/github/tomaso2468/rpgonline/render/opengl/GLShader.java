package io.github.tomaso2468.rpgonline.render.opengl;

import org.lwjgl.opengl.GL20;

import io.github.tomaso2468.rpgonline.render.Shader;
import io.github.tomaso2468.rpgonline.render.UnknownShaderVariableException;

public class GLShader implements Shader {
	final int program;
	
	public GLShader(int program) {
		super();
		this.program = program;
	}
	
	private int getLocation(String name) {
		int location = GL20.glGetUniformLocation(program, name);
		
		if (location == -1) {
			throw new UnknownShaderVariableException(name);
		}
		
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

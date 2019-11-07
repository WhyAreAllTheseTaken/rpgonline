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

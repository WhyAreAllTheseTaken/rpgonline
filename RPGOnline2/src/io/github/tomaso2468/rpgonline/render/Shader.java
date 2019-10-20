package io.github.tomaso2468.rpgonline.render;

public interface Shader {
	public void setUniform(String name, float value);
	public void setUniform(String name, float v1, float v2);
	public void setUniform(String name, float v1, float v2, float v3);
	public void setUniform(String name, float v1, float v2, float v3, float v4);
	public void setUniform(String name, int value);
	public void setUniform(String name, int v1, int v2);
	public void setUniform(String name, int v1, int v2, int v3);
	public void setUniform(String name, int v1, int v2, int v3, int v4);
}

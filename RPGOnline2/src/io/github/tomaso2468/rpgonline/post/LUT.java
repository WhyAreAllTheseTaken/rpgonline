package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.render.Shader;

public interface LUT {
	public Color apply(Color in);
	public float lookupRed(float r, float g, float b);
	public float lookupGreen(float r, float g, float b);
	public float lookupBlue(float r, float g, float b);
	public void bindToShader(Shader shader, String var);
}

package io.github.tomaso2468.rpgonline.render.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import io.github.tomaso2468.rpgonline.render.BasicLUT;
import io.github.tomaso2468.rpgonline.render.Shader;

public class GLLUT extends BasicLUT {
	int texture;

	public GLLUT(int size, float[][][] data_r, float[][][] data_g, float[][][] data_b) {
		super(size, data_r, data_g, data_b);
	}

	public GLLUT(int size, float[][][][] data) {
		super(size, data);
	}
	
	@Override
	public void bindToShader(Shader shader, String var) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0); 
		shader.setUniform(var, 0);
		GL11.glBindTexture(GL12.GL_TEXTURE_3D, texture);
	}
}

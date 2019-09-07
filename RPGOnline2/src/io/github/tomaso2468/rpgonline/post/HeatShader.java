package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * A shader creating a heat effect.
 * 
 * @author Tomas
 */
public class HeatShader extends ShaderEffect {
	/**
	 * Create the shader
	 */
	public HeatShader() {
		super(HeatShader.class.getResource("/heat.vrt"), HeatShader.class.getResource("/heat.frg"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);

		shader.setUniformFloatVariable("u_time", System.currentTimeMillis() % 100000 / 50f);
		shader.setUniformFloatVariable("stren", 1);
	}
}

package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * A filter that sets all values below a threshold to black.
 * @author Tomas
 */
public class HighPass extends ShaderEffect {
	/**
	 * The threshold for the filter.
	 */
	private final float value;
	
	/**
	 * Constructs a new HighPass effect
	 * @param value The threshold for the filter.
	 */
	public HighPass(float value) {
		super(HighPass.class.getResource("/generic.vrt"), HighPass.class.getResource("/highpass.frg"));
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		shader.setUniformFloatVariable("limit", value);
	}

}

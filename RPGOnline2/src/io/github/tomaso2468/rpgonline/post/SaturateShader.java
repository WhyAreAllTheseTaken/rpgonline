package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * An effect that changes the saturation of the screen.
 * <strong>It may be best to use VibranceEffect instead as it offers better results for improving colours.</strong>
 * @author Tomas
 *
 */
public class SaturateShader extends ShaderEffect {
	/**
	 * The saturation effect factor.
	 * <ul>
	 * <li>0 - Grayscale</li>
	 * <li>0..1 - Desaturated</li>
	 * <li>1 - Normal</li>
	 * <li>1.. - Saturated</li>
	 * <ul>
	 */
	private float sat;
	
	/**
	 * Constructs a new SaturateShader. 
	 * <ul>
	 * <li>0 - Grayscale</li>
	 * <li>0..1 - Desaturated</li>
	 * <li>1 - Normal</li>
	 * <li>1.. - Saturated</li>
	 * <ul>
	 * @param sat The saturation effect factor.
	 */
	public SaturateShader(float sat) {
		super(SaturateShader.class.getResource("/generic.vrt"), SaturateShader.class.getResource("/saturate.frg"));
		this.sat = sat;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		shader.setUniformFloatVariable("saturation", sat);
	}
}

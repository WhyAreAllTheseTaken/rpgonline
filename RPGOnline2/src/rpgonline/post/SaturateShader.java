package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * <strong>It may be best to use VibranceEffect instead as it offers better results for improving colours.</strong>
 * @author Tomas
 *
 */
public class SaturateShader extends ShaderEffect {
	/**
	 * 0 - Grayscale
	 * 0..1 - Desaturated
	 * 1 - Normal
	 * 1.. - Saturated.
	 */
	private float sat;
	
	public SaturateShader(float sat) {
		super(SaturateShader.class.getResource("/generic.vrt"), SaturateShader.class.getResource("/saturate.frg"));
		this.sat = sat;
	}
	
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		shader.setUniformFloatVariable("saturation", sat);
	}
}

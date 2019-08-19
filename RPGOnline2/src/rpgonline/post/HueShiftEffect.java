package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * An effect that changes the hue of an image.
 * @author Tomas
 */
public class HueShiftEffect extends ShaderEffect {
	/**
	 * The change of the hue in degrees.
	 */
	public float shift;
	/**
	 * Constructs a new hue shift effect.
	 * @param shift The change of the hue in degrees.
	 */
	public HueShiftEffect(float shift) {
		super(HueShiftEffect.class.getResource("/hueshift.frg"));
		this.shift = shift;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		
		shader.setUniformFloatVariable("hueShift", shift);
	}

}

package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * An effect that brightens the screen.
 * @author Tomas
 *
 */
public class BrightnessEffect extends ShaderEffect {
	/**
	 * The value to brighten the screen by. (0 = normal)
	 */
	public float brightness;
	/**
	 * Constructs a new brightness effect.
	 * @param brightness The value to brighten the screen by. (0 = normal)
	 */
	public BrightnessEffect(float brightness) {
		super(BrightnessEffect.class.getResource("/brightness.frg"));
		this.brightness = brightness;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		
		shader.setUniformFloatVariable("brightness", brightness);
	}

}

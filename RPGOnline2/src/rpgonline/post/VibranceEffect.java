package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * An effect that changes the vibrance of the screen. This is different to saturation as it handles skin tones better than saturation.
 * @author Tomas
 */
public class VibranceEffect extends ShaderEffect {
	/**
	 * The factor to change the vibrance of the image by.
	 */
	public float vibrance;
	/**
	 * Constructs a new vibrance effect.
	 * @param vibrance The factor to change the vibrance of the image by.
	 */
	public VibranceEffect(float vibrance) {
		super(VibranceEffect.class.getResource("/vibrance.frg"));
		this.vibrance = vibrance;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		
		shader.setUniformFloatVariable("_vibrance", vibrance);
	}

}

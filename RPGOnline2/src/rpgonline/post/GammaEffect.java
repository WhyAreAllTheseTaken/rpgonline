package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * An effect that modifies the gamma of the screen to convert from linear to SRGB values.
 * @author Tomas
 */
public class GammaEffect extends ShaderEffect {
	/**
	 * The screen's gamma value.
	 */
	public float gamma;
	
	/**
	 * Constructs a new gamma effect.
	 * @param gamma The gamma of the screen.
	 */
	public GammaEffect(float gamma) {
		super(GammaEffect.class.getResource("/gamma.frg"));
		this.gamma = gamma;
	}
	
	/**
	 * Constructs a new gamma effect with a gamma of 2.2.
	 */
	public GammaEffect() {
		this(2.2f);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		
		shader.setUniformFloatVariable("gamma", gamma);
	}

}

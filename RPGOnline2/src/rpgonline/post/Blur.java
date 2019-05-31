package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * A blur effect.
 * 
 * @author Tomas
 */
public class Blur extends ShaderEffect {

	/**
	 * Creates a blur effect.
	 */
	public Blur() {
		super(Blur.class.getResource("/blur.vrt"), Blur.class.getResource("/blur.frg"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);

		shader.setUniformFloatVariable("ss", 0.75f / 1366f);
	}
}

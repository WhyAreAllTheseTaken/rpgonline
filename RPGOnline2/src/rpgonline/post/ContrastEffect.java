package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * An effect that modifies the contrast of the screen.
 * @author Tomas
 */
public class ContrastEffect extends ShaderEffect {
	/**
	 * The contrast value to adjust by.
	 * <ul>
	 * <li><1 - Less contrast</li>
	 * <li>0 - No change in contrast</li>
	 * <li>>1 - More contrast</li>
	 * </ul>
	 */
	public float contrast;
	/**
	 * <p>Constructs a new ContrastEffect shader.</p>
	 * <p>The contrast value is set as follows:
	 * <ul>
	 * <li><1 - Less contrast</li>
	 * <li>0 - No change in contrast</li>
	 * <li>>1 - More contrast</li>
	 * </ul>
	 * </p>
	 * @param contrast The contrast value to adjust by.
	 */
	public ContrastEffect(float contrast) {
		super(ContrastEffect.class.getResource("/contrast.frg"));
		this.contrast = contrast;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		
		shader.setUniformFloatVariable("contrast", contrast);
	}

}

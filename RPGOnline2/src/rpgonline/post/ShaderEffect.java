package rpgonline.post;

import java.net.URL;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import slickshader.Shader;

/**
 * An effect based on a shader.
 * 
 * @author Tomas
 */
public class ShaderEffect implements PostEffect {
	/**
	 * The vertex shader.
	 */
	private final URL vertex;
	/**
	 * The fragment shader.
	 */
	private final URL fragment;
	/**
	 * The shader.
	 */
	protected Shader shader;

	/**
	 * Creates a shader effect with a basic vertex shader that returns the inputed coordinates.
	 * 
	 * @param fragment The fragment shader.
	 */
	public ShaderEffect(URL fragment) {
		super();
		this.vertex = ShaderEffect.class.getResource("/generic.vrt");
		this.fragment = fragment;
	}
	
	/**
	 * Creates a shader effect.
	 * 
	 * @param vertex   The vertex shader.
	 * @param fragment The fragment shader.
	 */
	public ShaderEffect(URL vertex, URL fragment) {
		super();
		this.vertex = vertex;
		this.fragment = fragment;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		g.clear();

		if (shader == null) {
			shader = Shader.makeShader(vertex, fragment);
			initShader(shader, container);
		}

		shader.startShader();

		updateShader(shader, container);

		g.drawImage(buffer, 0, 0);

		Shader.forceFixedShader(); // Return to the default fixed pipeline shader
	}

	/**
	 * Updates any shader uniforms
	 * 
	 * @param shader The shader to update.
	 * @param c The game container.
	 */
	protected void updateShader(Shader shader, GameContainer c) {

	}
	
	/**
	 * Sets up the shader.
	 * @param shader The shader to set up.
	 * @param c The game container.
	 */
	protected void initShader(Shader shader, GameContainer c) {
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		if (shader != null) {
			shader.deleteShader();
		}
	}
}

package rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import slickshader.Shader;

/**
 * <p>A 2 pass gaussian blur effect.</p>
 * 
 * @author Tomas
 */
public class Blur implements PostEffect {
	/**
	 * Vertical shader.
	 */
	private Shader shader;
	/**
	 * Horizontal shader.
	 */
	private Shader shader2;
	/**
	 * Size of the blur.
	 */
	public int size;
	/**
	 * Deviation of the blur.
	 */
	public float sigma;
	
	/**
	 * Constructs a new blur effect.
	 * @param size The size of the blur.
	 * @param sigma The deviation of the blur.
	 */
	public Blur(int size, float sigma) {
		super();
		this.size = size;
		this.sigma = sigma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		buffer.setFilter(Image.FILTER_LINEAR);
		
		//XXX Two files needed because uniforms wouldn't change.
		if (shader == null) {
			shader = Shader.makeShader(Blur.class.getResource("/generic.vrt"), Blur.class.getResource("/blurV.frg"));
		}
		if (shader2 == null) {
			shader2 = Shader.makeShader(Blur.class.getResource("/generic.vrt"), Blur.class.getResource("/blurH.frg"));
		}
		
		shader.startShader();
		shader.setUniformIntVariable("blurSize", size);
		shader.setUniformFloatVariable("sigma", sigma);
		
		// vertical pass
		g.drawImage(buffer, 0, 0);
		
		//TODO Is this needed?
		Shader.forceFixedShader();
		
		buffer.flushPixelData();
		g.copyArea(buffer, 0, 0);
		
		shader2.startShader();
		
		shader2.setUniformIntVariable("blurSize", size);
		shader2.setUniformFloatVariable("sigma", sigma);
		
		// horizontal pass
		g.drawImage(buffer, 0, 0);
		Shader.forceFixedShader();
	}
}

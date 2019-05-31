package rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import slickshader.Shader;

/**
 * Improves the quality of edges. This class basically acts as a quick
 * antialias.
 * 
 * @author Tomas
 */
public class EdgeResample extends ShaderEffect {
	private float ss;

	public EdgeResample() {
		super(EdgeResample.class.getResource("/esaa.vrt"), EdgeResample.class.getResource("/esaa.frg"));
	}

	@Override
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException {
		ss = 0.27f / container.getHeight();
		super.doPostProcess(container, game, buffer, g);
	}

	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);

		shader.setUniformFloatVariable("ss", ss);
	}

}

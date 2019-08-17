package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

public class GammaEffect extends ShaderEffect {
	public float gamma;
	
	public GammaEffect(float gamma) {
		super(GammaEffect.class.getResource("/gamma.frg"));
		this.gamma = gamma;
	}
	
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		
		shader.setUniformFloatVariable("gamma", gamma);
	}

}

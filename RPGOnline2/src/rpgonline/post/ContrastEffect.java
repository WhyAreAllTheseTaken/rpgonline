package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

public class ContrastEffect extends ShaderEffect {
	public float contrast;
	public ContrastEffect(float contrast) {
		super(ContrastEffect.class.getResource("/contrast.frg"));
		this.contrast = contrast;
	}

	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		
		shader.setUniformFloatVariable("contrast", contrast);
	}

}

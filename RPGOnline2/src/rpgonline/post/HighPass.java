package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

public class HighPass extends ShaderEffect {
	private final float value;
	
	public HighPass(float value) {
		super(HighPass.class.getResource("/generic.vrt"), HighPass.class.getResource("/highpass.frg"));
		this.value = value;
	}
	
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		shader.setUniformFloatVariable("limit", value);
	}

}

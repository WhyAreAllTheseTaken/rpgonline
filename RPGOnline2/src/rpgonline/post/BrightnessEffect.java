package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

public class BrightnessEffect extends ShaderEffect {
	public float brightness;
	public BrightnessEffect(float brightness) {
		super(BrightnessEffect.class.getResource("/brightness.frg"));
		this.brightness = brightness;
	}
	
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		
		shader.setUniformFloatVariable("brightness", brightness);
	}

}

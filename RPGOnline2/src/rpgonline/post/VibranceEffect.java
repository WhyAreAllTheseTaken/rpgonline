package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

public class VibranceEffect extends ShaderEffect {
	public float vibrance;
	public VibranceEffect(float vibrance) {
		super(VibranceEffect.class.getResource("/vibrance.frg"));
		this.vibrance = vibrance;
	}
	
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		
		shader.setUniformFloatVariable("_vibrance", vibrance);
	}

}

package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

public class SaturateShader extends ShaderEffect {
	private float sat;
	
	public SaturateShader(float sat) {
		super(SaturateShader.class.getResource("/generic.vrt"), SaturateShader.class.getResource("/saturate.frg"));
		this.sat = sat;
	}
	
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		shader.setUniformFloatVariable("saturation", sat);
	}
}

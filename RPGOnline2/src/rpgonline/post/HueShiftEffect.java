package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

public class HueShiftEffect extends ShaderEffect {
	public float shift;
	
	public HueShiftEffect(float shift) {
		super(HueShiftEffect.class.getResource("/hueshift.frg"));
		this.shift = shift;
	}
	
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		super.updateShader(shader, c);
		
		shader.setUniformFloatVariable("hueShift", shift);
	}

}

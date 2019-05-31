package rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

public class FXAA extends ShaderEffect {
	public FXAA() {
		super(FXAA.class.getResource("/generic.vrt"), FXAA.class.getResource("/fxaa.frg"));
	}
	
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		shader.setUniformFloatVariable("u_texelStep", 1f / c.getWidth(), 1f / c.getHeight());
		shader.setUniformIntVariable("u_showEdges", 0);
		shader.setUniformIntVariable("u_fxaaOn", 1);
		shader.setUniformFloatVariable("u_lumaThreshold", 0.5f);
		shader.setUniformFloatVariable("u_mulReduce", 1 / 8f);
		shader.setUniformFloatVariable("u_minReduce", 1 / 128f);
		shader.setUniformFloatVariable("u_maxSpan", 8f);
	}
}

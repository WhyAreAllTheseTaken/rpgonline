package io.github.tomaso2468.rpgonline.post;

import io.github.tomaso2468.rpgonline.render.Shader;

public class HDRPost extends ShaderPost {
	private float exposure = 1f;
	private float gamma = 1.2f;
	
	public HDRPost() {
		super(HDRPost.class.getResource("/generic.vrt"), HDRPost.class.getResource("/hdr.frg"));
	}
	
	@Override
	public void updateShader(Shader shader) {
		super.updateShader(shader);
		shader.setUniform("exposure", exposure);
		shader.setUniform("gamma", gamma);
	}

	public float getExposure() {
		return exposure;
	}

	public void setExposure(float exposure) {
		this.exposure = exposure;
	}

	public float getGamma() {
		return gamma;
	}

	public void setGamma(float gamma) {
		this.gamma = gamma;
	}
}

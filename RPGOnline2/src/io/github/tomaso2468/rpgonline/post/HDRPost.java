package io.github.tomaso2468.rpgonline.post;

import io.github.tomaso2468.rpgonline.render.Shader;

public class HDRPost extends ShaderPost {
	private float exposure_last = Float.NaN;
	private float exposure = 1f;
	private float gamma_last = Float.NaN;
	private float gamma = 1.2f;
	private float pre_map_curve_last = Float.NaN;
	private float pre_map_curve = 1.5f;
	private float post_map_mul_last = Float.NaN;
	private float post_map_mul = 1.4f;
	
	public HDRPost() {
		super(HDRPost.class.getResource("/generic.vrt"), HDRPost.class.getResource("/hdr.frg"));
	}
	
	@Override
	public void updateShader(Shader shader) {
		super.updateShader(shader);
		if (exposure != exposure_last) {
			shader.setUniform("exposure", exposure);
			this.exposure_last = exposure;
		}
		if (gamma != gamma_last) {
			shader.setUniform("gamma", gamma);
			this.gamma_last = gamma;
		}
		if (pre_map_curve != pre_map_curve_last) {
			shader.setUniform("pre_map_curve", pre_map_curve);
			this.pre_map_curve_last = pre_map_curve;
		}
		if (post_map_mul != post_map_mul_last) {
			shader.setUniform("post_map_multiply", post_map_mul);
			this.pre_map_curve_last = post_map_mul;
		}
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

	public float getPreMapCurve() {
		return pre_map_curve;
	}

	public void setPreMapCurve(float pre_map) {
		this.pre_map_curve = pre_map;
	}
}

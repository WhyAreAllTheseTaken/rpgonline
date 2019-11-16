package io.github.tomaso2468.rpgonline.post;

import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.render.Shader;

public class HDRPost extends ShaderPost {
	private float exposure_last = Float.NaN;
	private float exposure = 1f;
	private float gamma_last = Float.NaN;
	private float gamma = 1.2f;
	
	private float curve_angle_last = Float.NaN;
	private float curve_angle = 1f;
	private float curve_exponent_last = Float.NaN;
	private float curve_exponent = -1f;
	private float contrast_last = Float.NaN;
	private float contrast = 1.4f;
	private float white_last = Float.NaN;
	private float white = 1f;
	
	public HDRPost() {
		super(HDRPost.class.getResource("/generic.vrt"), HDRPost.class.getResource("/hdr.frg"));
	}
	
	@Override
	public void updateShader(Shader shader, Renderer renderer) throws RenderException {
		super.updateShader(shader, renderer);
		if (exposure != exposure_last) {
			shader.setUniform("exposure", exposure);
			this.exposure_last = exposure;
		}
		if (gamma != gamma_last) {
			shader.setUniform("gamma", gamma);
			this.gamma_last = gamma;
		}
		
		if (curve_angle != curve_angle_last) {
			shader.setUniform("curve_angle", curve_angle);
			this.curve_angle_last = curve_angle;
		}
		if (curve_exponent != curve_exponent_last) {
			shader.setUniform("curve_exponent", curve_exponent);
			this.curve_exponent_last = curve_exponent;
		}
		if (contrast != contrast_last) {
			shader.setUniform("contrast", contrast);
			this.contrast_last = contrast;
		}
		if (white != white_last) {
			shader.setUniform("white_point", white);
			this.white_last = white;
		}
		shader.setUniform("screenScale", 1, (float) renderer.getWidth() / renderer.getHeight());
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

	public float getCurveAngle() {
		return curve_angle;
	}

	public void setCurveAngle(float curve_angle) {
		this.curve_angle = curve_angle;
	}

	public float getCurveExponent() {
		return curve_exponent;
	}

	public void setCurveExponent(float curve_exponent) {
		this.curve_exponent = curve_exponent;
	}

	public float getContrast() {
		return contrast;
	}

	public void setContrast(float contrast) {
		this.contrast = contrast;
	}

	public float getWhite() {
		return white;
	}

	public void setWhite(float white) {
		this.white = white;
	}
}

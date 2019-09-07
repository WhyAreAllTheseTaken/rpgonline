package io.github.tomaso2468.rpgonline.post;

import org.newdawn.slick.GameContainer;

import slickshader.Shader;

/**
 * A shader that effects the colours on the screen.
 * 
 * @author Tomas
 *
 * @see io.github.tomaso2468.rpgonline.post.SaturateShader
 * @see io.github.tomaso2468.rpgonline.post.BrightnessEffect
 * @see io.github.tomaso2468.rpgonline.post.ContrastEffect
 * @see io.github.tomaso2468.rpgonline.post.VibranceEffect
 * @see io.github.tomaso2468.rpgonline.post.HueShiftEffect
 * @see io.github.tomaso2468.rpgonline.post.GammaEffect
 */
public class ColorEffectsShader extends ShaderEffect {
	/**
	 * A value that increases the saturation of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.SaturateShader
	 */
	public float saturation;
	/**
	 * A value that adjusts the brightness of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.BrightnessEffect
	 */
	public float brightness;
	/**
	 * A value that changes the contrast of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.ContrastEffect
	 */
	public float contrast;
	/**
	 * A value that changes the vibrance of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.VibranceEffect
	 */
	public float vibrance;
	/**
	 * A value that changes the hue of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.HueShiftEffect
	 */
	public float hue;
	/**
	 * A value that changes the gamma of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.GammaEffect
	 */
	public float gamma;

	/**
	 * Constructs a new ColorEffectsShader.
	 * 
	 * @param saturation A value that increases the saturation of the image.
	 * @param brightness A value that adjusts the brightness of the image.
	 * @param contrast A value that changes the contrast of the image.
	 * @param vibrance A value that changes the vibrance of the image.
	 * @param hue A value that changes the hue of the image.
	 * @param gamma A value that changes the gamma of the image.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.SaturateShader
	 * @see io.github.tomaso2468.rpgonline.post.BrightnessEffect
	 * @see io.github.tomaso2468.rpgonline.post.ContrastEffect
	 * @see io.github.tomaso2468.rpgonline.post.VibranceEffect
	 * @see io.github.tomaso2468.rpgonline.post.HueShiftEffect
	 * @see io.github.tomaso2468.rpgonline.post.GammaEffect
	 */
	public ColorEffectsShader(float saturation, float brightness, float contrast, float vibrance, float hue,
			float gamma) {
		super(ColorEffectsShader.class.getResource("/generic.vrt"),
				ColorEffectsShader.class.getResource("/colorpost.frg"));
		this.saturation = saturation;
		this.brightness = brightness;
		this.contrast = contrast;
		this.vibrance = vibrance;
		this.hue = hue;
		this.gamma = gamma;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateShader(Shader shader, GameContainer c) {
		shader.setUniformFloatVariable("saturation", saturation);
		shader.setUniformFloatVariable("brightness", brightness);
		shader.setUniformFloatVariable("contrast", contrast);
		shader.setUniformFloatVariable("_vibrance", vibrance);
		shader.setUniformFloatVariable("hueShift", hue);
		shader.setUniformFloatVariable("gamma", gamma);
	}
}

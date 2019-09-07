package io.github.tomaso2468.rpgonline.post;

/**
 * A film grain effect.
 * 
 * @author Tomas
 */
@Deprecated
public class NoiseEffect extends ShaderEffect {
	/**
	 * Create a {@code NoiseEffect} effect.
	 */
	public NoiseEffect() {
		super(NoiseEffect.class.getResource("/expose.vrt"), NoiseEffect.class.getResource("/noise.frg"));
	}
}

package rpgonline.post;

/**
 * A film grain effect.
 * 
 * @author Tomas
 */
public class NoiseEffect extends ShaderEffect {
	/**
	 * Create a {@code NoiseEffect} effect.
	 */
	public NoiseEffect() {
		super(NoiseEffect.class.getResource("/expose.vrt"), NoiseEffect.class.getResource("/noise.frg"));
	}
}

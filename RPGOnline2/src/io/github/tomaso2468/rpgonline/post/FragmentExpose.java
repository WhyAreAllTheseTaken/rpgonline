package io.github.tomaso2468.rpgonline.post;

/**
 * Provides over exposure effect
 * 
 * @author Tomas
 * @see io.github.tomaso2468.rpgonline.post.LowExpose
 */
public class FragmentExpose extends ShaderEffect {
	/**
	 * Creates a {@code FragmentExpose} effect.
	 */
	public FragmentExpose() {
		super(HeatShader.class.getResource("/expose.vrt"), HeatShader.class.getResource("/expose.frg"));
	}
}

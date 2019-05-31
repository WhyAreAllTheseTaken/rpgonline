package rpgonline.post;

/**
 * A low quality but faster over exposure effect.
 * 
 * @author Tomas
 * @see rpgonline.post.FragmentExpose
 */
public class LowExpose extends ShaderEffect {
	/**
	 * Create a {@code LowExpose} effect.
	 */
	public LowExpose() {
		super(HeatShader.class.getResource("/expose.vrt"), HeatShader.class.getResource("/lowexpose.frg"));
	}
}

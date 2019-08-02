package rpgonline.post.pack;

import rpgonline.post.ColorBoostEffect;
import rpgonline.post.DynamicHeatShader2;
import rpgonline.post.MultiEffect;

/**
 * A quality shader pack for good devices.
 * 
 * @author Tomas
 */
@Deprecated
public class HighShaderPack extends MultiEffect {
	/**
	 * Create the shader pack.
	 */
	public HighShaderPack() {
		super(new DynamicHeatShader2(), new ColorBoostEffect());
	}
}

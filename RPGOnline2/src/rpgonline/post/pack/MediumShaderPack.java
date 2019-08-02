package rpgonline.post.pack;

import rpgonline.post.ColorBoostEffect;
import rpgonline.post.LowExpose;
import rpgonline.post.MultiEffect;

/**
 * A medium quality shader pack for average devices.
 * 
 * @author Tomas
 */
@Deprecated
public class MediumShaderPack extends MultiEffect {
	/**
	 * Create the shader pack.
	 */
	public MediumShaderPack() {
		super(new ColorBoostEffect(), new LowExpose());
	}
}

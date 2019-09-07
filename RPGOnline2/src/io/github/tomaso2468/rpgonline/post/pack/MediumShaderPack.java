package io.github.tomaso2468.rpgonline.post.pack;

import io.github.tomaso2468.rpgonline.post.ColorBoostEffect;
import io.github.tomaso2468.rpgonline.post.LowExpose;
import io.github.tomaso2468.rpgonline.post.MultiEffect;

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

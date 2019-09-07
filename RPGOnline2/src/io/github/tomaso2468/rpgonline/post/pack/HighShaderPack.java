package io.github.tomaso2468.rpgonline.post.pack;

import io.github.tomaso2468.rpgonline.post.ColorBoostEffect;
import io.github.tomaso2468.rpgonline.post.DynamicHeatShader2;
import io.github.tomaso2468.rpgonline.post.MultiEffect;

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

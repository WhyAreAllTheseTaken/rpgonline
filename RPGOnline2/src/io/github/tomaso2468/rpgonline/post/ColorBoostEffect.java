package io.github.tomaso2468.rpgonline.post;

/**
 * An effect that boosts the appearance of colours.
 * 
 * @author Tomas
 */
@Deprecated
public class ColorBoostEffect extends ShaderEffect {

	/**
	 * Creates a colour boost effect.
	 */
	public ColorBoostEffect() {
		super(EdgeResample.class.getResource("/colorboost.vrt"), EdgeResample.class.getResource("/colorboost.frg"));
	}

}

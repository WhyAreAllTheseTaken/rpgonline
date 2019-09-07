package io.github.tomaso2468.rpgonline.post;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.geom.Transform;

/**
 * An effect that rotates the screen.
 * @author Tomas
 */
public class RotateEffect extends TransformEffect {

	/**
	 * Constructs a new RotateEffect.
	 * @param a The angle to rotate by in degrees.
	 * @param x The X centre of rotation in pixels.
	 * @param y The Y centre of rotation in pixels.
	 */
	public RotateEffect(float a, float x, float y) {
		super(Transform.createRotateTransform((float) FastMath.toRadians(a), x, y));
	}

}

package rpgonline.post;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.geom.Transform;

public class RotateEffect extends TransformEffect {

	public RotateEffect(float a, float x, float y) {
		super(Transform.createRotateTransform((float) FastMath.toRadians(a), x, y));
	}

}

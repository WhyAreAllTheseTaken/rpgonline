package rpgonline.atmosphere;

import org.newdawn.slick.Color;

public interface Scatterer {
	public default Color scatter(Color c, float v) {
		float bv = v + 1;
		float gv = v / 4 + 1;
		float rv = v / 20 + 1;
		
		return new Color(c.r / rv, c.g / gv, c.b / bv);
	}
}

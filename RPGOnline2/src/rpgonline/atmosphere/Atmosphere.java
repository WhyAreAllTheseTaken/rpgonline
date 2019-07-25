package rpgonline.atmosphere;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.Color;

import rpgonline.ColorUtils;

@Deprecated
public class Atmosphere {
	private final Map<Scatterer, Float> gases = new HashMap<>();
	private float density = 0;

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public void addGas(Scatterer gas, float percent) {
		gases.put(gas, percent);
	}
	
	public void addGas(Scatterer gas, double percent) {
		gases.put(gas, (float) percent);
	}
	
	public Color scatter(Color c, float angle) {
		c = ColorUtils.scatter(c, density * angle);
		
		float r = 0;
		float g = 0;
		float b = 0;
		
		float total = 0;
		
		for (Entry<Scatterer, Float> gas : gases.entrySet()) {
			if (gas.getValue() > 0.01f) {
				Color gc = gas.getKey().scatter(c, gas.getValue());
				r += gc.r * gas.getValue();
				g += gc.g * gas.getValue();
				b += gc.b * gas.getValue();
				total += gas.getValue();
			}
		}
		
		r += c.r * (1 - total);
		g += c.g * (1 - total);
		b += c.b * (1 - total);
		
		return new Color(r, g, b);
	}
	
	public Color inverseScatter(Color c, float angle) {
		Color sc = scatter(c, angle);
		
		return new Color(c.r - sc.r, c.g - sc.g, c.b - sc.b);
	}
	
	public Color skyScatter(Color c, float angle) {
		return scatter(inverseScatter(c, angle), angle / 2f);
	}
}

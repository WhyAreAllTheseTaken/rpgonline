package rpgonline.atmosphere;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.Color;

@Deprecated
public class AtmosphereGas extends AtmosphereParticle {
	public static final AtmosphereGas OXYGEN = new AtmosphereGas(158f / 255f, 162f / 255f, 207f / 255f, 0.01f);
	public static final AtmosphereGas METHANE = new AtmosphereGas(208f / 255f, 135f / 255f, 77f / 255f, 0.7f);
	public static final AtmosphereGas OZONE = new AtmosphereGas(177f / 255f, 129f / 255f, 61f / 255f, 0.6f);
	public static final AtmosphereGas NITROGEN_OXIDE = new AtmosphereGas(133f / 255f, 86f / 255f, 43f / 255f, 0.5f);
	public static final AtmosphereGas SULPHUR = new AtmosphereGas(0, 0, 0, 0.8f) {
		float[] low = {
				194f / 255f,
				53f / 255f,
				4f / 255f,
		};
		float[] high = {
				213f / 255f,
				204f / 255f,
				2f / 255f,
		};
		float[] vhigh = {
				201f / 255f,
				213f / 255f,
				2f / 255f,
		};
		@Override
		public Color scatter(Color c, float v) {
			float r, g, b;
			float temp = (770 + 1120 / 2);
			if (temp < 770) {
				r = interp(0, low[0], temp / 770);
				g = interp(0, low[1], temp / 770);
				b = interp(0, low[2], temp / 770);
			} else if (temp < 1120) {
				r = interp(low[0], high[0], (temp - 770) / 350);
				g = interp(low[1], high[1], (temp - 770) / 350);
				b = interp(low[2], high[2], (temp - 770) / 350);
			} else {
				r = interp(high[0], vhigh[0], signum(temp - 1120));
				g = interp(high[1], vhigh[1], signum(temp - 1120));
				b = interp(high[2], vhigh[2], signum(temp - 1120));
			}
			return new Color(r, g, b);
		}
		private float interp(float a, float b, float v) {
			return a * (1 - v) + b * v;
		}
		private float signum(float v) {
			return (float) (FastMath.tanh(v/100)/1.55);
		}
	};
	public static final AtmosphereGas SAND = new AtmosphereGas(102f / 255f, 60f / 255f, 16f / 255f, 0.95f);
	public static final AtmosphereGas IRON_OXIDE = new AtmosphereGas(1, 0.8f, 0.1f, 0.9f);
	public static final AtmosphereGas WATER_VAPOUR = new AtmosphereGas(0.98f, 0.99f, 1, 0.95f);
	public static final AtmosphereGas CHLORINE = new AtmosphereGas(0.8f, 0.95f, 0f, 0.9f);
	public static final AtmosphereGas FLUROINE = new AtmosphereGas(0.7f, 0.7f, 0f, 0.8f);
	public static final Scatterer RAIN = new Scatterer() {
		@Override
		public Color scatter(Color c, float v) {
			float bv = v / 20 + 1;
			float gv = v / 10 + 1;
			float rv = v / 5 + 1;
			
			return new Color(c.r / rv, c.g / gv, c.b / bv);
		}
	};
	public static final Scatterer ICE = new Scatterer() {
		@Override
		public Color scatter(Color c, float v) {
			float bv = v / 50 + 1;
			float gv = v / 25 + 1;
			float rv = v / 2 + 1;
			
			return new Color(c.r / rv, c.g / gv, c.b / bv);
		}
	};
	
	public AtmosphereGas(float r, float g, float b, float size) {
		super(r, g, b, size);
	}
}

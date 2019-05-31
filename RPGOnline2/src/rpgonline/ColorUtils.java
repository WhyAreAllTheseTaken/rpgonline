package rpgonline;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.Color;
import org.newdawn.slick.util.Log;

/**
 * A class with utilities for manipulating colour.
 * 
 * @author Tomas
 */
public class ColorUtils {
	/**
	 * Converts a temperature in kelvin to the colour output (RGB) of a black body
	 * at the temperature.
	 * 
	 * @param kelvin A float greater than 0
	 * @return a {@code Color} object.
	 */
	public static Color kelvinToColor(float kelvin) {
		float temp = kelvin / 100;

		float r, g, b;

		if (temp <= 66) {
			r = 255;
		} else {
			r = temp - 60;
			r = (float) (329.698727446 * FastMath.pow(r, -0.1332047592));
		}
		if (r < 0) {
			r = 0;
		}
		if (r > 255) {
			r = 255;
		}

		if (temp <= 66) {
			g = temp;
			g = (float) (99.4708025861 * FastMath.log(g) - 161.1195681661);
		} else {
			g = temp - 60;
			g = (float) (288.1221695283 * FastMath.pow(g, -0.0755148492));
		}
		if (g < 0) {
			g = 0;
		}
		if (g > 255) {
			g = 255;
		}

		if (temp >= 66) {
			b = 255;
		} else {
			b = temp - 10;
			b = (float) (138.5177312231 * FastMath.log(b) - 305.0447927307);
		}
		if (b < 0) {
			b = 0;
		}
		if (b > 255) {
			b = 255;
		}

		r = r / 255f;
		g = g / 255f;
		b = b / 255f;

		if (kelvin < 440) {
			r /= FastMath.sqrt(FastMath.sqrt(441 - kelvin));
			g /= FastMath.sqrt(FastMath.sqrt(441 - kelvin));
			b /= FastMath.sqrt(FastMath.sqrt(441 - kelvin));
		}

		if (r != r) {
			r = 0;
		}
		if (g != g) {
			g = 0;
		}
		if (b != b) {
			b = 0;
		}

		return new Color(r, g, b, 1);
	}

	/**
	 * A method for testing
	 * 
	 * @param args Program arguments
	 */
	public static void main(String[] args) {
		Log.info("900 -> " + kelvinToColor(900));
	}
}

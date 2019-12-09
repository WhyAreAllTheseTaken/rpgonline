/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
/*
 * Some algrotihms by Ruo Feidu and Tanner Hellend
 */
package io.github.tomaso2468.rpgonline;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.util.FastMath;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.debug.Debugger;

/**
 * A class with utilities for manipulating colors.
 * 
 * Some of this code is adapted from
 * {@link http://blog.ruofeidu.com/postprocessing-brightness-contrast-hue-saturation-vibrance/}
 * and is designed to behave in a similar way to the ColorEffectsShader.
 * 
 * @see io.github.tomaso2468.rpgonline.post.ColorEffectsShader
 * 
 * @author Tomaso2468, Ruo Feidu, Tanner Hellend
 */
public final class ColorUtils {
	/**
	 * Prevent instantiation.
	 */
	private ColorUtils() {

	}

	/**
	 * Converts a temperature in kelvin to the colour output (RGB) of a black body
	 * at the specified temperature.
	 * 
	 * This is an expanded algorithm of
	 * {@link http://www.tannerhelland.com/4435/convert-temperature-rgb-algorithm-code/}.
	 * 
	 * @param kelvin A float greater than 0
	 * @return a {@code Color} object.
	 */
	public static Color kelvinToColor(float kelvin) {
		if (kelvin < 0) {
			throw new IllegalArgumentException("A temperature cannot be less than 0K");
		}

		Debugger.start("kelvin-compute");
		float temp = kelvin / 100;

		double r, g, b;

		if (temp <= 66) {
			r = 255;
		} else {
			r = temp - 60;
			r = 329.698727446 * FastMath.pow(r, -0.1332047592);
		}
		if (r < 0) {
			r = 0;
		}
		if (r > 255) {
			r = 255;
		}

		if (temp <= 66) {
			g = temp;
			g = 99.4708025861 * FastMath.log(g) - 161.1195681661;
		} else {
			g = temp - 60;
			g = 288.1221695283 * FastMath.pow(g, -0.0755148492);
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
			b = 138.5177312231 * FastMath.log(b) - 305.0447927307;
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

		Debugger.stop("kelvin-compute");
		return new Color((float) r, (float) g, (float) b, 1);
	}

	/**
	 * Adjusts the brightness of a color (additive) by adding {@code br} to all its
	 * components.
	 * 
	 * @param c  The color to adjust.
	 * @param br The factor to adjust by.
	 * @return A new color object.
	 */
	public static Color brightnessAdjust(Color c, float br) {
		float r = c.r + br;
		float g = c.g + br;
		float b = c.b + br;

		return new Color(r, g, b, c.a);
	}

	/**
	 * Adjusts the contrast of a color by the factor {@code c}.
	 * 
	 * @param color The color to adjust.
	 * @param c     The contrast factor.
	 * @return A new color object.
	 */
	public static Color contrastAdjust(Color color, float c) {
		float t = 0.5f - c * 0.5f;
		float r = color.r * c + t;
		float g = color.g * c + t;
		float b = color.b * c + t;

		return new Color(r, g, b, color.a);
	}

	/**
	 * Creates a matrix for the {@code saturationAdjust} function.
	 * 
	 * @param saturation A value between 0 and infinity.
	 * @return A matrix4f object.
	 */
	private static Matrix4f saturationMatrix(float saturation) {
		Vector3f luminance = new Vector3f(0.3086f, 0.6094f, 0.0820f);
		float oneMinusSat = 1.0f - saturation;
		Vector3f red = new Vector3f(luminance.x * oneMinusSat, luminance.x * oneMinusSat, luminance.x * oneMinusSat);
		red.x += saturation;

		Vector3f green = new Vector3f(luminance.y * oneMinusSat, luminance.y * oneMinusSat, luminance.y * oneMinusSat);
		green.y += saturation;

		Vector3f blue = new Vector3f(luminance.z * oneMinusSat, luminance.z * oneMinusSat, luminance.z * oneMinusSat);
		blue.z += saturation;

		Matrix4f mat4 = new Matrix4f();

		mat4.m00 = red.x;
		mat4.m01 = red.y;
		mat4.m02 = red.z;
		mat4.m03 = 0;

		mat4.m10 = green.x;
		mat4.m11 = green.y;
		mat4.m12 = green.z;
		mat4.m13 = 0;

		mat4.m20 = blue.x;
		mat4.m21 = blue.y;
		mat4.m22 = blue.z;
		mat4.m23 = 0;

		mat4.m30 = 0;
		mat4.m31 = 0;
		mat4.m32 = 0;
		mat4.m33 = 1;

		return mat4;
	}

	// In the original shader code left so that nothing brakes.
	// XXX Figure out what this does and optimise it.
	private static int modi(int x, int y) {
		return x - y * (x / y);
	}

	// In the original shader code left so that nothing brakes.
	// XXX Figure out what this does and optimise it.
	private static int and(int a, int b) {
		int result = 0;
		int n = 1;
		final int BIT_COUNT = 32;

		for (int i = 0; i < BIT_COUNT; i++) {
			if ((modi(a, 2) == 1) && (modi(b, 2) == 1)) {
				result += n;
			}

			a >>= 1;
			b >>= 1;
			n <<= 1;

			if (!(a > 0 && b > 0))
				break;
		}
		return result;
	}

	/**
	 * Linearly interpolate between two values.
	 * 
	 * {@code mix} performs a linear interpolation between x and y using a to weight
	 * between them. The return value is computed as @code{x*(1-a)+y*a}. For a
	 * component of a that is 0, the corresponding component of x is returned. For a
	 * component of a that is 1, the corresponding component of y is returned.
	 * Components of x and y that are not selected are allowed to be invalid
	 * floating-point values and will have no effect on the results.
	 * 
	 * {@link https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/mix.xhtml}
	 * 
	 * @param x Specify the start of the range in which to interpolate.
	 * @param y Specify the end of the range in which to interpolate.
	 * @param a Specify the value to use to interpolate between x and y.
	 * @return A new Vector3f.
	 */
	private static Vector3f mix(Vector3f x, Vector3f y, float a) {
		if (a == 0) {
			return new Vector3f(x);
		}
		if (a == 1) {
			return new Vector3f(y);
		}
		return new Vector3f(x.x * (1 - a) + y.x * a, x.y * (1 - a) + y.y * a, x.z * (1 - a) + y.z * a);
	}

	/**
	 * step generates a step function by comparing x to edge.
	 * 
	 * @param edge Specifies the location of the edge of the step function.
	 * @param x    Specify the value to be used to generate the step function.
	 * @return 0.0 is returned if x < edge, and 1.0 is returned otherwise.
	 */
	private static float step(float edge, float x) {
		if (x < edge) {
			return 0;
		} else {
			return 1;
		}
	}

	// forked from https://www.shadertoy.com/view/llGSzK
	// performance optimized by Ruofei
	// r,g,b 0.0 to 1.0, vibrance 1.0 no change, 0.0 image B&W.
	/**
	 * Changes the vibrance of a color.
	 * 
	 * @param inCol    The color to change.
	 * @param vibrance A value from 0 to infinity.
	 * @return A vibrance of 0 will produce a grayscale color, a vibrance between 0
	 *         and 1 will desaturate the color, a vibrance of 1 will return the same
	 *         color and a vibrance above 1 will return a more vibrant color.
	 */
	private static Vector4f vibrance(Vector4f inCol, float vibrance) {
		Vector4f outCol = new Vector4f();
		if (vibrance <= 1.0) {
			float avg = Vector3f.dot(new Vector3f(inCol.x, inCol.y, inCol.z), new Vector3f(0.3f, 0.6f, 0.1f));
			Vector3f rgb = mix(new Vector3f(avg, avg, avg), new Vector3f(inCol.x, inCol.y, inCol.z), vibrance);
			outCol.x = rgb.x;
			outCol.y = rgb.y;
			outCol.z = rgb.z;
		} else { // vibrance > 1.0
			float hue_a, a, f, p1, p2, p3, i, h, s, v, _max, _min, dlt;
			float br1, br2, br3, br4, br2_or_br1, br3_or_br1, br4_or_br1;
			int use;

			_min = FastMath.min(FastMath.min(inCol.x, inCol.y), inCol.z);
			_max = FastMath.max(FastMath.max(inCol.x, inCol.y), inCol.z);
			dlt = _max - _min + 0.00001f /* Hack to fix divide zero infinities */;
			h = 0.0f;
			v = _max;

			br1 = step(_max, 0.0f);
			s = (dlt / _max) * (1.0f - br1);
			h = -1.0f * br1;

			br2 = 1.0f - step(_max - inCol.x, 0.0f);
			br2_or_br1 = FastMath.max(br2, br1);
			h = ((inCol.y - inCol.z) / dlt) * (1.0f - br2_or_br1) + (h * br2_or_br1);

			br3 = 1.0f - step(_max - inCol.y, 0.0f);

			br3_or_br1 = FastMath.max(br3, br1);
			h = (2.0f + (inCol.z - inCol.x) / dlt) * (1.0f - br3_or_br1) + (h * br3_or_br1);

			br4 = 1.0f - br2 * br3;
			br4_or_br1 = FastMath.max(br4, br1);
			h = (4.0f + (inCol.x - inCol.y) / dlt) * (1.0f - br4_or_br1) + (h * br4_or_br1);

			h = h * (1.0f - br1);

			hue_a = FastMath.abs(h); // between h of -1 and 1 are skin tones
			a = dlt; // Reducing enhancements on small rgb differences

			// Reduce the enhancements on skin tones.
			a = step(1.0f, hue_a) * a * (hue_a * 0.67f + 0.33f) + step(hue_a, 1.0f) * a;
			a *= (vibrance - 1.0);
			s = (1.0f - a) * s + a * (float) FastMath.pow(s, 0.25f);

			i = (float) FastMath.floor(h);
			f = h - i;

			p1 = v * (1.0f - s);
			p2 = v * (1.0f - (s * f));
			p3 = v * (1.0f - (s * (1.0f - f)));

			inCol.x = 0;
			inCol.y = 0;
			inCol.z = 0;
			i += 6.0;
			// use = 1 << ((int)i % 6);
			use = (int) (FastMath.pow(2.0, i % 6));
			a = (float) and(use, 1); // i == 0;
			use >>= 1;
			inCol.x += a * v;
			inCol.y += a * p3;
			inCol.z += a * p1;

			a = (float) and(use, 1); // i == 1;
			use >>= 1;
			inCol.x += a * p2;
			inCol.y += a * v;
			inCol.z += a * p1;

			a = (float) and(use, 1); // i == 2;
			use >>= 1;
			inCol.x += a * p1;
			inCol.y += a * v;
			inCol.z += a * p3;

			a = (float) and(use, 1); // i == 3;
			use >>= 1;
			inCol.x += a * p1;
			inCol.y += a * p2;
			inCol.z += a * v;

			a = (float) and(use, 1); // i == 4;
			use >>= 1;
			inCol.x += a * p3;
			inCol.y += a * p1;
			inCol.z += a * v;

			a = (float) and(use, 1); // i == 5;
			use >>= 1;
			inCol.x += a * v;
			inCol.y += a * p1;
			inCol.z += a * p2;

			outCol = inCol;
		}

		outCol.w = inCol.w;

		return outCol;
	}

	/**
	 * Converts a color into a Vector4f.
	 * 
	 * @param c The color to convert.
	 * @return A new Vector4f object.
	 */
	private static Vector4f toVector(Color c) {
		return new Vector4f(c.r, c.g, c.b, c.a);
	}

	/**
	 * Converts a Vector4f into a color.
	 * 
	 * @param v The Vector4f to convert.
	 * @return A new Color object.
	 */
	private static Color toColor(Vector4f v) {
		return new Color(v.x, v.y, v.z, v.w);
	}

	/**
	 * Changes the vibrance of a color.
	 * 
	 * @param c The color to change.
	 * @param v A value from 0 to infinity.
	 * @return A vibrance of 0 will produce a grayscale color, a vibrance between 0
	 *         and 1 will desaturate the color, a vibrance of 1 will return the same
	 *         color and a vibrance above 1 will return a more vibrant color.
	 */
	public static Color vibrance(Color c, float v) {
		return toColor(vibrance(toVector(c), v));
	}

	/**
	 * Multiplies all components of x by y.
	 * 
	 * @param x The vector to multiply.
	 * @param y The multiplication factor.
	 * @return A modified Vector3f.
	 */
	private static Vector3f mul(Vector3f x, float y) {
		x.x *= y;
		x.y *= y;
		x.z *= y;

		return x;
	}

	/**
	 * Multiplies all components of x by y.
	 * 
	 * @param x The vector to multiply.
	 * @param y The multiplication factor.
	 * @return A modified Vector3f.
	 */
	private static Vector3f mul(Vector3f x, double y) {
		return mul(x, (float) y);
	}

	// remixed from mAlk's https://www.shadertoy.com/view/MsjXRt
	/**
	 * Adjusts the hue of a color by a factor.
	 * 
	 * @param col   The color to adjust.
	 * @param Shift The value to alter it's hue by.
	 * @return A new Vector4f object.
	 */
	private static Vector4f shiftHue(Vector4f col, float Shift) {
		Vector3f P = new Vector3f(0.55735f, 0.55735f, 0.55735f);
		float dot_P = Vector3f.dot(new Vector3f(0.55735f, 0.55735f, 0.55735f), new Vector3f(col.x, col.y, col.z));
		P.x *= dot_P;
		P.y *= dot_P;
		P.z *= dot_P;
		Vector3f U = Vector3f.sub(P, new Vector3f(col.x, col.y, col.z), new Vector3f());
		Vector3f V = Vector3f.cross(new Vector3f(0.55735f, 0.55735f, 0.55735f), U, new Vector3f());
		Vector3f col3 = Vector3f.add(Vector3f.add(mul(U, FastMath.cos(Shift * 6.2832)),
				mul(V, FastMath.sin(Shift * 6.2832)), new Vector3f()), P, new Vector3f());
		return new Vector4f(col3.x, col3.y, col3.z, col.w);
	}

	/**
	 * Adjusts the saturation of a color by sat. {@code vibrance()} may offer a
	 * better effect.
	 * 
	 * @param col The color to adjust.
	 * @param sat A value from 0 to infinity.
	 * @return A saturation of 0 will produce a grayscale color, a saturation
	 *         between 0 and 1 will desaturate the color, a saturation of 1 will
	 *         return the same color and a saturation above 1 will return a more
	 *         saturated color.
	 */
	private static Vector4f saturationAdjust(Vector4f col, float sat) {
		Matrix4f matrix = saturationMatrix(sat);

		return Matrix4f.transform(matrix, col, new Vector4f());
	}

	/**
	 * Adjusts the saturation of a color by sat. {@code vibrance()} may offer a
	 * better effect.
	 * 
	 * @param c   The color to adjust.
	 * @param sat A value from 0 to infinity.
	 * @return A saturation of 0 will produce a grayscale color, a saturation
	 *         between 0 and 1 will desaturate the color, a saturation of 1 will
	 *         return the same color and a saturation above 1 will return a more
	 *         saturated color.
	 */
	public static Color saturationAdjust(Color c, float sat) {
		return toColor(saturationAdjust(toVector(c), sat));
	}

	/**
	 * Adjusts the hue of a color by a factor.
	 * 
	 * @param col   The color to adjust.
	 * @param shift The value to alter it's hue by.
	 * @return A new Color object.
	 */
	public static Color shiftHue(Color col, float shift) {
		return toColor(shiftHue(toVector(col), shift));
	}

	/**
	 * A system for generating sun colors at a specific time.
	 * 
	 * @author Tomas
	 */
	@Deprecated
	public static class SunColorGenerator {
		/**
		 * Sun color red channel.
		 */
		private PolynomialSplineFunction sun_r;
		/**
		 * Sun color green channel.
		 */
		private PolynomialSplineFunction sun_g;
		/**
		 * Sun color blue channel.
		 */
		private PolynomialSplineFunction sun_b;
		/**
		 * Sky color red channel.
		 */
		private PolynomialSplineFunction sky_r;
		/**
		 * Sky color green channel.
		 */
		private PolynomialSplineFunction sky_g;
		/**
		 * Sky color blue channel.
		 */
		private PolynomialSplineFunction sky_b;
		/**
		 * Sun x position.
		 */
		private PolynomialSplineFunction sun_x;
		/**
		 * Sun y position.
		 */
		private PolynomialSplineFunction sun_y;
		/**
		 * Moon x position.
		 */
		private PolynomialSplineFunction moon_x;
		/**
		 * Moon y position.
		 */
		private PolynomialSplineFunction moon_y;
		/**
		 * The scale factor for the size of the sun.
		 */
		private float sunSize;

		/**
		 * Creates a generator based on a set of colors.
		 * 
		 * @param sunlight     The color of light at noon.
		 * @param moonlight    The color of light at midnight.
		 * @param sundown1     The color of light during early sundown / late sunrise.
		 * @param sundown2     The color of light during late sundown / early sunrise.
		 * @param night        The color of light bat night.
		 * @param sunlightSky  The color of the sky at noon.
		 * @param sundown1Sky  The color of the sky during early sundown / late sunrise.
		 * @param sundown2Sky  The color of the sky during late sundown / early sunrise.
		 * @param moonlightSky The color of the sky at midnight.
		 * @param nightSky     The color of the sky at night.
		 * @param sunSize      The size of the sun (1 is normal).
		 */
		public SunColorGenerator(Color sunlight, Color moonlight, Color sundown1, Color sundown2, Color night,
				Color sunlightSky, Color sundown1Sky, Color sundown2Sky, Color moonlightSky, Color nightSky,
				float sunSize) {
			this.sunSize = sunSize;

			init(sunlight, moonlight, sundown1, sundown2, night, sunlightSky, sundown1Sky, sundown2Sky, moonlightSky,
					nightSky);
		}

		/**
		 * Initi
		 * 
		 * @param sunlight
		 * @param moonlight
		 * @param sundown1
		 * @param sundown2
		 * @param night
		 * @param sunlightSky
		 * @param sundown1Sky
		 * @param sundown2Sky
		 * @param moonlightSky
		 * @param nightSky
		 */
		private void init(Color sunlight, Color moonlight, Color sundown1, Color sundown2, Color night,
				Color sunlightSky, Color sundown1Sky, Color sundown2Sky, Color moonlightSky, Color nightSky) {
			double[] x = { 0, 4.5, 5, 5.5, 6.5, 17, 18.5, 19, 20, 24, };
			double[] r = { moonlight.r, night.r, sundown2.r, sundown1.r, sunlight.r, sunlight.r, sundown1.r, sundown2.r,
					night.r, moonlight.r, };
			double[] g = { moonlight.g, night.g, sundown2.g, sundown1.g, sunlight.g, sunlight.g, sundown1.g, sundown2.g,
					night.g, moonlight.g, };
			double[] b = { moonlight.b, night.b, sundown2.b, sundown1.b, sunlight.b, sunlight.b, sundown1.b, sundown2.b,
					night.b, moonlight.b, };

			SplineInterpolator interp = new SplineInterpolator();
			sun_r = interp.interpolate(x, r);
			sun_g = interp.interpolate(x, g);
			sun_b = interp.interpolate(x, b);

			double[] sky_x = { 0, 4, 5, 5.5, 6.5, 17, 18.5, 19, 20.5, 24 };
			double[] sky_r = { moonlightSky.r, nightSky.r, sundown2Sky.r, sundown1Sky.r, sunlightSky.r, sunlightSky.r,
					sundown1Sky.r, sundown2Sky.r, nightSky.r, moonlightSky.r, };
			double[] sky_g = { moonlightSky.g, nightSky.g, sundown2Sky.g, sundown1Sky.g, sunlightSky.g, sunlightSky.g,
					sundown1Sky.g, sundown2Sky.g, nightSky.g, moonlightSky.g, };
			double[] sky_b = { moonlightSky.b, nightSky.b, sundown2Sky.b, sundown1Sky.b, sunlightSky.b, sunlightSky.b,
					sundown1Sky.b, sundown2Sky.b, nightSky.b, moonlightSky.b, };

			this.sky_r = interp.interpolate(sky_x, sky_r);
			this.sky_g = interp.interpolate(sky_x, sky_g);
			this.sky_b = interp.interpolate(sky_x, sky_b);

			double[] sun_t = { 0, 3, 5, 12, 18, 20, 24 };
			double[] sun_x = { -2, -1.1, -0.95, 0, 0.95, 1.1, 2 };
			double[] sun_y = { 1.5, 0.9, 0.25, -0.75, 0.25, 0.9, 1.5 };
			double[] moon_t = { -4, 0, 3, 5, 12, 18, 20, 24, 28 };
			double[] moon_x = { -0.9, 0, 0.9, 2, 0, -2, -0.9, 0, 0.9 };
			double[] moon_y = { 0.25, -0.75, 0.25, 0.9, 1.5, 0.9, 0.25, -0.75, 0.25 };

			this.sun_x = interp.interpolate(sun_t, sun_x);
			this.sun_y = interp.interpolate(sun_t, sun_y);
			this.moon_x = interp.interpolate(moon_t, moon_x);
			this.moon_y = interp.interpolate(moon_t, moon_y);
		}

		/**
		 * Gets the sunlight color at a specified time starting at midnight.
		 * 
		 * @param time A time (in milliseconds).
		 * @return A new color instance.
		 */
		public Color getSunLight(long time) {
			return getSunLight((time - 1561248000000L) / 1000.0 / 60.0 / 60.0 % 24);
		}

		/**
		 * Gets the sunlight color at a specified time starting at midnight.
		 * 
		 * @param time A time (in hours).
		 * @return A new color instance.
		 */
		public Color getSunLight(double hour) {
			return new Color((float) sun_r.value(hour % 24), (float) sun_g.value(hour % 24),
					(float) sun_b.value(hour % 24));
		}

		/**
		 * Gets the sky color at a specified time starting at midnight.
		 * 
		 * @param time A time (in milliseconds).
		 * @return A new color instance.
		 */
		public Color getSkyColor(long time) {
			return getSkyColor((time - 1561248000000L) / 1000.0 / 60.0 / 60.0 % 24);
		}

		/**
		 * Gets the sky color at a specified time starting at midnight.
		 * 
		 * @param time A time (in hours).
		 * @return A new color instance.
		 */
		public Color getSkyColor(double hour) {
			return new Color((float) sky_r.value(hour), (float) sky_g.value(hour), (float) sky_b.value(hour));
		}

		/**
		 * Gets the scale factor for the size of the sun.
		 * 
		 * @return A float in the range 0 to Infinity.
		 */
		public float getSunSize() {
			return sunSize;
		}

		/**
		 * Gets the x position of the sun at a specified hour.
		 * 
		 * @param hour A time (in hours)
		 * @return A double value.
		 */
		public double getSunX(double hour) {
			return sun_x.value(hour % 24);
		}

		/**
		 * Gets the y position of the sun at a specified hour.
		 * 
		 * @param hour A time (in hours)
		 * @return A double value.
		 */
		public double getSunY(double hour) {
			return sun_y.value(hour % 24);
		}

		/**
		 * Gets the x position of the moon at a specified hour.
		 * 
		 * @param hour A time (in hours)
		 * @return A double value.
		 */
		public double getMoonX(double hour) {
			return moon_x.value(hour % 24);
		}

		/**
		 * Gets the y position of the moon at a specified hour.
		 * 
		 * @param hour A time (in hours)
		 * @return A double value.
		 */
		public double getMoonY(double hour) {
			return moon_y.value(hour % 24);
		}
	}
}

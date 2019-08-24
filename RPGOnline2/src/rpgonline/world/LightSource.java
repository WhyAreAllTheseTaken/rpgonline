package rpgonline.world;

import java.io.Serializable;

import org.newdawn.slick.Color;

/**
 * A class for storing point light data.
 * @author Tomas
 */
public class LightSource implements Serializable {
	/**
	 * Used for storage in older versions.
	 */
	@Deprecated
	private static final long serialVersionUID = 3084200355976862821L;
	/**
	 * The X position of the light.
	 */
	public double x;
	/**
	 * The Y position of the light.
	 */
	public double y;
	/**
	 * The color of the light.
	 */
	public Color c;
	/**
	 * The brightness of the light.
	 */
	public float brightness;
	
	/**
	 * Constructs a new light source.
	 * @param x The X position of the light.
	 * @param y The Y position of the light.
	 * @param c The color of the light.
	 * @param brightness The brightness of the light.
	 */
	public LightSource(double x, double y, Color c, float brightness) {
		super();
		this.x = x;
		this.y = y;
		this.c = c;
		this.brightness = brightness;
	}

	/**
	 * Gets the X position of the light.
	 * @return A double value.
	 */
	public double getLX() {
		return x;
	}

	/**
	 * Gets the Y position of the light.
	 * @return A double value.
	 */
	public double getLY() {
		return y;
	}

	/**
	 * Gets the red channel of this light.
	 * @return A float value in the range 0..1.
	 */
	public float getR() {
		return c.r;
	}

	/**
	 * Gets the green channel of this light.
	 * @return A float value in the range 0..1.
	 */
	public float getG() {
		return c.g;
	}

	/**
	 * Gets the blue channel of this light.
	 * @return A float value in the range 0..1.
	 */
	public float getB() {
		return c.b;
	}

	/**
	 * Gets the color of this light.
	 * @return A color object.
	 */
	public Color getColor() {
		return c;
	}

	/**
	 * Gets the brightness of this light.
	 * @return A float value in the range 0..Infinity.
	 */
	public float getBrightness() {
		return brightness;
	}
}

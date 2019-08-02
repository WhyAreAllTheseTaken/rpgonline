package rpgonline.world;

import java.io.Serializable;

import org.newdawn.slick.Color;

public class LightSource implements Serializable {
	private static final long serialVersionUID = 3084200355976862821L;
	public double x;
	public double y;
	public Color c;
	public float brightness;
	
	public LightSource(double x, double y, Color c, float brightness) {
		super();
		this.x = x;
		this.y = y;
		this.c = c;
		this.brightness = brightness;
	}

	public double getLX() {
		return x;
	}

	public double getLY() {
		return y;
	}

	public float getR() {
		return c.r;
	}

	public float getG() {
		return c.g;
	}

	public float getB() {
		return c.b;
	}

	public Color getColor() {
		return c;
	}

	public float getBrightness() {
		return brightness;
	}
}

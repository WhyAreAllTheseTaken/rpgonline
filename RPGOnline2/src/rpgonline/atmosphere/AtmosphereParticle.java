package rpgonline.atmosphere;

import org.newdawn.slick.Color;

public class AtmosphereParticle implements Scatterer {
	public float r;
	public float g;
	public float b;
	public float size;
	
	public AtmosphereParticle(float r, float g, float b, float size) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
		this.size = size;
	}
	
	@Override
	public Color scatter(Color c, float v) {
		float cr = (c.r * (1 - size) + c.r * r * size);
		float cg = (c.g * (1 - size) + c.g * g * size);
		float cb = (c.b * (1 - size) + c.b * b * size);
		
		return new Color(cr, cg, cb);
	}
}

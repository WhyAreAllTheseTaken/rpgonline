package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import rpgonline.world.World;

public class TimeParallax implements SkyLayer {
	private final double factorX;
	private final double factorY;
	private final SkyLayer l;
	
	public TimeParallax(double factorX, double factorY, SkyLayer l) {
		super();
		this.factorX = factorX;
		this.factorY = factorY;
		this.l = l;
	}

	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		l.render(g, c, x + ((System.currentTimeMillis() - 1561284966000L) * factorX), y + ((System.currentTimeMillis() - 1561284966000L) * factorY), z, world, light);
	}

} 

package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import rpgonline.world.World;

public class MultiSkyLayer implements SkyLayer {
	private final SkyLayer[] skys;
	
	public MultiSkyLayer(SkyLayer[] skys) {
		super();
		this.skys = skys;
	}

	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		for (SkyLayer skyLayer : skys) {
			skyLayer.render(g, c, x, y, z, world, light);
		}
	}

}

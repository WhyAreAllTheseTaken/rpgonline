package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import rpgonline.world.World;

public class ColorLayer implements SkyLayer {
	private final Color c;
	
	public ColorLayer(Color c) {
		super();
		this.c = c;
	}

	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		g.setColor(this.c);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
	}

}

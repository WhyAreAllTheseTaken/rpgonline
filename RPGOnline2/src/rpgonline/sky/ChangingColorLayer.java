package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import rpgonline.world.World;

public abstract class ChangingColorLayer implements SkyLayer {
	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		g.setColor(getColor(g, c, x, y, z, world));
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
	}
	
	public abstract Color getColor(Graphics g, GameContainer c, double x, double y, double z, World world);
}

package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import rpgonline.world.World;

public interface SkyLayer {
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light);
}

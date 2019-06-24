package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import rpgonline.ColorUtils.SunColorGenerator;
import rpgonline.texture.TextureMap;
import rpgonline.world.World;

public abstract class MoonLayer implements SkyLayer {
	private final SunColorGenerator sg;
	public MoonLayer(SunColorGenerator sg) {
		this.sg = sg;
	}
	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		double time = getTime();
		float sx = (float) sg.getMoonX(time) * c.getWidth() / 2 + c.getWidth() / 2;
		float sy =  (float) sg.getMoonY(time) * c.getHeight() / 2 + c.getHeight() / 2;
		float size = sg.getSunSize() * 128;
		
		Image img = TextureMap.getTexture("moon").getScaledCopy((int) size, (int) size);
		g.drawImage(img, sx + size / 2, sy + size / 2);
	}
	
	public abstract double getTime();
}

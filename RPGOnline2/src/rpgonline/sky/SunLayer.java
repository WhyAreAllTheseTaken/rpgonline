package rpgonline.sky;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import rpgonline.ColorUtils.SunColorGenerator;
import rpgonline.texture.TextureMap;
import rpgonline.world.World;

public abstract class SunLayer implements SkyLayer {
	private final SunColorGenerator sg;
	public SunLayer(SunColorGenerator sg) {
		this.sg = sg;
	}
	@Override
	public void render(Graphics g, GameContainer c, double x, double y, double z, World world, Color light) {
		double time = getTime();
		float sx = (float) sg.getSunX(time) * c.getWidth() / 2 + c.getWidth() / 2;
		float sy =  (float) sg.getSunY(time) * c.getHeight() / 2 + c.getHeight() / 2;
		float size = sg.getSunSize() * 256;
		
		Image img = TextureMap.getTexture("sun").getScaledCopy((int) size, (int) size);
		g.drawImage(img, sx + size / 2, sy + size / 2, sg.getSunLight(time));
	}
	
	public abstract double getTime();

}

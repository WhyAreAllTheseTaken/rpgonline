package rpgonline.net;

import rpgonline.audio.AmbientMusic;
import rpgonline.world.World;

public interface Client {
	public World getWorld();
	public double getPlayerX();
	public double getPlayerY();
	public float getHeatEffect();
	public float getWind();
	public float getShadow();
	public AmbientMusic getMusic();
	public void walkY(float s);
	public void walkX(float s);
}

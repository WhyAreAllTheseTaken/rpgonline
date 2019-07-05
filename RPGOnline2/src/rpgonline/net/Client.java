package rpgonline.net;

import java.util.List;

import rpgonline.abt.TagGroup;
import rpgonline.audio.AmbientMusic;
import rpgonline.world.World;

public interface Client extends TickBased {
	public World getWorld();
	public double getPlayerX();
	public double getPlayerY();
	public float getHeatEffect();
	public float getWind();
	public AmbientMusic getMusic();
	public void walkY(double s);
	public void walkX(double s);
	public void requestChunk(long x, long y, long z);
	public List<TagGroup> getRequestedChunks();
	public String getServerType();
	public void setSprint(boolean s);
}

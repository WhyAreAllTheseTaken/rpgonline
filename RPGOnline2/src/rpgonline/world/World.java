package rpgonline.world;

import java.util.List;

import org.newdawn.slick.Color;

import rpgonline.entity.Entity;
import rpgonline.tile.Tile;

public interface World {
	public Tile getTile(long x, long y, long z);

	public String getTileState(long x, long y, long z);

	public void setTile(long x, long y, long z, Tile tile, String state);

	public void setAreaID(long x, long y, long z, String id);

	public String getAreaID(long x, long y, long z);

	public void setBiomeID(long x, long y, long z, int id);

	public int getBiomeID(long x, long y, long z);

	public default void setTile(long x, long y, long z, Tile tile) {
		setTile(x, y, z, tile, "");
	}

	public long getMinZ();

	public long getMaxZ();

	public long getMinX();

	public long getMaxX();

	public long getMinY();

	public long getMaxY();

	public default void doUpdateClient() {
	}

	public default void doUpdateServer() {
		if (System.currentTimeMillis() / 10 % 10 == 0) {
			doUpdateClient();
		}
	}

	public default Color getLightColor() {
		return Color.white;
	}

	public default void save() {

	}

	public void addLight(LightSource light);

	public void removeLight(LightSource light);

	public List<LightSource> getLights();

	public List<Entity> getEntities();
}

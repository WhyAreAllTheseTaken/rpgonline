package rpgonline.world;

import java.util.List;

import org.newdawn.slick.Color;

import rpgonline.entity.Entity;
import rpgonline.tile.Tile;

public class WorldWrapper implements World {
	private final World world;

	public WorldWrapper(World world) {
		this.world = world;
	}

	@Override
	public Tile getTile(long x, long y, long z) {
		return world.getTile(x, y, z);
	}

	@Override
	public String getTileState(long x, long y, long z) {
		return world.getTileState(x, y, z);
	}

	@Override
	public void setTile(long x, long y, long z, Tile tile, String state) {
		world.setTile(x, y, z, tile, state);
	}

	@Override
	public long getMinZ() {
		return world.getMinZ();
	}

	@Override
	public long getMaxZ() {
		return world.getMaxZ();
	}

	@Override
	public long getMinX() {
		return world.getMinX();
	}

	@Override
	public long getMaxX() {
		return world.getMaxX();
	}

	@Override
	public long getMinY() {
		return world.getMinY();
	}

	@Override
	public long getMaxY() {
		return world.getMaxY();
	}

	@Override
	public void addLight(LightSource light) {
		world.addLight(light);
	}

	@Override
	public void removeLight(LightSource light) {
		world.removeLight(light);
	}

	@Override
	public List<LightSource> getLights() {
		return world.getLights();
	}

	@Override
	@Deprecated
	public Color getLightColor() {
		return world.getLightColor();
	}

	@Override
	public void doUpdateClient() {
		world.doUpdateClient();
	}

	@Override
	public void doUpdateServer() {
		world.doUpdateServer();
	}

	@Override
	@Deprecated
	public void save() {
		world.save();
	}

	@Override
	public void setTile(long x, long y, long z, Tile tile) {
		world.setTile(x, y, z, tile);
	}

	@Override
	public void setAreaID(long x, long y, long z, String id) {
		world.setAreaID(x, y, z, id);
	}

	@Override
	public String getAreaID(long x, long y, long z) {
		return world.getAreaID(x, y, z);
	}

	@Override
	public void setBiomeID(long x, long y, long z, int id) {
		world.setBiomeID(x, y, z, id);
	}

	@Override
	public int getBiomeID(long x, long y, long z) {
		return world.getBiomeID(x, y, z);
	}

	@Override
	public List<Entity> getEntities() {
		return world.getEntities();
	}
}

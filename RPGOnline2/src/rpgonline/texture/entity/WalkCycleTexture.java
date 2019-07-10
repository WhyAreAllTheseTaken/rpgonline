package rpgonline.texture.entity;

import rpgonline.entity.Entity;
import rpgonline.texture.TextureMap;
import rpgonline.world.World;

public class WalkCycleTexture implements EntityTexture {
	private final int[] north;
	private final int[] east;
	private final int[] south;
	private final int[] west;
	private final double speed;
	private final int len;
	private final float x;
	private final float y;
	
	public WalkCycleTexture(String base, int north, int east, int south, int west, int len, double speed, float x, float y) {
		this.north = new int[len];
		this.east = new int[len];
		this.south = new int[len];
		this.west = new int[len];
		this.speed = speed;
		this.len = len;
		
		for (int i = 0; i < len; i++) {
			this.north[i] = TextureMap.getTextureIndex(base + "." + (north + i));
			this.east[i] = TextureMap.getTextureIndex(base + "." + (east + i));
			this.south[i] = TextureMap.getTextureIndex(base + "." + (south + i));
			this.west[i] = TextureMap.getTextureIndex(base + "." + (west + i));
		}
		
		this.x = x;
		this.y = y;
	}
	
	public WalkCycleTexture(String base, int north, int east, int south, int west, int len, double speed) {
		this(base, north, east, south, west, len, speed, 0, 0);
	}
	
	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		int[] data;
		int index;
		switch(e.getDirection()) {
		case EAST:
			data = east;
			index = (int) Math.floor(Math.abs(x * speed % len));
			break;
		case NORTH:
			data = north;
			index = (int) Math.floor(Math.abs(y * speed % len));
			break;
		case SOUTH:
			data = south;
			index = (int) Math.floor(Math.abs(y * speed % len));
			break;
		case WEST:
			index = (int) Math.floor(Math.abs(x * speed % len));
			data = west;
			break;
		default:
			throw new IllegalArgumentException("Unknown direction: " + e.getDirection());
		}
		
		return data[index];
	}
	
	@Override
	public float getX() {
		return x;
	}
	
	@Override
	public float getY() {
		return y;
	}

}

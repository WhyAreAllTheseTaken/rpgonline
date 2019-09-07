package io.github.tomaso2468.rpgonline.texture.entity;

import io.github.tomaso2468.rpgonline.entity.Entity;
import io.github.tomaso2468.rpgonline.texture.TextureMap;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * A texture for an entity walk cycle.
 * @author Tomas
 *
 */
public class WalkCycleTexture implements EntityTexture {
	/**
	 * North textures.
	 */
	private final int[] north;
	/**
	 * East textures.
	 */
	private final int[] east;
	/**
	 * South textures.
	 */
	private final int[] south;
	/**
	 * West textures.
	 */
	private final int[] west;
	/**
	 * The speed of the walk cycle.
	 */
	private final double speed;
	/**
	 * Length of the walk cycle in frames.
	 */
	private final int len;
	/**
	 * The X offset for this texture.
	 */
	private final float x;
	/**
	 * The Y offset for this texture.
	 */
	private final float y;
	
	/**
	 * Constructs a new WalkCycleTexture.
	 * @param base The base texture ID to use.
	 * @param north The offset for north.
	 * @param east The offset for east.
	 * @param south The offset for south.
	 * @param west The offset for west.
	 * @param len The length of each walk cycle.
	 * @param speed The speed of the walk cycle.
	 * @param x The X offset for this texture.
	 * @param y The Y offset for this texture.
	 */
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
	
	/**
	 * Constructs a new WalkCycleTexture.
	 * @param base The base texture ID to use.
	 * @param north The offset for north.
	 * @param east The offset for east.
	 * @param south The offset for south.
	 * @param west The offset for west.
	 * @param len The length of each walk cycle.
	 * @param speed The speed of the walk cycle.
	 */
	public WalkCycleTexture(String base, int north, int east, int south, int west, int len, double speed) {
		this(base, north, east, south, west, len, speed, 0, 0);
	}
	
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getX() {
		return x;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getY() {
		return y;
	}

}

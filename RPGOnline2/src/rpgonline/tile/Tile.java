package rpgonline.tile;

import java.util.Map;

import org.newdawn.slick.Color;

import rpgonline.texture.TileTexture;
import rpgonline.world.World;

public class Tile {
	private final Color c;
	private final TileTexture t;
	private final String id;

	public Tile(String id, Color c, TileTexture t, Map<String, Tile> registry) {
		super();
		this.c = c;
		this.t = t;
		this.id = id;
		
		registry.put(id, this);
	}

	public boolean isSolid(String state) {
		return true;
	}

	public Color getColor() {
		return c;
	}

	public TileTexture getTexture() {
		return t;
	}
	
	public void update(long x, long y, long z, World w, String state, Tile t, float wind) {
		
	}
	
	public void update(long x, long y, long z, World w, String state, Tile t) {
		update(x, y, z, w, state, t, 0);
	}

	public String getID() {
		return id;
	}
	
	public boolean isDamage(String entity_id) {
		return false;
	}
}

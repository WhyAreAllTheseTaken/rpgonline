package rpgonline.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;

import rpgonline.texture.TileTexture;
import rpgonline.world.World;

public class Tile {
	private final Color c;
	private final TileTexture t;
	private final String id;
	private final List<String> tags = new ArrayList<String>();

	public Tile(String id, Color c, TileTexture t, Map<String, Tile> registry) {
		super();
		this.c = c;
		this.t = t;
		this.id = id;
		
		registry.put(id, this);
		
		addTag("id:" + id);
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
	
	public void update(long x, long y, long z, World w, String state, float wind) {
		
	}
	
	public void update(long x, long y, long z, World w, String state) {
		update(x, y, z, w, state, 0);
	}

	public String getID() {
		return id;
	}
	
	public boolean isDamage(String entity_id) {
		return false;
	}
	
	public void addTag(String tag) {
		tags.add(tag);
	}
	
	public boolean hasTag(String tag) {
		for (String t : tags) {
			if(t.equals(tag)) {
				return true;
			}
		}
		return false;
	}
}

package rpgonline.world.chunk;

import java.util.Map;

import rpgonline.abt.TagBoolean;
import rpgonline.abt.TagGroup;
import rpgonline.abt.TagInt;
import rpgonline.abt.TagLong;
import rpgonline.abt.TagString;
import rpgonline.tile.Tile;

/**
 * A class for holding world data in separate chunks.
 * @author Tomas
 *
 */
public class Chunk {
	/**
	 * The size of the chunk on the X and Y axis.
	 */
	public static final int SIZE = 64;
	private volatile long x;
	private volatile long y;
	private volatile long z;
	private volatile Tile[][][] tiles = new Tile[1][SIZE][SIZE];
	private volatile String[][][] states = new String[1][SIZE][SIZE];
	private volatile boolean[][][] flag = new boolean[1][SIZE][SIZE];
	private volatile String[][][] area = new String[1][SIZE][SIZE];
	private volatile int[][][] biome = new int[1][SIZE][SIZE];
	private boolean change = false;
	private long lastUsed = System.currentTimeMillis();

	public Chunk(Map<String, Tile> registry, long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
		for (int tx = 0; tx < tiles[0].length; tx++) {
			for (int ty = 0; ty < tiles[0][0].length; ty++) {
				for (int tz = 0; tz < tiles.length; tz++) {
					tiles[tz][tx][ty] = registry.get("air");
					states[tz][tx][ty] = "";
					area[tz][tx][ty] = null;
				}
			}
		}
	}

	public Tile getTile(long x, long y, long z) {
		lastUsed = System.currentTimeMillis();
		return tiles[(int) z][(int) x][(int) y];
	}

	public void setTile(long x, long y, long z, Tile tile) {
		lastUsed = System.currentTimeMillis();
		change = true;
		tiles[(int) z][(int) x][(int) y] = tile;
	}

	public String getState(long x, long y, long z) {
		lastUsed = System.currentTimeMillis();
		return states[(int) z][(int) x][(int) y];
	}

	public void setState(long x, long y, long z, String state) {
		lastUsed = System.currentTimeMillis();
		change = true;
		states[(int) z][(int) x][(int) y] = state;
	}

	public boolean getFlag(long x, long y, long z) {
		lastUsed = System.currentTimeMillis();
		return flag[(int) z][(int) x][(int) y];
	}

	public void setFlag(long x, long y, long z, boolean f) {
		lastUsed = System.currentTimeMillis();
		change = true;
		flag[(int) z][(int) x][(int) y] = f;
	}

	public String getArea(long x, long y, long z) {
		lastUsed = System.currentTimeMillis();
		return area[(int) z][(int) x][(int) y];
	}

	public void setArea(long x, long y, long z, String id) {
		lastUsed = System.currentTimeMillis();
		change = true;
		area[(int) z][(int) x][(int) y] = id;
	}

	public int getBiome(long x, long y, long z) {
		lastUsed = System.currentTimeMillis();
		return biome[(int) z][(int) x][(int) y];
	}

	public void setBiome(long x, long y, long z, int id) {
		lastUsed = System.currentTimeMillis();
		change = true;
		biome[(int) z][(int) x][(int) y] = id;
	}

	public long getX() {
		return x;
	}

	public long getY() {
		return y;
	}

	public long getZ() {
		return z;
	}

	public boolean isAt(long cx, long cy, long cz) {
		return x == cx && y == cy && z == cz;
	}

	public boolean isChanged() {
		return change;
	}

	public void setChanged(boolean change) {
		this.change = change;
	}

	public long getLastUsed() {
		return lastUsed;
	}
	
	public TagGroup save() {
		TagGroup tg = new TagGroup("chunk");
		
		tg.add(new TagLong("x", x));
		tg.add(new TagLong("y", y));
		tg.add(new TagLong("z", z));
		
		tg.add(new TagInt("version", 0x0));
		
		tg.add(new TagGroup("tile"));
		tg.add(new TagGroup("state"));
		tg.add(new TagGroup("flag"));
		tg.add(new TagGroup("area"));
		tg.add(new TagGroup("biome"));
		
		for (int cz = 0; cz < 1; cz++) {
			((TagGroup) tg.getTag("tile")).add(new TagGroup(cz + ""));
			((TagGroup) tg.getTag("state")).add(new TagGroup(cz + ""));
			((TagGroup) tg.getTag("flag")).add(new TagGroup(cz + ""));
			((TagGroup) tg.getTag("area")).add(new TagGroup(cz + ""));
			((TagGroup) tg.getTag("biome")).add(new TagGroup(cz + ""));
			for (int cy = 0; cy < Chunk.SIZE; cy++) {
				((TagGroup) tg.getTag("tile/" + cz)).add(new TagGroup(cy + ""));
				((TagGroup) tg.getTag("state/" + cz)).add(new TagGroup(cy + ""));
				((TagGroup) tg.getTag("flag/" + cz)).add(new TagGroup(cy + ""));
				((TagGroup) tg.getTag("area/" + cz)).add(new TagGroup(cy + ""));
				((TagGroup) tg.getTag("biome/" + cz)).add(new TagGroup(cy + ""));
				for (int cx = 0; cx < Chunk.SIZE; cx++) {
					((TagGroup) tg.getTag("tile/" + cz + "/" + cy)).add(new TagString(cx + "", getTile(cx, cy, cz).getID()));
					((TagGroup) tg.getTag("state/" + cz + "/" + cy)).add(new TagString(cx + "", getState(cx, cy, cz)));
					((TagGroup) tg.getTag("flag/" + cz + "/" + cy)).add(new TagBoolean(cx + "", getFlag(cx, cy, cz)));
					((TagGroup) tg.getTag("area/" + cz + "/" + cy)).add(new TagString(cx + "", getArea(cx, cy, cz)));
					((TagGroup) tg.getTag("biome/" + cz + "/" + cy)).add(new TagInt(cx + "", getBiome(cx, cy, cz)));
				}
			}
		}
		
		return tg;
	}
}

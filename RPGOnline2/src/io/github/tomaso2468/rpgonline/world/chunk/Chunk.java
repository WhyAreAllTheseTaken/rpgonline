package io.github.tomaso2468.rpgonline.world.chunk;

import java.util.Map;

import io.github.tomaso2468.rpgonline.abt.TagBoolean;
import io.github.tomaso2468.rpgonline.abt.TagGroup;
import io.github.tomaso2468.rpgonline.abt.TagInt;
import io.github.tomaso2468.rpgonline.abt.TagLong;
import io.github.tomaso2468.rpgonline.abt.TagString;
import io.github.tomaso2468.rpgonline.tile.Tile;

/**
 * A class for holding world data in separate chunks.
 * 
 * @author Tomas
 *
 */
public class Chunk {
	/**
	 * The size of the chunk on the X and Y axis.
	 */
	public static final int SIZE = 64;
	/**
	 * The X position of this chunk.
	 */
	private final long x;
	/**
	 * The Y position of this chunk.
	 */
	private final long y;
	/**
	 * The Z position of this chunk.
	 */
	private final long z;
	/**
	 * The tile data in this chunk.
	 */
	private Tile[][][] tiles = new Tile[1][SIZE][SIZE];
	/**
	 * The state data in this chunk.
	 */
	private String[][][] states = new String[1][SIZE][SIZE];
	/**
	 * The flag data in this chunk used for marking generated data.
	 */
	private boolean[][][] flag = new boolean[1][SIZE][SIZE];
	/**
	 * The area data in this chunk.
	 */
	private String[][][] area = new String[1][SIZE][SIZE];
	/**
	 * The biome data in this chunk.
	 */
	private int[][][] biome = new int[1][SIZE][SIZE];

	/**
	 * Constructs a new chunk.
	 * 
	 * @param registry The tile registry to use in this chunk/
	 * @param x        The X position of this chunk.
	 * @param y        The Y position of this chunk.
	 * @param z        The Z position of this chunk.
	 */
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

	/**
	 * Gets the tile at a specified position.
	 * 
	 * @param x The X position of this chunk.
	 * @param y The Y position of this chunk.
	 * @param z The Z position of this chunk.
	 * @return A non-null tile instance.
	 */
	public Tile getTile(long x, long y, long z) {
		return tiles[(int) z][(int) x][(int) y];
	}

	/**
	 * Sets the tile at a specified position.
	 * 
	 * @param x    The X position of this chunk.
	 * @param y    The Y position of this chunk.
	 * @param z    The Z position of this chunk.
	 * @param tile A non-null tile instance.
	 */
	public void setTile(long x, long y, long z, Tile tile) {
		tiles[(int) z][(int) x][(int) y] = tile;
	}

	/**
	 * Gets the state at a specified position.
	 * 
	 * @param x The X position of this chunk.
	 * @param y The Y position of this chunk.
	 * @param z The Z position of this chunk.
	 * @return A non-null string.
	 */
	public String getState(long x, long y, long z) {
		return states[(int) z][(int) x][(int) y];
	}

	/**
	 * Sets the state at a specified position.
	 * 
	 * @param x     The X position of this chunk.
	 * @param y     The Y position of this chunk.
	 * @param z     The Z position of this chunk.
	 * @param state A non-null string.
	 */
	public void setState(long x, long y, long z, String state) {
		states[(int) z][(int) x][(int) y] = state;
	}

	/**
	 * Gets the flag at a specified position that can be used for various purposes.
	 * 
	 * @param x The X position of this chunk.
	 * @param y The Y position of this chunk.
	 * @param z The Z position of this chunk.
	 * @return A boolean flag.
	 */
	public boolean getFlag(long x, long y, long z) {
		return flag[(int) z][(int) x][(int) y];
	}

	/**
	 * Sets the flag at a specified position that can be used for various purposes.
	 * 
	 * @param x The X position of this chunk.
	 * @param y The Y position of this chunk.
	 * @param z The Z position of this chunk.
	 * @param f A boolean flag.
	 */
	public void setFlag(long x, long y, long z, boolean f) {
		flag[(int) z][(int) x][(int) y] = f;
	}

	/**
	 * Gets the area at a specified position.
	 * 
	 * @param x The X position of this chunk.
	 * @param y The Y position of this chunk.
	 * @param z The Z position of this chunk.
	 * @return A string or null to indicate no area.
	 */
	public String getArea(long x, long y, long z) {
		return area[(int) z][(int) x][(int) y];
	}

	/**
	 * Sets the area at a specified position.
	 * 
	 * @param x  The X position of this chunk.
	 * @param y  The Y position of this chunk.
	 * @param z  The Z position of this chunk.
	 * @param id A string or null to indicate no area.
	 */
	public void setArea(long x, long y, long z, String id) {
		area[(int) z][(int) x][(int) y] = id;
	}

	/**
	 * Gets the biome at a specified position.
	 * 
	 * @param x The X position of this chunk.
	 * @param y The Y position of this chunk.
	 * @param z The Z position of this chunk.
	 * @return A int biome index.
	 */
	public int getBiome(long x, long y, long z) {
		return biome[(int) z][(int) x][(int) y];
	}

	/**
	 * Sets the biome at a specified position.
	 * 
	 * @param x  The X position of this chunk.
	 * @param y  The Y position of this chunk.
	 * @param z  The Z position of this chunk.
	 * @param id A int biome index.
	 */
	public void setBiome(long x, long y, long z, int id) {
		biome[(int) z][(int) x][(int) y] = id;
	}

	/**
	 * Gets the X position of this chunk.
	 * 
	 * @return A long value.
	 */
	public long getX() {
		return x;
	}

	/**
	 * Gets the Y position of this chunk.
	 * 
	 * @return A long value.
	 */
	public long getY() {
		return y;
	}

	/**
	 * Gets the Z position of this chunk.
	 * 
	 * @return A long value.
	 */
	public long getZ() {
		return z;
	}

	/**
	 * Determines if this chunk is at the specified position.
	 * 
	 * @param cx The X position to check against.
	 * @param cy The Y position to check against.
	 * @param cz The Z position to check against.
	 * @return {@code true} if this chunk is at the specified position,
	 *         {@code false} otherwise.
	 */
	public boolean isAt(long cx, long cy, long cz) {
		return x == cx && y == cy && z == cz;
	}

	/**
	 * Converts this chunk to a tag group.
	 * @return A tag group object containing chunk data.
	 */
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
					((TagGroup) tg.getTag("tile/" + cz + "/" + cy))
							.add(new TagString(cx + "", getTile(cx, cy, cz).getID()));
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

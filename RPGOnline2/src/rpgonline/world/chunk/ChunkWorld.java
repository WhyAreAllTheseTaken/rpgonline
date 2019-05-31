package rpgonline.world.chunk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import rpgonline.entity.Entity;
import rpgonline.tile.Tile;
import rpgonline.world.LightSource;
import rpgonline.world.World;

public class ChunkWorld implements World {
	protected final List<Chunk> chunks = new ArrayList<Chunk>();
	protected final List<CacheEntry> cache = Collections.synchronizedList(new ArrayList<CacheEntry>());
	protected Chunk last_chunk = null;
	private long mix = 0;
	private long max = 0;
	private long miy = 0;
	private long may = 0;
	private long miz = 0;
	private long maz = 0;
	private List<LightSource> lights = new ArrayList<LightSource>();
	protected long update_counter = 0;
	private Map<String, Tile> registry;
	private List<Entity> entities = new ArrayList<>();
	
	public ChunkWorld(Map<String, Tile> registry) {
		this.registry = registry;
	}

	@Override
	public synchronized Tile getTile(long x, long y, long z) {
		Tile t = getChunk(x, y, z).getTile(xToChunk(x), xToChunk(y), zToChunk(z));
		if (t == null) {
			return registry.get("air");
		}
		return t;
	}

	@Override
	public synchronized String getTileState(long x, long y, long z) {
		return getChunk(x, y, z).getState(xToChunk(x), xToChunk(y), zToChunk(z));
	}

	@Override
	public synchronized void setTile(long x, long y, long z, Tile tile, String state) {
		if (x < mix) {
			mix = x;
		}
		if (x > max) {
			max = x;
		}
		if (y < miy) {
			miy = y;
		}
		if (y > may) {
			may = y;
		}
		if (z < miz) {
			miz = z;
		}
		if (z > maz) {
			maz = z;
		}
		getChunk(x, y, z).setTile(xToChunk(x), xToChunk(y), zToChunk(z), tile);
		getChunk(x, y, z).setState(xToChunk(x), xToChunk(y), zToChunk(z), state);
	}

	@Override
	public synchronized long getMinZ() {
		return miz;
	}

	@Override
	public synchronized long getMaxZ() {
		return maz;
	}

	protected synchronized Chunk getChunk(long x, long y, long z) {
		long cx = (int) Math.floor(x / (Chunk.SIZE * 1f));
		long cy = (int) Math.floor(y / (Chunk.SIZE * 1f));
		long cz = (int) Math.floor(z / (2 * 1f));

		if (last_chunk != null) {
			if (last_chunk.isAt(cx, cy, cz)) {
				return last_chunk;
			}
		}

		synchronized (cache) {
			for (CacheEntry e : cache) {
				Chunk chunk = e.getChunk();
				if (chunk.isAt(cx, cy, cz)) {
					e.setTime(System.currentTimeMillis());
					last_chunk = chunk;
					return chunk;
				}
			}
		}

		for (Chunk chunk : chunks) {
			if (chunk.isAt(cx, cy, cz)) {
				cache.add(new CacheEntry(chunk, System.currentTimeMillis()));
				last_chunk = chunk;
				return chunk;
			}
		}

		Chunk chunk = new Chunk(registry, cx, cy, cz);
		chunks.add(chunk);
		cache.add(new CacheEntry(chunk, System.currentTimeMillis()));
		last_chunk = chunk;

		return chunk;
	}

	protected synchronized long xToChunk(long x) {
		return Math.abs(x) % Chunk.SIZE;
	}

	protected synchronized long zToChunk(long x) {
		return 0;
	}

	public synchronized void checkCacheServerQuick() {
		clearCacheQuick(1000 * 60 * 3, 32);
	}

	public synchronized void checkCacheClientQuick() {
		clearCacheQuick(1000 * 60, 8);
	}

	public synchronized void clearCacheQuick(long time, int max) {
		int count = 0;
		synchronized (cache) {
			for (int i = 0; i < cache.size(); i++) {
				if (count > max) {
					return;
				}
				CacheEntry e = cache.get(i);
				if (e.getTime() + time < System.currentTimeMillis()) {
					cache.remove(e);
					count += 1;
				}
			}
		}
	}

	public synchronized void checkCacheServer() {
		clearCache(1000 * 60 * 2);
	}

	public synchronized void checkCacheClient() {
		clearCache(1000 * 30);
	}

	public synchronized void clearCache(long time) {
		synchronized (cache) {
			for (int i = 0; i < cache.size(); i++) {
				CacheEntry e = cache.get(i);
				if (e.getTime() + time < System.currentTimeMillis()) {
					cache.remove(e);
				}
			}
		}
	}

	public synchronized void clearCacheFull() {
		cache.clear();
	}

	public synchronized void doUpdateClient() {
		update_counter += 1;
		if (update_counter % 60 == 0) {
			checkCacheClientQuick();
		}
		if (update_counter % 240 == 0) {
			checkCacheClient();
		}
		if (update_counter < 0) {
			update_counter = 0;
		}
	}

	public synchronized void doUpdateServer() {
		update_counter += 1;
		if (update_counter % 120 == 0) {
			checkCacheServerQuick();
		}
		if (update_counter % 480 == 0) {
			checkCacheServer();
		}
		if (update_counter < 0) {
			update_counter = 0;
		}
	}

	@Override
	public long getMinX() {
		return mix;
	}

	@Override
	public long getMaxX() {
		return max;
	}

	@Override
	public long getMinY() {
		return miy;
	}

	@Override
	public long getMaxY() {
		return may;
	}

	public List<Chunk> getChunks() {
		return new ArrayList<Chunk>(chunks);
	}

	@Override
	public void addLight(LightSource light) {
		lights.add(light);
	}

	@Override
	public void removeLight(LightSource light) {
		lights.remove(light);
	}

	@Override
	public List<LightSource> getLights() {
		return new ArrayList<LightSource>(lights);
	}

	@Override
	public void setAreaID(long x, long y, long z, String id) {
		getChunk(x, y, z).setArea(xToChunk(x), xToChunk(y), zToChunk(z), id);
	}

	@Override
	public String getAreaID(long x, long y, long z) {
		return getChunk(x, y, z).getArea(xToChunk(x), xToChunk(y), zToChunk(z));
	}

	@Override
	public void setBiomeID(long x, long y, long z, int id) {
		getChunk(x, y, z).setBiome(xToChunk(x), xToChunk(y), zToChunk(z), id);
	}

	@Override
	public int getBiomeID(long x, long y, long z) {
		return getChunk(x, y, z).getBiome(xToChunk(x), xToChunk(y), zToChunk(z));
	}

	@Override
	public List<Entity> getEntities() {
		return entities;
	}
}

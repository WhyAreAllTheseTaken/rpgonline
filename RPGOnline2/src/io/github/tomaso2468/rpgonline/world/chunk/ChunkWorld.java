/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.world.chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.tomaso2468.rpgonline.entity.Entity;
import io.github.tomaso2468.rpgonline.tile.Tile;
import io.github.tomaso2468.rpgonline.world.LightSource;
import io.github.tomaso2468.rpgonline.world.World;

/**
 * A class for storing world data using many chunks.
 * @author Tomas
 * 
 * @see io.github.tomaso2468.rpgonline.world.chunk.Chunk
 */
public class ChunkWorld implements World {
	/**
	 * The list of chunks in this world.
	 */
	protected final List<Chunk> chunks = new ArrayList<Chunk>();
	/**
	 * The list of more frequently used cached chunks.
	 */
	protected final List<CacheEntry> cache = new ArrayList<CacheEntry>();
	/**
	 * The most recently accessed chunk.
	 */
	protected Chunk last_chunk = null;
	/**
	 * The minimum X position of the world.
	 */
	private long mix = 0;
	/**
	 * The maximum X position of the world.
	 */
	private long max = 0;
	/**
	 * The minimum Y position of the world.
	 */
	private long miy = 0;
	/**
	 * The maximum Y position of the world.
	 */
	private long may = 0;
	/**
	 * The minimum Z position of the world.
	 */
	private long miz = 0;
	/**
	 * The maximum Z position of the world.
	 */
	private long maz = 0;
	/**
	 * The list of lights in this world.
	 */
	private List<LightSource> lights = new ArrayList<LightSource>();
	/**
	 * A counter used to determine when the next update will be.
	 */
	protected long update_counter = 0;
	/**
	 * The tile registry for this world.
	 */
	protected Map<String, Tile> registry;
	/**
	 * The list of entities in this world.
	 */
	private List<Entity> entities = new ArrayList<>();
	
	/**
	 * Constructs a new ChunkWorld
	 * @param registry The tile registry for this world.
	 */
	public ChunkWorld(Map<String, Tile> registry) {
		this.registry = registry;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tile getTile(long x, long y, long z) {
		Tile t = getChunk(x, y, z).getTile(xToChunk(x), xToChunk(y), zToChunk(z));
		if (t == null) {
			return registry.get("air");
		}
		return t;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTileState(long x, long y, long z) {
		return getChunk(x, y, z).getState(xToChunk(x), xToChunk(y), zToChunk(z));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTile(long x, long y, long z, Tile tile, String state) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMinZ() {
		return miz;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMaxZ() {
		return maz;
	}

	/**
	 * Gets a chunk at the specified position.
	 * @param x The X position of the chunk in world coordinates.
	 * @param y The Y position of the chunk in world coordinates.
	 * @param z The Z position of the chunk in world coordinates.
	 * @return A chunk object.
	 */
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

	/**
	 * Converts a X/Y world space coordinate to chunk space.
	 * @param x The X/Y position in world space coordinates.
	 * @return A chunk space equivalent of the coordinate.
	 */
	protected long xToChunk(long x) {
		return Math.abs(x) % Chunk.SIZE;
	}

	/**
	 * Converts a Z world space coordinate to chunk space.
	 * @param x The Z position in world space coordinates.
	 * @return A chunk space equivalent of the coordinate.
	 */
	protected long zToChunk(long x) {
		return 0;
	}

	/**
	 * A fast clear of the cache optimised for servers.
	 */
	public void checkCacheServerQuick() {
		clearCacheQuick(1000 * 60 * 3, 32);
	}

	/**
	 * A fast clear of the cache optimised for clients.
	 */
	public void checkCacheClientQuick() {
		clearCacheQuick(1000 * 60, 8);
	}

	/**
	 * A method that clears some of the cache.
	 * @param time The time delay for chunks being unloaded.
	 * @param max The maximum count of chunks to unload.
	 */
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

	/**
	 * A method that clears the cache optimised for servers.
	 */
	public void checkCacheServer() {
		clearCache(1000 * 60 * 2);
	}

	/**
	 * A method that clears the cache optimised for clients
	 */
	public void checkCacheClient() {
		clearCache(1000 * 30);
	}

	/**
	 * A method that clears the cache.
	 * @param time The time delay to keep chunks for.
	 */
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

	/**
	 * A method that completely empties the cache.
	 */
	public synchronized void clearCacheFull() {
		cache.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	public void doUpdateClient() {
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

	/**
	 * {@inheritDoc}
	 */
	public void doUpdateServer() {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMinX() {
		return mix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMaxX() {
		return max;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMinY() {
		return miy;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMaxY() {
		return may;
	}

	/**
	 * Gets a list of all chunks in this world.
	 * @return A unique list object.
	 */
	public synchronized List<Chunk> getChunks() {
		return new ArrayList<Chunk>(chunks);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void addLight(LightSource light) {
		lights.add(light);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void removeLight(LightSource light) {
		lights.remove(light);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized List<LightSource> getLights() {
		return new ArrayList<LightSource>(lights);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAreaID(long x, long y, long z, String id) {
		getChunk(x, y, z).setArea(xToChunk(x), xToChunk(y), zToChunk(z), id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAreaID(long x, long y, long z) {
		return getChunk(x, y, z).getArea(xToChunk(x), xToChunk(y), zToChunk(z));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBiomeID(long x, long y, long z, int id) {
		getChunk(x, y, z).setBiome(xToChunk(x), xToChunk(y), zToChunk(z), id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBiomeID(long x, long y, long z) {
		return getChunk(x, y, z).getBiome(xToChunk(x), xToChunk(y), zToChunk(z));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized List<Entity> getEntities() {
		return entities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setState(long x, long y, long z, String state) {
		getChunk(x, y, z).setState(x, y, z, state);
	}
}

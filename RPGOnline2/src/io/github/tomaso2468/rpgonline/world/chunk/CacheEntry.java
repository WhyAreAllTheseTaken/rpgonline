package io.github.tomaso2468.rpgonline.world.chunk;

/**
 * A class storing a cache of a chunk.
 * @author Tomas
 */
public class CacheEntry {
	/**
	 * The chunk to cache.
	 */
	Chunk chunk;
	/**
	 * The time of the last access to this chunk.
	 */
	long time;

	/**
	 * Constructs a new CacheEntry.
	 * @param chunk The chunk to cache.
	 * @param time The time of the last access to this chunk.
	 */
	public CacheEntry(Chunk chunk, long time) {
		this.chunk = chunk;
		this.time = time;
	}

	/**
	 * Gets the chunk that is cached.
	 * @return A chunk object.
	 */
	public Chunk getChunk() {
		return chunk;
	}

	/**
	 * Gets the time of the last access to this chunk.
	 * @return A long value in milliseconds.
	 */
	public long getTime() {
		return time;
	}
	
	/**
	 * Sets the time of the last access to this chunk.
	 * @param time A long value in milliseconds.
	 */
	public void setTime(long time) {
		this.time = time;
	}
}

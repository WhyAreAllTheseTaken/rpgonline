package rpgonline.world.chunk;

public class CacheEntry {
	Chunk chunk;
	long time;

	public CacheEntry(Chunk chunk, long time) {
		this.chunk = chunk;
		this.time = time;
	}

	public Chunk getChunk() {
		return chunk;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}

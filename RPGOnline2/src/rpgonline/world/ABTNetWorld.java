package rpgonline.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.util.Log;

import rpgonline.abt.TagBoolean;
import rpgonline.abt.TagDouble;
import rpgonline.abt.TagFloat;
import rpgonline.abt.TagGroup;
import rpgonline.abt.TagInt;
import rpgonline.abt.TagLong;
import rpgonline.abt.TagString;
import rpgonline.net.ServerManager;
import rpgonline.tile.Tile;
import rpgonline.world.chunk.CacheEntry;
import rpgonline.world.chunk.Chunk;
import rpgonline.world.chunk.ChunkWorld;

public class ABTNetWorld extends ChunkWorld {
	private boolean stop = false;
	public ABTNetWorld(File folder, Map<String, Tile> registry, List<TagGroup> lights) throws IOException {
		super(registry);
		
		for(TagGroup g : lights) {
			TagGroup c = (TagGroup) g.getTag("color");
			
			float lr = ((TagFloat) c.getTag("r")).getData();
			float lg = ((TagFloat) c.getTag("g")).getData();
			float lb = ((TagFloat) c.getTag("b")).getData();
			
			TagGroup pos = (TagGroup) g.getTag("pos");
			
			double x = ((TagDouble) pos.getTag("x")).getData();
			double y = ((TagDouble) pos.getTag("y")).getData();
			
			float b = ((TagFloat) c.getTag("brightness")).getData();
			
			Color color = new Color(lr, lg, lb);
			
			addLight(new LightSource(x, y, color, b));
		}
		
		new Thread(toString()) {
			public void run() {
				while (!stop) {
					List<TagGroup> requested = new ArrayList<TagGroup>(ServerManager.getClient().getRequestedChunks());
					
					for (TagGroup tg : requested) {
						long x = ((TagLong) tg.getTag("x")).getData();
						long y = ((TagLong) tg.getTag("y")).getData();
						long z = ((TagLong) tg.getTag("z")).getData();
						
						try {
							loadChunk(registry, x, y, z, tg, getChunk(x, y, z));
						} catch (IOException e) {
							Log.error("Error loading chunk.", e);
						}
					}
				}
			};
		}.start();
	}
	
	@Override
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
		
		ServerManager.getClient().requestChunk(cx, cy, cz);

		Chunk chunk = new Chunk(registry, cx, cy, cz);
		chunks.add(chunk);
		cache.add(new CacheEntry(chunk, System.currentTimeMillis()));
		last_chunk = chunk;

		return chunk;
	}
	
	protected Chunk loadChunk(Map<String, Tile> registry, long x, long y, long z, TagGroup tg, Chunk c) throws FileNotFoundException, IOException {
		int version = ((TagInt) tg.getTag("version")).getData();
		
		if (version == 0) {
			if (x == ((TagLong) tg.getTag("x")).getData()
					&& y == ((TagLong) tg.getTag("y")).getData()
					&& z == ((TagLong) tg.getTag("z")).getData()) {
				for (int cz = 0; cz < 1; cz++) {
					for (int cy = 0; cy < Chunk.SIZE; cy++) {
						for (int cx = 0; cx < Chunk.SIZE; cx++) {
							c.setTile(cx, cy, cz, registry.get(((TagString) tg.getTag("tile/" + cz + "/" + cy + "/" + cx)).getData()));
							c.setState(cx, cy, cz, ((TagString) tg.getTag("state/" + cz + "/" + cy + "/" + cx)).getData());
							c.setFlag(cx, cy, cz, ((TagBoolean) tg.getTag("flag/" + cz + "/" + cy + "/" + cx)).getData());
							c.setArea(cx, cy, cz, ((TagString) tg.getTag("area/" + cz + "/" + cy + "/" + cx)).getData());
							c.setBiome(cx, cy, cz, ((TagInt) tg.getTag("biome/" + cz + "/" + cy + "/" + cx)).getData());
						}
					}
				}
				
				return c;
			} else {
				throw new IOException("Chunk has inconsistant location data.");
			}
		} else {
			throw new IOException("Chunk file is from newer version.");
		}
	}
	
	public void stop() {
		stop = true;
	}
}

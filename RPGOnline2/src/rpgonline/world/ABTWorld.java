package rpgonline.world;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.util.Log;

import rpgonline.abt.Tag;
import rpgonline.abt.TagBoolean;
import rpgonline.abt.TagDoc;
import rpgonline.abt.TagGroup;
import rpgonline.abt.TagInt;
import rpgonline.abt.TagLong;
import rpgonline.abt.TagString;
import rpgonline.abt.TagVector2;
import rpgonline.abt.TagVector4;
import rpgonline.entity.Entity;
import rpgonline.entity.EntityManager;
import rpgonline.tile.Tile;
import rpgonline.world.chunk.CacheEntry;
import rpgonline.world.chunk.Chunk;
import rpgonline.world.chunk.ChunkWorld;

public class ABTWorld extends ChunkWorld {
	protected File folder;
	protected int format;
	public ABTWorld(File folder, Map<String, Tile> registry, EntityManager em, boolean server) throws IOException {
		super(registry);
		this.folder = folder;
		folder.mkdirs();
		
		if (new File(folder, "map.abt").exists()) {
			TagDoc d = TagDoc.read(new GZIPInputStream(new BufferedInputStream(new FileInputStream(new File(folder, "map.abt")))), "map");
			
			format = ((TagInt) d.getTags().getTag("version")).getData();
			
			if (format == 0) {
				TagGroup lights = (TagGroup) d.getTags().getTag("lights");
				
				for(Tag t : lights.getTags()) {
					TagGroup g = (TagGroup) t;
					
					TagVector4 c = (TagVector4) g.getTag("color");
					
					float lr = (float) c.get(0);
					float lg = (float) c.get(1);
					float lb = (float) c.get(2);
					
					TagVector2 pos = (TagVector2) g.getTag("pos");
					
					double x = pos.get(0);
					double y = pos.get(1);
					
					float b = (float) c.get(3);
					
					Color color = new Color(lr, lg, lb);
					
					addLight(new LightSource() {
						private static final long serialVersionUID = -7735632885503935298L;

						@Override
						public double getLX() {
							return x;
						}

						@Override
						public double getLY() {
							return y;
						}

						@Override
						public Color getColor() {
							return color;
						}
						
						@Override
						public float getBrightness() {
							return b;
						}
					});
				}
				
				TagGroup entities = (TagGroup) d.getTags().getTag("entities");
				
				for (Tag t : entities.getTags()) {
					TagGroup g = (TagGroup) t;
					
					getEntities().add(new Entity(em, g, server));
				}
			} else {
				throw new IOException("Unknown version file: " + format);
			}
		}
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
		
		File f = new File(folder, "chunk_" + Long.toHexString(x) + "_" + Long.toHexString(y) + "_" + Long.toHexString(z) + ".abt");
		
		if (f.exists()) {
			Chunk c;
			try {
				c = loadChunk(registry, cx, cy, cz, f);
				
				chunks.add(c);
				cache.add(new CacheEntry(c, System.currentTimeMillis()));
				last_chunk = c;
				
				return c;
			} catch (IOException | NullPointerException e) {
				Log.error("Error reading chunk from file " + f.getName(), e);
			}
		}

		Chunk chunk = new Chunk(registry, cx, cy, cz);
		chunks.add(chunk);
		cache.add(new CacheEntry(chunk, System.currentTimeMillis()));
		last_chunk = chunk;

		return chunk;
	}
	
	protected Chunk loadChunk(Map<String, Tile> registry, long x, long y, long z, File f) throws FileNotFoundException, IOException {
		TagDoc d = TagDoc.read(new GZIPInputStream(new BufferedInputStream(new FileInputStream(f))), "map_c");
		
		TagGroup tg = d.getTags();
		
		int version = ((TagInt) tg.getTag("version")).getData();
		
		if (version == 0) {
			if (x == ((TagLong) tg.getTag("x")).getData()
					&& y == ((TagLong) tg.getTag("y")).getData()
					&& z == ((TagLong) tg.getTag("z")).getData()) {
				Chunk c = new Chunk(registry, x, y, z);
				
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
	
	@Override
	public void save() {
		TagGroup tg = new TagGroup("map");
		tg.add(new TagInt("version", 0));
		
		TagGroup lights = new TagGroup("lights");
		for(LightSource l : getLights()) {
			TagGroup g = new TagGroup(l.toString());
			
			TagVector4 c = new TagVector4("color", l.getColor().r, l.getColor().g, l.getColor().b, l.getBrightness());
			g.add(c);
			
			TagVector2 pos = new TagVector2("pos", l.getLX(), l.getLY());
			g.add(pos);
			
			lights.add(g);
		}
		
		tg.add(lights);
		
		TagGroup entities = new TagGroup("entities");
		for (Entity e : getEntities()) {
			entities.add(e.toABT(e.toString()));
		}
		
		TagDoc d = new TagDoc("map", tg);
		
		try {
			d.write(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(new File(folder, "map.abt")))));
		} catch (IOException e) {
			Log.error("Error writing map data.", e);
		}
		
		for (Chunk c : chunks) {
			File f = new File(folder, "chunk_" + Long.toHexString(c.getX()) + "_" + Long.toHexString(c.getY()) + "_" + Long.toHexString(c.getZ()) + ".abt");
			TagDoc doc = new TagDoc("map_c", c.save());
			
			try {
				doc.write(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(f))));
			} catch (IOException e) {
				Log.error("Error writing chunk " + c.getX() + " " + c.getY() + " " + c.getZ(), e);
			}
		}
	}
}

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
package io.github.tomaso2468.rpgonline.world;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.abt.TagBoolean;
import io.github.tomaso2468.rpgonline.abt.TagDouble;
import io.github.tomaso2468.rpgonline.abt.TagFloat;
import io.github.tomaso2468.rpgonline.abt.TagGroup;
import io.github.tomaso2468.rpgonline.abt.TagInt;
import io.github.tomaso2468.rpgonline.abt.TagLong;
import io.github.tomaso2468.rpgonline.abt.TagString;
import io.github.tomaso2468.rpgonline.net.Client2D;
import io.github.tomaso2468.rpgonline.net.ServerManager;
import io.github.tomaso2468.rpgonline.tile.Tile;
import io.github.tomaso2468.rpgonline.world.chunk.CacheEntry;
import io.github.tomaso2468.rpgonline.world.chunk.Chunk;
import io.github.tomaso2468.rpgonline.world.chunk.ChunkWorld;

/**
 * A world that stores ABT data received from the server on the client side.
 * @author Tomas
 * 
 * @see io.github.tomaso2468.rpgonline.net.Client#getRequestedChunks()
 */
public class ABTNetWorld extends ChunkWorld {
	/**
	 * Determines if the world should disconnect from the server.
	 */
	private boolean stop = false;
	/**
	 * Constructs a new ABTNetWorld.
	 * @param registry The tile registry for this world.
	 * @param lights The list of lights in this world.
	 * @throws IOException If an error occurs reading world data.
	 */
	public ABTNetWorld(Map<String, Tile> registry, List<TagGroup> lights) throws IOException {
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
			
			addLight(new LightSource(x, y, color, b, false));
		}
		
		new Thread(toString()) {
			public void run() {
				while (!stop) {
					List<TagGroup> requested = new ArrayList<TagGroup>(((Client2D) ServerManager.getClient()).getRequestedChunks());
					
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
	
	/**
	 * {@inheritDoc}
	 */
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
		
		((Client2D) ServerManager.getClient()).requestChunk(cx, cy, cz);

		Chunk chunk = new Chunk(registry, cx, cy, cz);
		chunks.add(chunk);
		cache.add(new CacheEntry(chunk, System.currentTimeMillis()));
		last_chunk = chunk;

		return chunk;
	}
	
	/**
	 * Loads a chunk from the network.
	 * @param registry The registry of tile.
	 * @param x The X position of the chunk
	 * @param y The Y position of the chunk
	 * @param z The Z position of the chunk
	 * @param tg The tag containing chunk data.
	 * @param c The chunk to load to.
	 * @return A chunk with tile data.
	 * @throws IOException If an error occurs reading world data.
	 */
	protected Chunk loadChunk(Map<String, Tile> registry, long x, long y, long z, TagGroup tg, Chunk c) throws IOException {
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
	
	/**
	 * Stops this world's connection to the server.
	 */
	public void stop() {
		stop = true;
	}
}

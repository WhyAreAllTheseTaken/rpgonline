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
package io.github.tomaso2468.rpgonline.world.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.world.Tile;
import io.github.tomaso2468.rpgonline.world.chunk.Chunk;
import io.github.tomaso2468.rpgonline.world.chunk.ChunkWorld;

/**
 * <p>
 * A world type that stores world data as images.
 * </p>
 * <p>
 * Colors in this class are interpreted including alpha. Any alpha value that
 * isn't {@code 0xFF} with be intepreted as nothing.
 * </p>
 * 
 * @author Tomas
 */
public class WorldFromImage extends ChunkWorld {
	/**
	 * The X offset to offset world data by.
	 */
	private final long ix;
	/**
	 * The Y offset to offset world data by.
	 */
	private final long iy;
	/**
	 * The images for land z=0 data.
	 */
	private final List<ImageCache> imgsLand = new ArrayList<>();
	/**
	 * The images for top z=-1 data.
	 */
	private final List<ImageCache> imgsTop = new ArrayList<>();
	/**
	 * The image for roof z=-2 data.
	 */
	private final List<ImageCache> imgsRoof = new ArrayList<>();
	/**
	 * The images for biome data.
	 */
	private final List<ImageCache> imgsBiome = new ArrayList<>();
	/**
	 * The mapping from image colors to tile IDs.
	 */
	private final Map<Integer, String> mappings;
	/**
	 * The mapping from image colors to biome IDs.
	 */
	private final Map<Integer, Integer> biomes;
	/**
	 * The size of the images used by this world in pixels.
	 */
	private final int size;
	/**
	 * The folder containing image data.
	 */
	private final File f;

	/**
	 * Constructs a new WorldFromImage object.
	 * 
	 * @param registry The tile registry for interpreting tile IDs.
	 * @param ix The X offset to offset world data by.
	 * @param iy The Y offset to offset world data by.
	 * @param mappings The mappings from colors to tile IDs.
	 * @param biomes The mappings from colors to biome IDs.
	 * @param size The size of one image in the world data.
	 * @param f The folder containing world data.
	 */
	public WorldFromImage(Map<String, Tile> registry, long ix, long iy, Map<Integer, String> mappings,
			Map<Integer, Integer> biomes, int size, File f) {
		super(registry);
		this.ix = ix;
		this.iy = iy;
		this.mappings = mappings;
		this.biomes = biomes;
		this.size = size;
		this.f = f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void setTile(long x, long y, long z, Tile tile) {
		if (tile == null) {
			tile = registry.get("air");
		}
		BufferedImage img = null;
		long imgX = x + ix;
		long imgY = y + iy;

		if (z == 0) {
			img = getImageLand(imgX, imgY);
		} else if (z == -1) {
			img = getImageTop(imgX, imgY);
		} else if (z == -2) {
			img = getImageRoof(imgX, imgY);
		}

		img.setRGB(Math.abs((int) (imgX % size)), Math.abs((int) (imgY % size)), getColor(tile.getID()));

		super.setTile(x, y, z, tile);
	}

	/**
	 * Gets the color from a tile ID.
	 * @param id A tile ID.
	 * @return A color code.
	 */
	private int getColor(String id) {
		for (Entry<Integer, String> e : mappings.entrySet()) {
			if (e.getValue().equals(id)) {
				return e.getKey();
			}
		}
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected synchronized Chunk getChunk(long x, long y, long z) {
		Chunk chunk = super.getChunk(x, y, z);

		if (!chunk.getFlag(xToChunk(x), xToChunk(y), 0)) {
			chunk.setFlag(xToChunk(x), xToChunk(y), 0, true);
			long gx = x;
			long gy = y;

			long imgX = gx + ix;
			long imgY = gy + iy;

			int biome_c = getImageBiome(imgX, imgY).getRGB(Math.abs(Math.abs((int) (imgX % size))),
					Math.abs(Math.abs((int) (imgY % size))));

			if ((biome_c & 0xff000000) == 0xff000000) {
				Integer bi = biomes.get(biome_c & 0x00ffffff | 0xff000000);
				if (bi != null) {
					setBiomeID(gx, gy, z, bi);
				}
			} else {
				setBiomeID(gx, gy, z, 0);
			}

			if (z == 0) {
				int land = getImageLand(imgX, imgY).getRGB(Math.abs((int) (imgX % size)),
						Math.abs((int) (imgY % size)));
				if ((land & 0xff000000) == 0xff000000) {
					String ti = mappings.get(land & 0x00ffffff | 0xff000000);
					if (ti != null) {
						setTile(gx, gy, z, registry.get(ti));
					}
				} else {
					setTile(gx, gy, z, registry.get("air"));
				}
			}

			if (z == -1) {
				int land = getImageTop(imgX, imgY).getRGB(Math.abs((int) (imgX % size)), Math.abs((int) (imgY % size)));

				if ((land & 0xff000000) == 0xff000000) {
					String ti = mappings.get(land & 0x00ffffff | 0xff000000);
					if (ti != null) {
						setTile(gx, gy, z, registry.get(ti));
					}
				} else {
					setTile(gx, gy, z, registry.get("air"));
				}
			}

			if (z == -2) {
				int land = getImageRoof(imgX, imgY).getRGB(Math.abs((int) (imgX % size)),
						Math.abs((int) (imgY % size)));

				if ((land & 0xff000000) == 0xff000000) {
					String ti = mappings.get(land & 0x00ffffff | 0xff000000);
					if (ti != null) {
						setTile(gx, gy, z, registry.get(ti));
					}
				} else {
					setTile(gx, gy, z, registry.get("air"));
				}
			}
		}

		return chunk;
	}

	/**
	 * Gets an image of z=0 data.
	 * @param x The X coordinate.
	 * @param y The Y coordinate.
	 * @return A buffered image.
	 */
	public BufferedImage getImageLand(long x, long y) {
		for (ImageCache img : imgsLand) {
			if (img.getX() == x / size && img.getY() == y / size) {
				return img.getImage();
			}
		}

		try {
			BufferedImage img = ImageIO
					.read(new File(new File(f, "z0"), "map_" + (x / size) + "_" + (y / size) + ".png"));
			imgsLand.add(new ImageCache(img, x / size, y / size));
			return img;
		} catch (IOException | NullPointerException | IllegalArgumentException e) {
			Log.warn("Error loading layer 0 chunk data. Most likely because no tiles exist in this chunk.", e);
		}

		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		imgsLand.add(new ImageCache(img, x / size, y / size));

		return img;
	}

	/**
	 * Gets an image of z=-1 data.
	 * @param x The X coordinate.
	 * @param y The Y coordinate.
	 * @return A buffered image.
	 */
	public BufferedImage getImageTop(long x, long y) {
		for (ImageCache img : imgsTop) {
			if (img.getX() == x / size && img.getY() == y / size) {
				return img.getImage();
			}
		}

		try {
			BufferedImage img = ImageIO
					.read(new File(new File(f, "z1"), "map_" + (x / size) + "_" + (y / size) + ".png"));
			imgsTop.add(new ImageCache(img, x / size, y / size));
			return img;
		} catch (IOException | NullPointerException | IllegalArgumentException e) {
			Log.warn("Error loading layer -1 chunk data. Most likely because no tiles exist in this chunk.", e);
		}

		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		imgsTop.add(new ImageCache(img, x / size, y / size));

		return img;
	}

	/**
	 * Gets an image of z=-2 data.
	 * @param x The X coordinate.
	 * @param y The Y coordinate.
	 * @return A buffered image.
	 */
	public BufferedImage getImageRoof(long x, long y) {
		for (ImageCache img : imgsRoof) {
			if (img.getX() == x / size && img.getY() == y / size) {
				return img.getImage();
			}
		}

		try {
			BufferedImage img = ImageIO
					.read(new File(new File(f, "z2"), "map_" + (x / size) + "_" + (y / size) + ".png"));
			imgsRoof.add(new ImageCache(img, x / size, y / size));
			return img;
		} catch (IOException | NullPointerException | IllegalArgumentException e) {
			Log.warn("Error loading layer -2 chunk data. Most likely because no tiles exist in this chunk.", e);
		}

		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		imgsRoof.add(new ImageCache(img, x / size, y / size));

		return img;
	}

	/**
	 * Gets an image of biome data.
	 * @param x The X coordinate.
	 * @param y The Y coordinate.
	 * @return A buffered image.
	 */
	public BufferedImage getImageBiome(long x, long y) {
		for (ImageCache img : imgsBiome) {
			if (img.getX() == x / size && img.getY() == y / size) {
				return img.getImage();
			}
		}

		try {
			BufferedImage img = ImageIO
					.read(new File(new File(f, "biome"), "map_" + (x / size) + "_" + (y / size) + ".png"));
			imgsBiome.add(new ImageCache(img, x / size, y / size));
			return img;
		} catch (IOException | NullPointerException | IllegalArgumentException e) {
			Log.warn("Error loading layer biome chunk data. Most likely because no tiles exist in this chunk.", e);
		}

		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		imgsBiome.add(new ImageCache(img, x / size, y / size));

		return img;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void save() throws IOException {
		for (ImageCache i : imgsLand) {
			File f = new File(new File(this.f, "z0"), "map_" + i.getX() + "_" + i.getY() + ".png").getAbsoluteFile();

			Log.debug("Saving " + f.getAbsolutePath());

			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}

			ImageIO.write(i.getImage(), "png", f);
		}
		for (ImageCache i : imgsTop) {
			File f = new File(new File(this.f, "z1"), "map_" + i.getX() + "_" + i.getY() + ".png").getAbsoluteFile();

			Log.debug("Saving " + f.getAbsolutePath());

			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}

			ImageIO.write(i.getImage(), "png", f);
		}
		for (ImageCache i : imgsRoof) {
			File f = new File(new File(this.f, "z2"), "map_" + i.getX() + "_" + i.getY() + ".png").getAbsoluteFile();

			Log.debug("Saving " + f.getAbsolutePath());

			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}

			ImageIO.write(i.getImage(), "png", f);
		}
	}

}

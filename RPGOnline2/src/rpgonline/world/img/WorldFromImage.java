package rpgonline.world.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.newdawn.slick.util.Log;

import rpgonline.tile.Tile;
import rpgonline.world.chunk.Chunk;
import rpgonline.world.chunk.ChunkWorld;

public class WorldFromImage extends ChunkWorld {
	private final double ix;
	private final double iy;
	private final List<ImageCache> imgsLand = new ArrayList<>();
	private final List<ImageCache> imgsTop = new ArrayList<>();
	private final List<ImageCache> imgsRoof = new ArrayList<>();
	private final List<ImageCache> imgsBiome = new ArrayList<>();
	private final Map<Integer, String> mappings;
	private final Map<Integer, Integer> biomes;
	private final int size;
	private final File f;
	
	public WorldFromImage(Map<String, Tile> registry, double ix, double iy, Map<Integer, String> mappings,
			Map<Integer, Integer> biomes, int size, File f) {
		super(registry);
		this.ix = ix;
		this.iy = iy;
		this.mappings = mappings;
		this.biomes = biomes;
		this.size = size;
		this.f = f;
	}

	@Override
	protected synchronized Chunk getChunk(long x, long y, long z) {
		Chunk chunk = super.getChunk(x, y, z);

		if (!chunk.getFlag(xToChunk(x), xToChunk(y), 0)) {
			chunk.setFlag(xToChunk(x), xToChunk(y), 0, true);
			long gx = x;
			long gy = y;

			long imgX = (long) (gx + ix);
			long imgY = (long) (gy + iy);
			
			int biome_c = getImageBiome(imgX, imgY).getRGB((int) (imgX % size), (int) (imgY % size));
			
			if ((biome_c & 0xff000000) == 0xff000000) {
				Integer bi = biomes.get(biome_c & 0x00ffffff);
				if (bi != null) {
					setBiomeID(gx, gy, z, bi);
				}
			} else {
				setBiomeID(gx, gy, z, 0);
			}

			if (z == 0) {
				int land = getImageLand(imgX, imgY).getRGB((int) (imgX % size), (int) (imgY % size));
				
				if ((land & 0xff000000) == 0xff000000) {
					String ti = mappings.get(land & 0x00ffffff);
					if (ti != null) {
						setTile(gx, gy, z, registry.get(ti));
					}
				} else {
					setTile(gx, gy, z, registry.get("air"));
				}
			}
			
			if (z == -1) {
				int land = getImageTop(imgX, imgY).getRGB((int) (imgX % size), (int) (imgY % size));
				
				if ((land & 0xff000000) == 0xff000000) {
					String ti = mappings.get(land & 0x00ffffff);
					if (ti != null) {
						setTile(gx, gy, z, registry.get(ti));
					}
				} else {
					setTile(gx, gy, z, registry.get("air"));
				}
			}
			
			if (z == -2) {
				int land = getImageRoof(imgX, imgY).getRGB((int) (imgX % size), (int) (imgY % size));
				
				if ((land & 0xff000000) == 0xff000000) {
					String ti = mappings.get(land & 0x00ffffff);
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

	public BufferedImage getImageLand(long x, long y) {
		for (ImageCache img : imgsLand) {
			if (img.getX() == x / size && img.getY() == y / size) {
				return img.getImage();
			}
		}

		try {
			BufferedImage img = ImageIO.read(new File(new File(f, "z0"), "map_" + (x / size) + "_" + (y / size) + ".png"));
			imgsLand.add(new ImageCache(img, x / size, y / size));
			return img;
		} catch (IOException | NullPointerException | IllegalArgumentException e) {
			Log.warn("Error loading layer 0 chunk data. Most likely because no tiles exist in this chunk.", e);
		}

		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		imgsLand.add(new ImageCache(img, x / size, y / size));

		return img;
	}

	public BufferedImage getImageTop(long x, long y) {
		for (ImageCache img : imgsTop) {
			if (img.getX() == x / size && img.getY() == y / size) {
				return img.getImage();
			}
		}

		try {
			BufferedImage img = ImageIO.read(new File(new File(f, "z1"), "map_" + (x / size) + "_" + (y / size) + ".png"));
			imgsTop.add(new ImageCache(img, x / size, y / size));
			return img;
		} catch (IOException | NullPointerException | IllegalArgumentException e) {
			Log.warn("Error loading layer -1 chunk data. Most likely because no tiles exist in this chunk.", e);
		}

		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		imgsTop.add(new ImageCache(img, x / size, y / size));

		return img;
	}

	public BufferedImage getImageRoof(long x, long y) {
		for (ImageCache img : imgsRoof) {
			if (img.getX() == x / size && img.getY() == y / size) {
				return img.getImage();
			}
		}

		try {
			BufferedImage img = ImageIO.read(new File(new File(f, "z2"), "map_" + (x / size) + "_" + (y / size) + ".png"));
			imgsRoof.add(new ImageCache(img, x / size, y / size));
			return img;
		} catch (IOException | NullPointerException | IllegalArgumentException e) {
			Log.warn("Error loading layer -2 chunk data. Most likely because no tiles exist in this chunk.", e);
		}

		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		imgsRoof.add(new ImageCache(img, x / size, y / size));

		return img;
	}
	
	public BufferedImage getImageBiome(long x, long y) {
		for (ImageCache img : imgsBiome) {
			if (img.getX() == x / size && img.getY() == y / size) {
				return img.getImage();
			}
		}

		try {
			BufferedImage img = ImageIO.read(new File(new File(f, "biome"), "map_" + (x / size) + "_" + (y / size) + ".png"));
			imgsBiome.add(new ImageCache(img, x / size, y / size));
			return img;
		} catch (IOException | NullPointerException | IllegalArgumentException e) {
			Log.warn("Error loading layer biome chunk data. Most likely because no tiles exist in this chunk.", e);
		}

		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		imgsBiome.add(new ImageCache(img, x / size, y / size));

		return img;
	}

}

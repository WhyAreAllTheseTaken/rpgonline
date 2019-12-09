package io.github.tomaso2468.rpgonline;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.commons.math3.util.FastMath;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;

/**
 * An implementation of a texture map.
 * @author Tomaso2468
 *
 */
public class TextureMapImpl implements TextureMap {
	/**
	 * A list of textures.
	 */
	private List<Image> texturesFast = new ArrayList<>();
	/**
	 * A map of texture IDs to textures.
	 */
	private Map<String, Image> textures = new HashMap<>();
	/**
	 * A map of textures that should be added to the atlas.
	 */
	private Map<String, BufferedImage> texturesMapped = new HashMap<>();
	/**
	 * A map of textures to their sprite sheet.
	 */
	private Map<Image, Image> sheets = new HashMap<>();

	/**
	 * The renderer.
	 */
	private Renderer renderer;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTexture(String s, Image img) {
		textures.put(s.intern(), img);
		texturesFast.add(img);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadTexture(String s, URL loc) throws RenderException {
		Log.debug("Loading texture " + s + " from " + loc);

		Image img;
		try {
			img = new Image(renderer, renderer.getPNG(loc));
		} catch (RenderException | IOException e) {
			throw new RenderException("Error loading texture.", e);
		}

		// Disabled interpolation.
		img.setFilter(RPGConfig.getFilterMode());

		// Add the texture.
		addTexture(s, img);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadMappedTexture(String s, URL loc) throws RenderException {
		// Load the texture normally if mapping is disabled.
		if (!RPGConfig.isMapped()) {
			loadTexture(s, loc);
			return;
		}

		Log.debug("Loading mapped texture " + s + " from " + loc);

		// Load the texture
		BufferedImage img;
		try {
			img = ImageIO.read(loc);
		} catch (IOException e) {
			throw new RenderException(e.toString());
		}

		// Add the texture to the texture map.
		addMappedTexture(s, img);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMappedTexture(String s, BufferedImage img) throws RenderException {
		// Store the texture in a file then reload if mapping is disabled.
		if (!RPGConfig.isMapped()) {
			try {
				// XXX Is there anyway to directly load the texture without a temporary file.
				File f = File.createTempFile("rpgonline_unmapped_", ".png");
				ImageIO.write(img, "PNG", f);

				loadTexture(s, f.toURI().toURL());

				// Delete the temporary file (on some system this may require a system reboot to
				// take effect but should not be too much of a concern.
				f.deleteOnExit();
			} catch (IOException e) {
				throw new RenderException(e.toString());
			}
			return;
		}

		// Add the texture
		texturesMapped.put(s, img);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSpriteMap(String s, Image img, int tw, int th) {
		SpriteSheet map = new SpriteSheet(renderer, img, tw, th);
		int id = 0;
		Log.debug("Map size " + map.getHorizontalCount() + " x " + map.getVerticalCount());
		for (int y = 0; y < map.getVerticalCount(); y++) {
			for (int x = 0; x < map.getHorizontalCount(); x++) {
				Log.debug("Loading sprite map part " + s + "." + id + " @ " + x + " " + y);
				addTexture(s + "." + id, map.getSprite(x, y));
				id += 1;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadSpriteMap(String s, URL loc, int tw, int th) throws RenderException {
		Log.debug("Loading sprite map texture " + s + " from " + loc);
		Image img;
		try {
			img = new Image(renderer, renderer.getPNG(loc));
		} catch (RenderException | IOException e) {
			throw new RenderException("Error loading texture.", e);
		}
		img.setFilter(RPGConfig.getFilterMode());

		addSpriteMap(s, img, tw, th);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loadSpriteMapMapped(String s, URL loc, int tw, int th) throws RenderException {
		Log.debug("Loading sprite map texture " + s + " from " + loc);
		BufferedImage img;
		try {
			img = ImageIO.read(loc);

			addSpriteMapMapped(s, img, tw, th);
		} catch (IOException e) {
			throw new RenderException(e.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSpriteMapMapped(String s, BufferedImage img, int tw, int th) throws RenderException {
		int id = 0;
		Log.debug("Map size " + img.getWidth() / tw + " x " + img.getHeight() / th);
		for (int y = 0; y < img.getHeight() / th; y++) {
			for (int x = 0; x < img.getWidth() / tw; x++) {
				Log.debug("Loading sprite map part " + s + "." + id + " @ " + x + " " + y);
				addMappedTexture(s + "." + id, img.getSubimage(x * tw, y * th, tw, th));
				id += 1;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTextureIndex(String s) {
		return texturesFast.indexOf(textures.get(s.intern()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getTexture(int i) {
		if (i < 0) {
			return null;
		}
		return texturesFast.get(i);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getTexture(String s) {
		return textures.get(s.intern());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void genTextureMap(int sw, int sh) throws RenderException {
		if (!RPGConfig.isMapped()) {
			return;
		}
		Log.debug("Preparing to map textures of size " + sw + " x " + sh);

		// Get largest texture size.
		int mts = GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);

		// Limit to the config size for auto-generated sprite maps to not use all
		// available RAM.
		mts = FastMath.min(mts, RPGConfig.getAutoSpriteMapSize());

		if (sw > mts) {
			throw new IllegalArgumentException(
					"Sprite width is larger than maximum size specified by GL_MAX_TEXTURE_SIZE and RPGConfig.getAutoSpriteMapSize(): "
							+ sw + "/" + mts);
		}
		if (sh > mts) {
			throw new IllegalArgumentException(
					"Sprite height is larger than maximum size specified by GL_MAX_TEXTURE_SIZE and RPGConfig.getAutoSpriteMapSize(): "
							+ sh + "/" + mts);
		}

		// Find largest multiple of sw and sh to insure no unneeded space is allocated.
		int w = largestMultiple(sw, mts);
		int h = largestMultiple(sh, mts);

		// Compute number of sprites in grid.
		int wc = w / sw;
		int hc = h / sh;

		Log.debug("Grabbing textures to map.");

		// List of textures to put in map.
		List<Entry<String, BufferedImage>> toMap = new ArrayList<>();

		for (Entry<String, BufferedImage> e : texturesMapped.entrySet()) {
			if (e.getValue().getWidth() == sw && e.getValue().getHeight() == sh) {
				toMap.add(e);
			}
		}

		// Remove mapped textures
		for (Entry<String, BufferedImage> t : toMap) {
			texturesMapped.remove(t.getKey());
		}

		Log.info("Mapping textures of size " + sw + " x " + sh);

		Map<String, Integer> textureX = new HashMap<>();
		Map<String, Integer> textureY = new HashMap<>();
		Map<BufferedImage, List<String>> textureImg = new HashMap<>();
		Map<BufferedImage, File> textureFile = new HashMap<>();

		try {
			BufferedImage img = null;
			int tx = 0;
			int ty = 0;
			Graphics2D g = null;
			// Loop through and map all textures.
			for (Entry<String, BufferedImage> t : toMap) {
				Log.debug("Mapping " + t.getKey());

				// Go onto next row if this row is full.
				if (tx >= wc) {
					tx = 0;
					ty += 1;
				}
				/**
				 * go to the next image when this image is full.
				 */
				if (ty >= hc) {
					File f = File.createTempFile("rpgonline_tilesheet_", ".png");
					ImageIO.write(img, "PNG", f);
					textureFile.put(img, f);

					img = null;
					tx = 0;
					ty = 0;
				}
				// Create a new image when needed.
				if (img == null) {
					img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
					textureImg.put(img, new ArrayList<String>(64));
					g = img.createGraphics();
				}

				// Add textures
				Log.debug("Placing " + t.getKey() + " @ " + tx + " " + ty);

				textureX.put(t.getKey(), tx);
				textureY.put(t.getKey(), ty);
				textureImg.get(img).add(t.getKey());

				g.drawImage(t.getValue(), tx * sw, ty * sh, null);

				tx += 1;
			}

			// Write final image.
			if (img != null) {
				File f = File.createTempFile("rpgonline_tilesheet_", ".png");
				ImageIO.write(img, "PNG", f);
				textureFile.put(img, f);
			}

			Log.debug("Storing data in temporary file");

			// Load textures.
			int i = 0;
			for (Entry<BufferedImage, List<String>> e : textureImg.entrySet()) {
				File f = textureFile.get(e.getKey());

				Image img2;
				img2 = new Image(renderer, renderer.getPNG(f.toURI().toURL()));
				img2.setFilter(RPGConfig.getFilterMode());

				SpriteSheet ss = new SpriteSheet(renderer, img2, sw, sh);

				for (String s : e.getValue()) {
					addTexture(s, ss.getSubImage(textureX.get(s), textureY.get(s)));

					sheets.put(ss.getSubImage(textureX.get(s), textureY.get(s)), ss);
				}

				ss.write("map" + sw + "-" + sh + "-" + i + ".png");

				i += 1;
			}

			Log.debug("Mapping complete");
		} catch (IOException e) {
			Log.error(e);
			throw new RenderException(e.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateAllMaps() throws RenderException {
		Log.info("Generating all texture maps.");
		while (!texturesMapped.isEmpty()) {
			Entry<String, BufferedImage> e = texturesMapped.entrySet().iterator().next();
			genTextureMap(e.getValue().getWidth(), e.getValue().getHeight());
		}
		Log.info("Map generation complete.");
	}

	/**
	 * Finds the largest multiple of a given number given a maximum (inclusive).
	 * 
	 * @param x   A number to multiply.
	 * @param max The maximum number to go to (inclusive).
	 * @return A multiple of x.
	 * @throws IllegalArgumentException if x or max is less than or equal to 0.
	 */
	private int largestMultiple(int x, int max) {
		if (x <= 0 || max <= 0) {
			throw new IllegalArgumentException("x and max must be greater than 0");
		}
		int m = 0;
		int i = 1;

		while (m <= max) {
			m = x * i;
			i += 1;
		}

		return (i - 2) * x;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getSheet(Image img) {
		Image sheet = sheets.get(img);
		if (sheet == null) {
			sheet = img;
		}
		return sheet;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}
}

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
package io.github.tomaso2468.rpgonline;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
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
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;

/**
 * <p>
 * A class for storing, managing and mapping textures.
 * </p>
 * <p>Textures are loaded using the filter method described by {@link io.github.tomaso2468.rpgonline.RPGConfig#getFilterMode()}</p>
 * <p>
 * Textures can be stored in 2 ways: by a string id or by index. When a texture
 * is added it is automatically put into a map with a key based on its string ID
 * and placed into a list at the next available index. The string IDs are
 * automatically interned to reduce overhead. A string ID should be unique as
 * using the same string ID will replace the texture in the map but will not
 * remove it from the fast access list. If the same image is added with
 * different string IDs it will be added mapped to both IDs but will have the
 * same texture ID. It is better to use texture indexes as the performance is
 * slightly higher due to the speed of array indexing as opposed to map lookup.
 * </p>
 * <p>
 * Textures can also be added in a way that indicates that they should be mapped
 * into a larger image (texture atlasing). This moves a texture into the same
 * sprite map. A mapped texture cannot be used until {@code generateAllMaps()}
 * has been called. Once a texture has been added to a map it will be added
 * normally but will also be mapped to a map of sprite sheets. If texture
 * mapping is disabled then textures are loaded normally. However, to make
 * texture mapping more reliable, mapped textures are loaded first using Java2D
 * and {@code BufferedImage} stored in a temporary file then loaded with
 * slick2d. If mapping is disabled textures are still loaded with Java2D stored
 * in a temporary file and then loaded with slick2D but one file is needed per
 * texture rather than per map. This is not needed if textures are directly
 * loaded from a URL. Mapping is controlled with {@link RPGConfig#isMapped()}.
 * The size of the sprite atlases are defined by
 * {@link RPGConfig#getAutoSpriteMapSize()} but will not exceed the maximum
 * texture size of the graphics card.
 * </p>
 * <p>
 * Sprite maps can also be loaded with this class and follow the pattern
 * {@code texture.index} (with indexes starting at 0). They can also be mapped.
 * </p>
 * 
 * @author Tomaso2468
 */
public class TextureMap {
	/**
	 * Prevent instantiation.
	 */
	private TextureMap() {

	}

	/**
	 * A list of textures.
	 */
	private static List<Image> texturesFast = new ArrayList<>();
	/**
	 * A map of texture IDs to textures.
	 */
	private static Map<String, Image> textures = new HashMap<>();
	/**
	 * A map of textures that should be added to the atlas.
	 */
	private static Map<String, BufferedImage> texturesMapped = new HashMap<>();
	/**
	 * A map of textures to their sprite sheet.
	 */
	private static Map<Image, Image> sheets = new HashMap<>();

	/**
	 * Adds a texture to the texture map.
	 * 
	 * @param s   The texture ID.
	 * @param img The texture to add.
	 */
	public static void addTexture(String s, Image img) {
		textures.put(s.intern(), img);
		texturesFast.add(img);
	}

	/**
	 * Loads and adds a texture to the texture map.
	 * 
	 * @param s   The texture ID.
	 * @param loc The location of the texture file.
	 * @throws SlickException If an error occurs loading a texture.
	 */
	public static void loadTexture(String s, URL loc) throws SlickException {
		Log.debug("Loading texture " + s + " from " + loc);

		// Load texture.
		Texture tex;
		try {
			tex = TextureLoader.getTexture("PNG", new BufferedInputStream(loc.openStream()));
		} catch (IOException e) {
			throw new SlickException(e.toString());
		}
		Image img = new Image(tex);

		// Disabled interpolation.
		img.setFilter(RPGConfig.getFilterMode());

		// Add the texture.
		addTexture(s, img);
	}

	/**
	 * <p>
	 * Loads a texture and adds it to the to-map list.
	 * </p>
	 * <p>
	 * This method will automatically delegate to {@code loadTexture()} if texture
	 * mapping is disabled.
	 * </p>
	 * 
	 * @param s   The texture ID.
	 * @param loc The location of the texture file.
	 * @throws SlickException If an error occurs loading a texture.
	 */
	public static void loadMappedTexture(String s, URL loc) throws SlickException {
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
			throw new SlickException(e.toString());
		}

		// Add the texture to the texture map.
		addMappedTexture(s, img);
	}

	/**
	 * <p>
	 * Adds a texture to the to-map list.
	 * </p>
	 * <p>
	 * This method has some IO overhead if texture mapping is disabled.
	 * </p>
	 * 
	 * @param s   The texture ID.
	 * @param img The image to map.
	 * @throws SlickException If an error occurs adding the texture.
	 */
	public static void addMappedTexture(String s, BufferedImage img) throws SlickException {
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
				throw new SlickException(e.toString());
			}
			return;
		}

		// Add the texture
		texturesMapped.put(s, img);
	}

	/**
	 * <p>
	 * Adds an image as a sprite map.
	 * </p>
	 * <p>
	 * The textures are bounds as {@code s + "." + i} where i is the index in the
	 * sprite map starting at the top left corner and travelling horizontally.
	 * <p>
	 * 
	 * @param s   The prefix of the texture ID to bind to.
	 * @param img The image holding the sprite map.
	 * @param tw  The width of one sprite.
	 * @param th  The height of one sprite.
	 */
	public static void addSpriteMap(String s, Image img, int tw, int th) {
		SpriteSheet map = new SpriteSheet(img, tw, th);
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
	 * <p>
	 * Loads a texture then adds it as a sprite map.
	 * </p>
	 * <p>
	 * The textures are bounds as {@code s + "." + i} where i is the index in the
	 * sprite map starting at the top left corner and travelling horizontally.
	 * <p>
	 * 
	 * @param s   The prefix of the texture ID to bind to.
	 * @param loc A URL pointing to a .png image.
	 * @param tw  The width of one sprite.
	 * @param th  The height of one sprite.
	 * @throws SlickException if an error occurs loading the sprite map.
	 */
	public static void loadSpriteMap(String s, URL loc, int tw, int th) throws SlickException {
		Log.debug("Loading sprite map texture " + s + " from " + loc);
		Texture tex;
		try {
			tex = TextureLoader.getTexture("PNG", new BufferedInputStream(loc.openStream()));
		} catch (IOException e) {
			Log.error(e);
			throw new SlickException(e.toString());
		}
		Image img = new Image(tex);
		img.setFilter(RPGConfig.getFilterMode());

		addSpriteMap(s, img, tw, th);
	}

	/**
	 * <p>
	 * Loads a texture then adds it as a sprite map to the to-map list.
	 * </p>
	 * <p>
	 * The textures are bounds as {@code s + "." + i} where i is the index in the
	 * sprite map starting at the top left corner and travelling horizontally.
	 * <p>
	 * 
	 * @param s   The prefix of the texture ID to bind to.
	 * @param loc A URL pointing to a .png image.
	 * @param tw  The width of one sprite.
	 * @param th  The height of one sprite.
	 * @throws SlickException if an error occurs loading the sprite map.
	 */
	public static void loadSpriteMapMapped(String s, URL loc, int tw, int th) throws SlickException {
		Log.debug("Loading sprite map texture " + s + " from " + loc);
		BufferedImage img;
		try {
			img = ImageIO.read(loc);

			addSpriteMapMapped(s, img, tw, th);
		} catch (IOException e) {
			throw new SlickException(e.toString());
		}
	}

	/**
	 * <p>
	 * Adds an image as a sprite map and adds it to the to-map list.
	 * </p>
	 * <p>
	 * The textures are bounds as {@code s + "." + i} where i is the index in the
	 * sprite map starting at the top left corner and travelling horizontally.
	 * <p>
	 * 
	 * @param s   The prefix of the texture ID to bind to.
	 * @param img The image holding the sprite map.
	 * @param tw  The width of one sprite.
	 * @param th  The height of one sprite.
	 */
	public static void addSpriteMapMapped(String s, BufferedImage img, int tw, int th) throws SlickException {
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
	 * Gets the texture index of the specified texture.
	 * 
	 * @param s The texture ID.
	 * @return A texture index.
	 */
	public static int getTextureIndex(String s) {
		return texturesFast.indexOf(textures.get(s.intern()));
	}

	/**
	 * Gets a texture by its index.
	 * @param i The texture index.
	 * @return A texture.
	 */
	public static Image getTexture(int i) {
		if (i < 0) {
			return null;
		}
		return texturesFast.get(i);
	}

	/**
	 * Gets a texture by its ID.
	 * @param s The texture ID.
	 * @return A texture.
	 */
	public static Image getTexture(String s) {
		return textures.get(s.intern());
	}

	/**
	 * Generates a texture map of the specified size. It is better to use {@link #generateAllMaps()} as it will automatically load all texture maps. This method will do nothing if mapping is disabled.
	 * @param sw The sprite width.
	 * @param sh The sprite height.
	 * @throws SlickException If an error occurs mapping textures.
	 */
	public static void genTextureMap(int sw, int sh) throws SlickException {
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

				Texture tex;
				tex = TextureLoader.getTexture("PNG", new BufferedInputStream(f.toURI().toURL().openStream()));
				Image img2 = new Image(tex);
				img2.setFilter(RPGConfig.getFilterMode());

				SpriteSheet ss = new SpriteSheet(img2, sw, sh);

				for (String s : e.getValue()) {
					addTexture(s, ss.getSubImage(textureX.get(s), textureY.get(s)));

					sheets.put(ss.getSubImage(textureX.get(s), textureY.get(s)), ss);
				}

				ImageOut.write(ss, "map" + sw + "-" + sh + "-" + i + ".png");

				i += 1;
			}

			Log.debug("Mapping complete");
		} catch (IOException e) {
			Log.error(e);
			throw new SlickException(e.toString());
		}
	}

	/**
	 * Generates all texture maps using {@link #genTextureMap(int, int)}
	 * @throws SlickException If an error occurs mapping textures.
	 */
	public static void generateAllMaps() throws SlickException {
		Log.info("Generating all texture maps.");
		while (!texturesMapped.isEmpty()) {
			Entry<String, BufferedImage> e = texturesMapped.entrySet().iterator().next();
			genTextureMap(e.getValue().getWidth(), e.getValue().getHeight());
		}
		Log.info("Map generation complete.");
	}

	/**
	 * Finds the largest multiple of a given number given a maximum (inclusive).
	 * @param x A number to multiply.
	 * @param max The maximum number to go to (inclusive).
	 * @return A multiple of x.
	 * @throws IllegalArgumentException if x or max is less than or equal to 0.
	 */
	private static int largestMultiple(int x, int max) {
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
	 * Gets the associated sprite sheet of this image.
	 * @param img The texture to get the sprite sheet of.
	 * @return A sprite sheet or {@code img} if no sprite sheet could be found.
	 */
	public static Image getSheet(Image img) {
		Image sheet = sheets.get(img);
		if (sheet == null) {
			sheet = img;
		}
		return sheet;
	}
}

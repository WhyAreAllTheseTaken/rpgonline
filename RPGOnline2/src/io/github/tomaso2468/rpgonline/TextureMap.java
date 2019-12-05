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

import java.awt.image.BufferedImage;
import java.net.URL;

import org.newdawn.slick.SlickException;

import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;

/**
 * <p>
 * A class for storing, managing and mapping textures.
 * </p>
 * <p>
 * Textures are loaded using the filter method described by
 * {@link io.github.tomaso2468.rpgonline.RPGConfig#getFilterMode()}
 * </p>
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
public interface TextureMap {

	/**
	 * Adds a texture to the texture map.
	 * 
	 * @param s   The texture ID.
	 * @param img The texture to add.
	 */
	public void addTexture(String s, Image img) throws RenderException;

	/**
	 * Loads and adds a texture to the texture map.
	 * 
	 * @param s   The texture ID.
	 * @param loc The location of the texture file.
	 * @throws RenderException
	 */
	public void loadTexture(String s, URL loc) throws RenderException;

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
	public void loadMappedTexture(String s, URL loc) throws RenderException;

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
	public void addMappedTexture(String s, BufferedImage img) throws RenderException;

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
	public void addSpriteMap(String s, Image img, int tw, int th) throws RenderException;

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
	public void loadSpriteMap(String s, URL loc, int tw, int th) throws RenderException;

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
	public void loadSpriteMapMapped(String s, URL loc, int tw, int th) throws RenderException;

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
	public void addSpriteMapMapped(String s, BufferedImage img, int tw, int th) throws RenderException;

	/**
	 * Gets the texture index of the specified texture.
	 * 
	 * @param s The texture ID.
	 * @return A texture index.
	 */
	public int getTextureIndex(String s);

	/**
	 * Gets a texture by its index.
	 * 
	 * @param i The texture index.
	 * @return A texture.
	 */
	public Image getTexture(int i);

	/**
	 * Gets a texture by its ID.
	 * 
	 * @param s The texture ID.
	 * @return A texture.
	 */
	public Image getTexture(String s);

	/**
	 * Generates a texture map of the specified size. It is better to use
	 * {@link #generateAllMaps()} as it will automatically load all texture maps.
	 * This method will do nothing if mapping is disabled.
	 * 
	 * @param sw The sprite width.
	 * @param sh The sprite height.
	 * @throws SlickException If an error occurs mapping textures.
	 */
	public void genTextureMap(int sw, int sh) throws RenderException;

	/**
	 * Generates all texture maps using {@link #genTextureMap(int, int)}
	 * 
	 * @throws SlickException If an error occurs mapping textures.
	 */
	public void generateAllMaps() throws RenderException;

	/**
	 * Gets the associated sprite sheet of this image.
	 * 
	 * @param img The texture to get the sprite sheet of.
	 * @return A sprite sheet or {@code img} if no sprite sheet could be found.
	 */
	public Image getSheet(Image img);

	/**
	 * Sets the renderer for this TextureMap.
	 * @param renderer A renderer object.
	 */
	public void setRenderer(Renderer renderer);
}

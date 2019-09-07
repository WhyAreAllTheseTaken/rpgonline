package io.github.tomaso2468.rpgonline.bullet;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A interface representing a bullet.
 * 
 * @author Tomas
 * @see io.github.tomaso2468.rpgonline.bullet.BulletState
 */
public interface Bullet {
	/**
	 * Gets the X position of the bullet.
	 * 
	 * @return A finite float value.
	 */
	public float getX();

	/**
	 * Gets the Y position of the bullet.
	 * 
	 * @return A finite float value.
	 */
	public float getY();

	/**
	 * Updates the bullet.
	 * 
	 * @param container A game container.
	 * @param game      The current game.
	 * @param delf      The amount of time since the last frame measured in seconds.
	 * @param px        The current X position of the player.
	 * @param py        The current Y position of the player.
	 * @param xv        The X velocity of the player.
	 * @param yv        The Y velocity of the player.
	 * @param state     The current bullet state.
	 * @param bullets   A list of all bullets currently in the state.
	 */
	public void update(GameContainer container, StateBasedGame game, float delf, float px, float py, float xv, float yv,
			BulletState state, List<Bullet> bullets);

	/**
	 * <p>
	 * If the bullet uses a custom rendering method.
	 * </p>
	 * <p>
	 * For performance, if the bullet can be rendered using only <a href=
	 * "http://slick.ninjacave.com/javadoc/org/newdawn/slick/Image.html#drawEmbedded(float,%20float,%20float,%20float)">drawEmbedded(float,
	 * float, float, float)</a>, it should be rendered as a combined bullet instead.
	 * </p>
	 * 
	 * @return {@code true} if a custom rendering method is being used,
	 *         {@code false} otherwise.
	 * @see #render(float, float, float, float, BulletState, Graphics, float, float)
	 * @see #isCombined()
	 * @see #renderEmbedded(float, float, float, float, BulletState, Graphics,
	 *      Image, float, float)
	 */
	public default boolean isCustom() {
		return false;
	}

	/**
	 * Gets the texture of this bullet.
	 * 
	 * @return A texture ID.
	 * @see io.github.tomaso2468.rpgonline.texture.TextureMap#getTexture(int)
	 * @see io.github.tomaso2468.rpgonline.texture.TextureMap#getTextureIndex(String)
	 */
	public int getTexture();

	/**
	 * <p>
	 * Renders a custom bullet. When this method is called no texture is currently
	 * bound.
	 * </p>
	 * <p>
	 * For performance, if the bullet can be rendered using only <a href=
	 * "http://slick.ninjacave.com/javadoc/org/newdawn/slick/Image.html#drawEmbedded(float,%20float,%20float,%20float)">drawEmbedded(float,
	 * float, float, float)</a>, it should be rendered as a combined bullet instead.
	 * </p>
	 * 
	 * @param px    The player X position.
	 * @param py    The player Y position.
	 * @param xv    The player X velocity.
	 * @param yv    The player Y velocity.
	 * @param state The current bullet state.
	 * @param g     The current graphics context.
	 * @param sx    The X position of the bullet on the screen.
	 * @param sy    The Y position of the bullet on the screen.
	 * 
	 * @see #isCustom()
	 * @see #isCombined()
	 * @see #renderEmbedded(float, float, float, float, BulletState, Graphics,
	 *      Image, float, float)
	 */
	public default void render(float px, float py, float xv, float yv, BulletState state, Graphics g, float sx,
			float sy) {

	}

	/**
	 * <p>
	 * Indicates that a bullet may make multiple draw calls but still use <a href=
	 * "http://slick.ninjacave.com/javadoc/org/newdawn/slick/Image.html#drawEmbedded(float,%20float,%20float,%20float)">drawEmbedded(float,
	 * float, float, float)</a>
	 * </p>
	 * <p>
	 * This system has greater performance than {@link #isCustom()} and
	 * {@link #render(float, float, float, float, BulletState, Graphics, float, float)}
	 * but it requires that textures are bound and unbound.
	 * </p>
	 * 
	 * @see #render(float, float, float, float, BulletState, Graphics, float, float)
	 * @see #renderEmbedded(float, float, float, float, BulletState, Graphics,
	 *      Image, float, float)
	 * @see #isCustom()
	 * @return {@code true} if a combined rendering method is being used,
	 *         {@code false} otherwise.
	 */
	public default boolean isCombined() {
		return false;
	}

	/**
	 * <p>
	 * Renders a bullet using embedded methods.
	 * </p>
	 * <p>
	 * This system has greater performance than {@link #isCustom()} and
	 * {@link #render(float, float, float, float, BulletState, Graphics, float, float)}
	 * but it requires that textures are bound and unbound.
	 * </p>
	 * <p>
	 * Non-embedded drawing can be done provided the texture is unbound then bound
	 * again as shown below.
	 * </p>
	 * <code>
	 * if (current != null) current.endUse();<br>
	 * ...Do something...<br>
	 * if (current != null) current.startUse();
	 * </code>
	 * <p>
	 * To change the texture if should be done similar to what is shown below. The
	 * method shown below also allows for texture atlasing to be used which can
	 * improve performance.
	 * </p>
	 * <ul>
	 * <li>img - The image you want to change to.</li>
	 * </ul>
	 * <code>
	 * if (current != TextureMap.getSheet(img)) {<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;if (current != null) current.endUse();<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;current = TextureMap.getSheet(img);<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;current.startUse();<br>
	 * }
	 * </code>
	 * 
	 * @param px The X position of the player.
	 * @param py The Y position of the player.
	 * @param xv The X velocity of the player.
	 * @param yv The Y velocity of the player.
	 * @param state The current bullet state.
	 * @param g The current graphics context.
	 * @param current The current texture or {@code null} if no texture is bound.
	 * @param sx The X position of the bullet on the screen.
	 * @param sy The Y position of the bullet on the screen.
	 * @return This should return {@code current} including any changes made to the bound texture.
	 * 
	 * @see io.github.tomaso2468.rpgonline.texture.TextureMap
	 * @see io.github.tomaso2468.rpgonline.texture.TextureMap#getSheet(Image)
	 * @see #render(float, float, float, float, BulletState, Graphics, float, float)
	 * @see #isCombined()
	 * @see #isCustom()
	 */
	public default Image renderEmbedded(float px, float py, float xv, float yv, BulletState state, Graphics g,
			Image current, float sx, float sy) {
		return current;
	}
}
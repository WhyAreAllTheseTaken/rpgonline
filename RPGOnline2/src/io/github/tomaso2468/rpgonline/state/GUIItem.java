package io.github.tomaso2468.rpgonline.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A part of the GUI
 * 
 * @author Tomas
 */
public interface GUIItem {
	/**
	 * Render this GUI
	 * 
	 * @param g         The screen graphics.
	 * @param container The game container holding this game.
	 * @param game      This game.
	 * @param s_width   The width of the screen (scaled).
	 * @param s_height  The height of the screen (scaled).
	 */
	public void render(Graphics g, GameContainer container, StateBasedGame game, float s_width, float s_height);

	/**
	 * <p>
	 * Indicated if coordinates should be centred.
	 * </p>
	 * <p>
	 * For example, if {@code isCentered} returns {@code true} then coordinates
	 * would be scaled so that {@code (0, 0)} is in the centre of the screen. If
	 * {@code isCentered} returns {@code false} then {@code (0,0)} is in the top
	 * left corner.
	 * 
	 * @return {@code true} if coordinates should be centred. {@code false}
	 *         otherwise.
	 */
	public boolean isCentered();

	/**
	 * <p>
	 * Update the GUI.
	 * </p>
	 * <p>
	 * This method should be overridden by GUI elements with keyboard controls.
	 * </p>
	 * 
	 * @param container The game container holding this game.
	 * @param game      This game.
	 * @param delta     The time in milliseconds since the last update.
	 */
	public default void update(GameContainer container, StateBasedGame game, int delta) {

	}
}

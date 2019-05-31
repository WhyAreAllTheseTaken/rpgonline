package rpgonline.post;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A class for managing graphical effects.
 * 
 * @author Tomas
 *
 */
public interface PostEffect {
	/**
	 * Run the effect.
	 * 
	 * @param container The container holding the game.
	 * @param game      The game.
	 * @param buffer    The screen buffer from the last render or from the last
	 *                  effect.
	 * @param g         The screen graphics object.
	 * @throws SlickException If an error occurs rendering the effect.
	 */
	public void doPostProcess(GameContainer container, StateBasedGame game, Image buffer, Graphics g)
			throws SlickException;

	public default void dispose() throws SlickException {

	}
}

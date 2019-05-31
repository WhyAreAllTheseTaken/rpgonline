package rpgonline.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A task to be run during a game update.
 * 
 * @author Tomas
 */
public interface UpdateHook {
	/**
	 * Update the game.
	 * 
	 * @param container The game container holding the game.
	 * @param game      The game.
	 * @param delta     The time in milliseconds since the last game update.
	 * @throws SlickException If an error occurs.
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException;
}

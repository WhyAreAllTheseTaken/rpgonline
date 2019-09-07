package io.github.tomaso2468.rpgonline.cutscene;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import io.github.tomaso2468.rpgonline.audio.AmbientMusic;
import io.github.tomaso2468.rpgonline.audio.AudioManager;

/**
 * An abstract class representing a cutscene.
 * @author Tomas
 */
public abstract class Cutscene {
	/**
	 * The time left for the cutscene in seconds.
	 */
	protected float time;
	/**
	 * The total length of the cutscene.
	 */
	protected final float base_time;
	
	/**
	 * Constructs a new cutscene.
	 * @param time The total length of the cutscene.
	 */
	public Cutscene(float time) {
		this.base_time = time;
		this.time = time;
	}
	
	/**
	 * Determines if the cutscene has finished playing.
	 * @return {@code true} if the cutscene is finished, {@code false} otherwise.
	 */
	public boolean isDone() {
		return time <= 0;
	}
	/**
	 * Resets the cutscene to the beginning. This should not be used to determine the start of the cutscene.
	 */
	public void reset() {
		time = base_time;
	}
	/**
	 * Updates the cutscene.
	 * @param delf The time since the last game update in seconds.
	 */
	public void update(float delf) {
		time -= delf;
	}
	/**
	 * Renders the cutscene/
	 * @param c The current game container.
	 * @param game The current game.
	 * @param g The current graphics context.
	 * @param state The current game state.
	 */
	public abstract void render(GameContainer c, StateBasedGame game, Graphics g, CutsceneState state);
	/**
	 * Gets the music for this cutscene.
	 * @return An ambient music object.
	 */
	public abstract AmbientMusic getMusic();
	/**
	 * Called at the start of a cutscene.
	 */
	public void onStart() {
		AudioManager.setMusic(getMusic());
	}
}

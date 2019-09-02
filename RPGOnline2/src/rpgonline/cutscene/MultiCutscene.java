package rpgonline.cutscene;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import rpgonline.audio.AmbientMusic;

/**
 * A cutscene type that allows multiple cutscenes to be combined.
 * @author Tomas
 *
 */
public class MultiCutscene extends Cutscene {
	/**
	 * The array of scenes.
	 */
	private final Cutscene[] scenes;
	/**
	 * The current cutscene index.
	 */
	private int index = 0;
	/**
	 * The previous game updates cutscene index.
	 */
	private int lastIndex = -1;
	/**
	 * The previously playing music.
	 */
	private AmbientMusic lastMusic = null;
	/**
	 * Constructs a new MultiCutscene.
	 * @param scenes The array of cutscenes.
	 */
	public MultiCutscene(Cutscene[] scenes) {
		super(1);
		this.scenes = scenes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(GameContainer c, StateBasedGame game, Graphics g, CutsceneState state) {
		if (index < scenes.length) {
			scenes[index].render(c, game, g, state);
		}
		
		g.resetTransform();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDone() {
		return index == scenes.length;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		lastIndex = -1;
		index = 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(float delf) {
		if (lastIndex != index) {
			if (index < scenes.length) {
				scenes[index].onStart();
			}
		}
		if (index < scenes.length) {
			scenes[index].update(delf);
			
			lastMusic = scenes[index].getMusic();
			
			if (scenes[index].isDone()) {
				index += 1;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AmbientMusic getMusic() {
		return lastMusic;
	}

}

package rpgonline.cutscene;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import rpgonline.audio.AmbientMusic;

public class MultiCutscene extends Cutscene {
	private final Cutscene[] scenes;
	private int index = 0;
	private int lastIndex = -1;
	private AmbientMusic lastMusic = null;
	public MultiCutscene(Cutscene[] scenes) {
		super(1);
		this.scenes = scenes;
	}

	@Override
	public void render(GameContainer c, StateBasedGame game, Graphics g, CutsceneState state) {
		if (index < scenes.length) {
			scenes[index].render(c, game, g, state);
		}
		
		g.resetTransform();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
	}
	
	@Override
	public boolean isDone() {
		return index == scenes.length;
	}
	
	@Override
	public void reset() {
		lastIndex = -1;
		index = 0;
	}
	
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

	@Override
	public AmbientMusic getMusic() {
		return lastMusic;
	}

}

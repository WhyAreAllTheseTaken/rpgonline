package rpgonline.cutscene;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import rpgonline.audio.AmbientMusic;
import rpgonline.audio.AudioManager;

public abstract class Cutscene {
	protected float time;
	protected final float base_time;
	
	public Cutscene(float time) {
		this.base_time = time;
		this.time = time;
	}
	
	public boolean isDone() {
		return time <= 0;
	}
	public void reset() {
		time = base_time;
	}
	public void update(float delf) {
		time -= delf;
	}
	public abstract void render(GameContainer c, StateBasedGame game, Graphics g, CutsceneState state);
	public abstract AmbientMusic getMusic();
	public void onStart() {
		AudioManager.setMusic(getMusic());
	}
}

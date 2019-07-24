package rpgonline.cutscene;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import rpgonline.audio.AmbientMusic;
import rpgonline.audio.AudioManager;
import rpgonline.texture.TextureMap;

public class TextureCutscene extends Cutscene {
	private final String texture;
	private final String music;
	public TextureCutscene(float time, String texture, String music) {
		super(time);
		this.texture = texture;
		this.music = music;
	}

	@Override
	public void render(GameContainer c, StateBasedGame game, Graphics g, CutsceneState state) {
		g.drawImage(TextureMap.getTexture(texture).getScaledCopy(c.getWidth(), c.getHeight()), 0, 0);
	}

	@Override
	public AmbientMusic getMusic() {
		return AudioManager.getAmbientMusic(music);
	}

}

package rpgonline.cutscene;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import rpgonline.audio.AmbientMusic;
import rpgonline.audio.AudioManager;
import rpgonline.texture.TextureMap;

/**
 * A cutscene that displays a static texture.
 * @author Tomas
 */
public class TextureCutscene extends Cutscene {
	/**
	 * The texture ID.
	 */
	private final String texture;
	/**
	 * The music ID.
	 */
	private final String music;
	/**
	 * Constructs a new TextureCutscene.
	 * @param time The length of the cutscene in seconds.
	 * @param texture The texture ID to display.
	 * @param music The music to play.
	 */
	public TextureCutscene(float time, String texture, String music) {
		super(time);
		this.texture = texture;
		this.music = music;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(GameContainer c, StateBasedGame game, Graphics g, CutsceneState state) {
		g.drawImage(TextureMap.getTexture(texture).getScaledCopy(c.getWidth(), c.getHeight()), 0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AmbientMusic getMusic() {
		return AudioManager.getAmbientMusic(music);
	}

}

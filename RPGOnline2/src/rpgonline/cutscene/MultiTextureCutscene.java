package rpgonline.cutscene;

/**
 * A cutscene made of a series of static images.
 * @author Tomas
 *
 */
public class MultiTextureCutscene extends MultiCutscene {

	/**
	 * Constructs new MultiTextureCutscene.
	 * @param texture The texture prefix ID to use this is combined with a "." and an index starting at 0.
	 * @param length The length of the textures.
	 * @param time The length of each frame in seconds.
	 * @param music The music to play.
	 */
	public MultiTextureCutscene(String texture, int length, float time, String music) {
		super(generate(texture, length, time, music));
	}
	
	/**
	 * Constructs new MultiTextureCutscene.
	 * @param texture The texture prefix ID to use this is combined with a "." and an index starting at 0.
	 * @param length The length of the textures.
	 * @param time The length for each frame in seconds.
	 * @param music The music to play.
	 */
	public MultiTextureCutscene(String texture, int length, float[] time, String music) {
		super(generate(texture, length, time, music));
	}
	
	/**
	 * Generates a cutscene array for the constructor.
	 * @param texture The texture prefix ID to use this is combined with a "." and an index starting at 0.
	 * @param length The length of the textures.
	 * @param time The length of each frame in seconds.
	 * @param music The music to play.
	 * @return a cutscene array.
	 */
	private static Cutscene[] generate(String texture, int len, float time, String music) {
		TextureCutscene[] textures = new TextureCutscene[len];
		
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new TextureCutscene(time, texture + "." + i, music);
		}
		
		return textures;
	}
	
	/**
	 * Generates a cutscene array for the constructor.
	 * @param texture The texture prefix ID to use this is combined with a "." and an index starting at 0.
	 * @param length The length of the textures.
	 * @param time The length for each frame in seconds.
	 * @param music The music to play.
	 * @return a cutscene array.
	 */
	private static Cutscene[] generate(String texture, int len, float[] time, String music) {
		TextureCutscene[] textures = new TextureCutscene[len];
		
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new TextureCutscene(time[i], texture + "." + i, music);
		}
		
		return textures;
	}

}

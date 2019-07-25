package rpgonline.cutscene;

public class MultiTextureCutscene extends MultiCutscene {

	public MultiTextureCutscene(String texture, int length, float time, String music) {
		super(generate(texture, length, time, music));
	}
	
	public MultiTextureCutscene(String texture, int length, float[] time, String music) {
		super(generate(texture, length, time, music));
	}
	
	private static Cutscene[] generate(String texture, int len, float time, String music) {
		TextureCutscene[] textures = new TextureCutscene[len];
		
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new TextureCutscene(time, texture + "." + i, music);
		}
		
		return textures;
	}
	
	private static Cutscene[] generate(String texture, int len, float[] time, String music) {
		TextureCutscene[] textures = new TextureCutscene[len];
		
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new TextureCutscene(time[i], texture + "." + i, music);
		}
		
		return textures;
	}

}

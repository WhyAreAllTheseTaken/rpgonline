package rpgonline.audio;

/**
 * A class for storing Ambient Music.
 * @author Tomas
 *
 */
public class AmbientMusic {
	private final String[] sounds;
	private final String[] groups;
	private final float[] volumes;
	final String[] refs;

	public AmbientMusic(String[] sounds, String[] groups, float[] volumes) {
		super();
		this.sounds = sounds;
		refs = new String[sounds.length];
		this.groups = groups;
		this.volumes = volumes;
	}
	
	public AmbientMusic(String sound, String group, float volume) {
		this(new String[] { sound }, new String[] { group }, new float[] {volume});
	}
	
	public AmbientMusic(String sound, float volume) {
		this(sound, "music", volume);
	}
	
	public AmbientMusic(String sound) {
		this(sound, 1f);
	}

	public String[] getSounds() {
		return sounds;
	}

	public String[] getGroups() {
		return groups;
	}

	public float[] getVolumes() {
		return volumes;
	}
}

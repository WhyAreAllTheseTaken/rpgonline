package rpgonline.net.packet;

public class MusicPacket implements NetPacket {
	private static final long serialVersionUID = 2441590535347918982L;
	private final String music;

	public MusicPacket(String music) {
		super();
		this.music = music;
	}

	public String getMusic() {
		return music;
	}
}

package rpgonline.net.packet;

import rpgonline.audio.AudioManager;

public class AmbientPacket implements NetPacket {
	private static final long serialVersionUID = -6976795378405555984L;
	private final String id;
	private final float v;
	private final float p;
	private final float x;
	private final float y;
	private final float z;
	private final boolean loop;

	public AmbientPacket(String id, float v, float p, float x, float y, float z, boolean loop) {
		super();
		this.id = id;
		this.v = v;
		this.p = p;
		this.x = x;
		this.y = y;
		this.z = z;
		this.loop = loop;
	}

	public void apply() {
		AudioManager.playAmbient(id, v, p, x, y, z, loop);
	}
}

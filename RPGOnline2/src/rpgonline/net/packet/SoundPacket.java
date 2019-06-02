package rpgonline.net.packet;

import rpgonline.audio.AudioManager;

public class SoundPacket implements NetPacket {
	private static final long serialVersionUID = 1589226011832111169L;
	private final String id;
	private final float v;
	private final float p;
	private final float x;
	private final float y;
	private final float z;
	private final float dx;
	private final float dy;
	private final float dz;

	public SoundPacket(String id, float v, float p, float x, float y, float z, float dx, float dy, float dz) {
		super();
		this.id = id;
		this.v = v;
		this.p = p;
		this.x = x;
		this.y = y;
		this.z = z;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	public void apply() {
		AudioManager.playSound(id, v, p, x, y, z, false, dx, dy, dz);
	}
}

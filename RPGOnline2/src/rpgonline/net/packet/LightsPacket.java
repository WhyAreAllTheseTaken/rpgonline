package rpgonline.net.packet;

import java.util.List;

import rpgonline.world.LightSource;

public class LightsPacket implements NetPacket {
	private static final long serialVersionUID = -3648467862338726913L;
	private final List<LightSource> lights;

	public LightsPacket(List<LightSource> lights) {
		super();
		this.lights = lights;
	}

	public List<LightSource> getLights() {
		return lights;
	}
}

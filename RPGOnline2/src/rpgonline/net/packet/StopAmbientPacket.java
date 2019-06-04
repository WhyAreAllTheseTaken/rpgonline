package rpgonline.net.packet;

import rpgonline.audio.AudioManager;

public class StopAmbientPacket implements NetPacket {
	private static final long serialVersionUID = 3184792895522235066L;
	
	public void apply() {
		AudioManager.stopAmbient();
	}
}

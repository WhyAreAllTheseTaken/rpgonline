package rpgonline.net.packet;

public class ShadowPacket implements NetPacket {
	private static final long serialVersionUID = 1819479861136004882L;

	private final float shadow;

	public ShadowPacket(float shadow) {
		super();
		this.shadow = shadow;
	}
	
	public float getShadow() {
		return shadow;
	}
}

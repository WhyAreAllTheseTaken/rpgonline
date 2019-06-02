package rpgonline.net.packet;

public class WindPacket implements NetPacket {
	private static final long serialVersionUID = 6681502216787431450L;
	private final float wind;

	public WindPacket(float wind) {
		super();
		this.wind = wind;
	}

	public float getWind() {
		return wind;
	}
}

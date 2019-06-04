package rpgonline.net.packet;

public class ChunkRequestPacket implements NetPacket {
	private static final long serialVersionUID = 2088638661829275185L;
	public final long x, y, z;
	public ChunkRequestPacket(long x, long y, long z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
}

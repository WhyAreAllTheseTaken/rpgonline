package rpgonline.net.packet;

public class MovePacket implements NetPacket {
	private static final long serialVersionUID = 3538606078927907705L;
	public final double x, y;
	public final boolean sprint;
	
	public MovePacket(double x, double y, boolean sprint) {
		super();
		this.x = x;
		this.y = y;
		this.sprint = sprint;
	}
	
	
}

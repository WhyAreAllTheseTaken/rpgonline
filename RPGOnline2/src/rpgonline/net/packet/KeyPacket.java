package rpgonline.net.packet;

import java.security.PublicKey;

public class KeyPacket implements NetPacket {
	private static final long serialVersionUID = -3131656449111362838L;
	public final byte[] key;

	public KeyPacket(byte[] key) {
		this.key = key;
	}

	public KeyPacket(PublicKey key) {
		this(key.getEncoded());
	}
}

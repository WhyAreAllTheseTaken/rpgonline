package rpgonline.abt;

/**
 * A 128bit integer tag.
 * @author Tomas
 */
public class Tag128 extends TagByteArray {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1699823075838316001L;

	public Tag128(String name, byte[] data) {
		super(name, data);
	}
	
	@Override
	public byte getType() {
		return 0x06;
	}
}

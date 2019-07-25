package rpgonline.abt;

/**
 * A 128bit integer tag.
 * @author Tomas
 */
public class Tag128 extends TagByteArray {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 1699823075838316001L;

	/**
	 * Constructs a new 128bit tag
	 * @param name The tag name.
	 * @param data The big-endian byte data for this number.
	 */
	public Tag128(String name, byte[] data) {
		super(name, data);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getType() {
		return 0x06;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag128 clone() {
		return new Tag128(name, getData());
	}
}

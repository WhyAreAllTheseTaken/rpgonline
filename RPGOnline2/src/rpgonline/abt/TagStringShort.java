package rpgonline.abt;

/**
 * <p>
 * A tag holding a string value with a length measured as a signed 16bit
 * integer. If a higher length is required use {@code TagString} instead.
 * </p>
 * 
 * @author Tomas
 * 
 * @see rpgonline.abt.TagString
 */
public class TagStringShort extends TagString {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 4128177059182617455L;

	/**
	 * Constructs a new short string tag.
	 * 
	 * @param name
	 * @param data
	 */
	public TagStringShort(String name, String data) {
		super(name, data);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getType() {
		return 0x0A;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagStringShort clone() {
		return new TagStringShort(name, getData());
	}
}

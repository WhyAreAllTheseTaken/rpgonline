package rpgonline.abt;

public class TagStringShort extends TagString {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 4128177059182617455L;
	public TagStringShort(String name, String data) {
		super(name, data);
	}
	@Override
	public byte getType() {
		return 0x0A;
	}
	
	@Override
	public TagStringShort clone() {
		return new TagStringShort(name, getData());
	}
}

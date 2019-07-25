package rpgonline.abt;

public class TagMatrix3 extends TagMatrix {

	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 2774523147051092054L;

	public TagMatrix3(String name, double[][] data) {
		super(name, data);
	}
	
	@Override
	public byte getType() {
		return 0x1E;
	}
	
	@Override
	public TagMatrix3 clone() {
		return new TagMatrix3(name, getData());
	}

}

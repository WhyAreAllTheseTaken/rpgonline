package rpgonline.abt;

public class TagMatrix2 extends TagMatrix {

	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = -8333903528570351735L;

	public TagMatrix2(String name, double[][] data) {
		super(name, data);
	}
	
	@Override
	public byte getType() {
		return 0x1D;
	}
	
	@Override
	public TagMatrix2 clone() {
		return new TagMatrix2(name, getData());
	}

}

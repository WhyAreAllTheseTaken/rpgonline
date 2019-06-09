package rpgonline.abt;

public class TagMatrix4 extends TagMatrix {
	/**
	 * 
	 */
	private static final long serialVersionUID = 379159091560392566L;

	public TagMatrix4(String name, double[][] data) {
		super(name, data);
	}
	
	@Override
	public byte getType() {
		return 0x1F;
	}
	
	@Override
	public TagMatrix4 clone() {
		return new TagMatrix4(name, getData());
	}
}

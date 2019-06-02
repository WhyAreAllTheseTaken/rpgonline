package rpgonline.abt;

public class TagVector3 extends TagDoubleArray {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7179822069996874868L;

	public TagVector3(String name, double a, double b, double c) {
		super(name, new double[] {a, b, c});
	}
	
	@Override
	public byte getType() {
		return 0x1B;
	}
}

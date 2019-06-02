package rpgonline.abt;

public class TagVector4 extends TagDoubleArray {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4087268138371073020L;

	public TagVector4(String name, double a, double b, double c, double d) {
		super(name, new double[] {a, b, c, d});
	}
	
	@Override
	public byte getType() {
		return 0x1C;
	}
}

package rpgonline.abt;

public class TagVector2 extends TagDoubleArray {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = -2292925464289505467L;

	public TagVector2(String name, double a, double b) {
		super(name, new double[] {a, b});
	}
	
	@Override
	public byte getType() {
		return 0x1A;
	}
	
	@Override
	public TagVector2 clone() {
		return new TagVector2(name, getData()[0], getData()[1]);
	}
}

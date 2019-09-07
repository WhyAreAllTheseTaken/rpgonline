package io.github.tomaso2468.rpgonline.abt;

@Deprecated
public class TagVector3 extends TagDoubleArray {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 7179822069996874868L;

	public TagVector3(String name, double a, double b, double c) {
		super(name, new double[] {a, b, c});
	}
	
	@Override
	public byte getType() {
		return 0x1B;
	}
	
	@Override
	public TagVector3 clone() {
		return new TagVector3(name, getData()[0], getData()[1], getData()[2]);
	}
}

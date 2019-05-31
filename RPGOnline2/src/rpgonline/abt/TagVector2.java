package rpgonline.abt;

public class TagVector2 extends TagDoubleArray {
	public TagVector2(String name, double a, double b) {
		super(name, new double[] {a, b});
	}
	
	@Override
	public byte getType() {
		return 0x1A;
	}
}

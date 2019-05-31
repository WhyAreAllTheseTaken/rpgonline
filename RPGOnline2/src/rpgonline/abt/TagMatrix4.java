package rpgonline.abt;

public class TagMatrix4 extends TagMatrix {
	public TagMatrix4(String name, double[][] data) {
		super(name, data);
	}
	
	@Override
	public byte getType() {
		return 0x1F;
	}
}

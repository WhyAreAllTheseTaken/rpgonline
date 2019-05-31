package rpgonline.abt;

public class TagMatrix3 extends TagMatrix {

	public TagMatrix3(String name, double[][] data) {
		super(name, data);
	}
	
	@Override
	public byte getType() {
		return 0x1E;
	}

}

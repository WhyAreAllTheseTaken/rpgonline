package rpgonline.abt;

public class TagMatrix2 extends TagMatrix {

	public TagMatrix2(String name, double[][] data) {
		super(name, data);
	}
	
	@Override
	public byte getType() {
		return 0x1D;
	}

}

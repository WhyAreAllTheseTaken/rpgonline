package rpgonline.abt;

public class Tag128 extends TagByteArray {
	public Tag128(String name, byte[] data) {
		super(name, data);
	}
	
	@Override
	public byte getType() {
		return 0x06;
	}
}

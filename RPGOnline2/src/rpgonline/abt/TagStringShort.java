package rpgonline.abt;

public class TagStringShort extends TagString {
	public TagStringShort(String name, String data) {
		super(name, data);
	}
	@Override
	public byte getType() {
		return 0x0A;
	}
}

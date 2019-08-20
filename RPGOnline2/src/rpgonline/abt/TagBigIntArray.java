package rpgonline.abt;

@Deprecated
public class TagBigIntArray extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 7429612726507784333L;

	public TagBigIntArray(String name, byte[][] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x12;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagBigInt)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(byte[][] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new Tag128(i + "", data[i]));
		}
	}
	
	public byte[][] getData() {
		byte[][] data = new byte[size()][];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagBigInt) tags.get(i)).getData();
		}
		
		return data;
	}
	
	public void set(int index, byte[] v) {
		((TagBigInt) getTag(index + "")).setData(v);
	}
	
	public byte[] get(int index) {
		return ((TagBigInt) getTag(index + "")).getData();
	}
	
	@Override
	public TagBigIntArray clone() {
		return new TagBigIntArray(name, getData());
	}
}
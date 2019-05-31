package rpgonline.abt;

public class Tag128Array extends TagGroup {
	public Tag128Array(String name, byte[][] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x11;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof Tag128)) {
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
			data[i] = ((Tag128) tags.get(i)).getData();
		}
		
		return data;
	}
	
	public void set(int index, byte[] v) {
		((Tag128) getTag(index + "")).setData(v);
	}
	
	public byte[] get(int index) {
		return ((Tag128) getTag(index + "")).getData();
	}
}
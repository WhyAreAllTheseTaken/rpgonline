package rpgonline.abt;

public class TagLongArray extends TagGroup {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8423838098514114284L;

	public TagLongArray(String name, long[] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x10;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagLong)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(long[] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagLong(i + "", data[i]));
		}
	}
	
	public long[] getData() {
		long[] data = new long[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagLong) tags.get(i)).getData();
		}
		
		return data;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{\n");

		appendStr(sb, "name", getName());
		appendSep(sb);
		appendNum(sb, "type", getType());
		appendSep(sb);
		
		String[] data = new String[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagLong) tags.get(i)).getData() + "";
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	public void set(int index, long v) {
		((TagLong) getTag(index + "")).setData(v);
	}
	
	public long get(int index) {
		return ((TagLong) getTag(index + "")).getData();
	}
	
	@Override
	public TagLongArray clone() {
		return new TagLongArray(name, getData());
	}
}

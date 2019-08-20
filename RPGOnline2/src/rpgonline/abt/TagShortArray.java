package rpgonline.abt;

@Deprecated
public class TagShortArray extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 1010121178154657137L;

	public TagShortArray(String name, short[] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x0E;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagShort)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(short[] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagShort(i + "", data[i]));
		}
	}
	
	public short[] getData() {
		short[] data = new short[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagShort) tags.get(i)).getData();
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
			data[i] = ((TagShort) tags.get(i)).getData() + "";
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	public void set(int index, short v) {
		((TagShort) getTag(index + "")).setData(v);
	}
	
	public short get(int index) {
		return ((TagShort) getTag(index + "")).getData();
	}
	
	@Override
	public TagShortArray clone() {
		return new TagShortArray(name, getData());
	}
}

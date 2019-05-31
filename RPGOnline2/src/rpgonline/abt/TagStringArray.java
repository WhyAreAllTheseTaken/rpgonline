package rpgonline.abt;

public class TagStringArray extends TagGroup {
	public TagStringArray(String name, String[] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x17;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagString)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(String[] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagString(i + "", data[i]));
		}
	}
	
	public String[] getData() {
		String[] data = new String[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagString) tags.get(i)).getData();
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
			data[i] = "\"" + sanitiseJSON(((TagString) tags.get(i)).getData()) + "\"";
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	public void set(int index, String v) {
		((TagString) getTag(index + "")).setData(v);
	}
	
	public String get(int index) {
		return ((TagString) getTag(index + "")).getData();
	}
}

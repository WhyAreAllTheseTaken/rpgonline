package rpgonline.abt;

public class TagCharArray extends TagGroup {
	public TagCharArray(String name, char[] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x19;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagChar)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(char[] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagChar(i + "", data[i]));
		}
	}
	
	public char[] getData() {
		char[] data = new char[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagChar) tags.get(i)).getData();
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
			data[i] = "\"" + sanitiseJSON(((TagChar) tags.get(i)).getData() + "") + "\"";
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	public void set(int index, char v) {
		((TagChar) getTag(index + "")).setData(v);
	}
	
	public char get(int index) {
		return ((TagChar) getTag(index + "")).getData();
	}
}

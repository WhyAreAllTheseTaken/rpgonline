package rpgonline.abt;

public class TagBooleanArray extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 5651631010558425924L;

	public TagBooleanArray(String name, boolean[] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x21;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagBoolean)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(boolean[] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagBoolean(i + "", data[i]));
		}
	}
	
	public boolean[] getData() {
		boolean[] data = new boolean[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagBoolean) tags.get(i)).getData();
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
			data[i] = ((TagBoolean) tags.get(i)).getData() + "";
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	public void set(int index, boolean v) {
		((TagBoolean) getTag(index + "")).setData(v);
	}
	
	public boolean get(int index) {
		return ((TagBoolean) getTag(index + "")).getData();
	}
	
	@Override
	public TagBooleanArray clone() {
		return new TagBooleanArray(name, getData());
	}
}

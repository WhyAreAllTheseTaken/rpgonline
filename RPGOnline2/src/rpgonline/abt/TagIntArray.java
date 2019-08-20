package rpgonline.abt;

@Deprecated
public class TagIntArray extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 6491733185975406878L;

	public TagIntArray(String name, int[] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x0F;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagInt)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(int[] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagInt(i + "", data[i]));
		}
	}
	
	public int[] getData() {
		int[] data = new int[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagInt) tags.get(i)).getData();
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
			data[i] = ((TagInt) tags.get(i)).getData() + "";
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	public void set(int index, int v) {
		((TagInt) getTag(index + "")).setData(v);
	}
	
	public int get(int index) {
		return ((TagInt) getTag(index + "")).getData();
	}
	
	@Override
	public TagIntArray clone() {
		return new TagIntArray(name, getData());
	}
}

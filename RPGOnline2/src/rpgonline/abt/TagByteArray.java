package rpgonline.abt;

public class TagByteArray extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 4138088648100888068L;

	public TagByteArray(String name, byte[] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x0D;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagByte)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(byte[] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagByte(i + "", data[i]));
		}
	}
	
	public byte[] getData() {
		byte[] data = new byte[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagByte) tags.get(i)).getData();
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
			data[i] = ((TagByte) tags.get(i)).getData() + "";
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	public void set(int index, byte v) {
		((TagByte) getTag(index + "")).setData(v);
	}
	
	public byte get(int index) {
		return ((TagByte) getTag(index + "")).getData();
	}
	
	@Override
	public TagByteArray clone() {
		return new TagByteArray(name, getData());
	}
}

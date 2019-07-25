package rpgonline.abt;

public class TagLong extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 8009138827339894704L;
	private long data;
	
	public TagLong(String name, long data) {
		super(name, 0x05);
	}
	
	public long getData() {
		return data;
	}
	
	public void setData(long data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{\n");

		appendStr(sb, "name", getName());
		appendSep(sb);
		appendNum(sb, "type", getType());
		appendSep(sb);
		appendNum(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	@Override
	public TagLong clone() {
		return new TagLong(name, getData());
	}

}

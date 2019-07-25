package rpgonline.abt;

public class TagBoolean extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = -5639673941391616798L;
	private boolean data;
	
	public TagBoolean(String name, boolean data) {
		super(name, 0x20);
	}
	
	public boolean getData() {
		return data;
	}
	
	public void setData(boolean data) {
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
		appendBool(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	@Override
	public TagBoolean clone() {
		return new TagBoolean(name, getData());
	}

}

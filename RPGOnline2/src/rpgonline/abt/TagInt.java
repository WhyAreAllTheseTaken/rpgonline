package rpgonline.abt;

public class TagInt extends Tag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1440056949202502049L;
	private int data;
	
	public TagInt(String name, int data) {
		super(name, 0x04);
	}
	
	public int getData() {
		return data;
	}
	
	public void setData(int data) {
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
	public TagInt clone() {
		return new TagInt(name, getData());
	}

}

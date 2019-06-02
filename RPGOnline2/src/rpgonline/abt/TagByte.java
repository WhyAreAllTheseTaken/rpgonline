package rpgonline.abt;

public class TagByte extends Tag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5823727651026695943L;
	private byte data;
	
	public TagByte(String name, byte data) {
		super(name, 0x02);
	}
	
	public byte getData() {
		return data;
	}
	
	public void setData(byte data) {
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

}

package rpgonline.abt;

public class TagChar extends Tag {
	private char data;
	
	public TagChar(String name, char data) {
		super(name, 0x18);
	}
	
	public char getData() {
		return data;
	}
	
	public void setData(char data) {
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
		appendStr(sb, "data", "\"" + sanitiseJSON(data + "") + "\"");
		
		sb.append("\n}");

		return sb.toString();
	}
}

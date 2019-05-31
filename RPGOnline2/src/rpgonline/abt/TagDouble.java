package rpgonline.abt;

public class TagDouble extends Tag {
	private double data;
	
	public TagDouble(String name, double data) {
		super(name, 0x09);
	}
	
	public double getData() {
		return data;
	}
	
	public void setData(double data) {
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

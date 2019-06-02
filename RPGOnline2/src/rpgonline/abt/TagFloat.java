package rpgonline.abt;

public class TagFloat extends Tag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 878957984413787929L;
	private float data;
	
	public TagFloat(String name, float data) {
		super(name, 0x08);
	}
	
	public float getData() {
		return data;
	}
	
	public void setData(float data) {
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

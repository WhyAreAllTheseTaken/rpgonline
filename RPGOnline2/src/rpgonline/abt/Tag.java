package rpgonline.abt;

public abstract class Tag {
	private String name;
	private byte type;

	public Tag(String name, int type) {
		this.name = name;
		this.type = (byte) type;
	}

	public long getLength() {
		return 2 + name.length();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{\n");

		appendStr(sb, "name", getName());
		appendSep(sb);
		appendNum(sb, "type", getType());
		
		sb.append("\n}");

		return sb.toString();
	}

	public byte getType() {
		return type;
	}

	protected static void appendStr(StringBuilder sb, String key, String v) {
		sb.append("\"");
		sb.append(sanitiseJSON(key));
		sb.append("\"");

		sb.append(":");

		sb.append("\"");
		sb.append(sanitiseJSON(v));
		sb.append("\"");
	}
	
	protected static void appendNum(StringBuilder sb, String key, double v) {
		sb.append("\"");
		sb.append(sanitiseJSON(key));
		sb.append("\"");

		sb.append(":");

		sb.append(v);
	}
	
	protected static void appendBool(StringBuilder sb, String key, boolean v) {
		sb.append("\"");
		sb.append(sanitiseJSON(key));
		sb.append("\"");

		sb.append(":");

		sb.append(v);
	}
	
	protected static void appendArray(StringBuilder sb, String key, String[] v) {
		sb.append("\"");
		sb.append(sanitiseJSON(key));
		sb.append("\"");

		sb.append(":");

		sb.append("[");
		
		for (int i = 0; i < v.length; i++) {
			sb.append(v[i]);
			if(i != v.length - 1) {
				sb.append(",\n");
			}
		}
		
		sb.append("]");
	}
	
	protected static void appendObject(StringBuilder sb, String key, String o) {
		sb.append("\"");
		sb.append(sanitiseJSON(key));
		sb.append("\"");

		sb.append(":");

		sb.append(o);
	}
	
	protected static void appendSep(StringBuilder sb) {
		sb.append(",");
		sb.append("\n");
	}
	
	protected static String sanitiseJSON(String in) {
		return in.replace("\\", "\\\\").replace("\b", "\\b").replace("\f", "\\f").replace("\n", "\\n")
				.replace("\r", "\\r").replace("\t", "\\t").replace("\"", "\\\"");
	}
	
	public Tag getTag(String name) {
		String[] name2 = name.split("/");
		if(name2[0].equals("this")) {
			StringBuilder sb = new StringBuilder();
			
			for (int i = 1; i < name2.length; i++) {
				sb.append(name2[i]);
				
				if(i != name2.length - 1) {
					sb.append("/");
				}
			}
			
			return this.getTag(sb.toString());
		}
		return null;
	}
}

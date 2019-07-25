package rpgonline.abt;

public class TagString extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 6344197038704127023L;
	private String data;
	
	public TagString(String name, String data) {
		super(name, 0x0B);
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public Tag getTag(String name) {
		Tag t = super.getTag(name);
		
		if (t == null) {
			try {
				int i = Integer.parseInt(name);
				t = new TagChar(name, data.charAt(i)) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 832728519849455689L;
					@Override
					public void setData(char data) {
						StringBuilder sb = new StringBuilder(TagString.this.data);
						sb.setCharAt(i, data);
						
						TagString.this.data = sb.toString();
					}
					@Override
					public char getData() {
						return data.charAt(i);
					}
				};
			} catch (NumberFormatException e) {
				return null;
			}
		}
		
		return t;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{\n");

		appendStr(sb, "name", getName());
		appendSep(sb);
		appendNum(sb, "type", getType());
		appendSep(sb);
		appendStr(sb, "data", "\"" + sanitiseJSON(data) + "\"");
		
		sb.append("\n}");

		return sb.toString();
	}
	
	@Override
	public TagString clone() {
		return new TagString(name, getData());
	}
}

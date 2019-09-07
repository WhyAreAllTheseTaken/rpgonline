package io.github.tomaso2468.rpgonline.abt;

/**
 * A tag holding a char value.
 * @author Tomas
 *
 */
public class TagChar extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = -1188892226546495285L;
	/**
	 * The data held in this tag.
	 */
	private char data;
	
	/**
	 * Constructs a new char tag.
	 * @param name The name of this tag.
	 * @param data The data to hold in this tag.
	 */
	public TagChar(String name, char data) {
		super(name, 0x18);
	}
	
	/**
	 * Gets the data held in this tag.
	 * @return A char value.
	 */
	public char getData() {
		return data;
	}
	
	/**
	 * Sets the data held in this tag.
	 * @param data A char value.
	 */
	public void setData(char data) {
		this.data = data;
	}

	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagChar clone() {
		return new TagChar(name, getData());
	}
}

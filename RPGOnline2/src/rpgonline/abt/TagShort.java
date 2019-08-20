package rpgonline.abt;

/**
 * A tag holding a short value.
 * @author Tomas
 *
 */
public class TagShort extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = -2634062576907254468L;
	/**
	 * The data held in this tag.
	 */
	private short data;
	
	/**
	 * Constructs a new short tag.
	 * @param name The name of this tag.
	 * @param data The value of this tag.
	 */
	public TagShort(String name, short data) {
		super(name, 0x03);
	}
	
	/**
	 * Gets the data held in this tag.
	 * @return A short value.
	 */
	public short getData() {
		return data;
	}
	
	/**
	 * Sets the data held in this tag.
	 * @param data A short value.
	 */
	public void setData(short data) {
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
		appendNum(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagShort clone() {
		return new TagShort(name, getData());
	}

}

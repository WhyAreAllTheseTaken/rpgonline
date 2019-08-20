package rpgonline.abt;

/**
 * A tag holding a long value.
 * @author Tomas
 *
 */
public class TagLong extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 8009138827339894704L;
	/**
	 * The data held in this tag.
	 */
	private long data;
	
	/**
	 * Constructs a new long tag.
	 * @param name The name of the tag.
	 * @param data The value of this tag.
	 */
	public TagLong(String name, long data) {
		super(name, 0x05);
	}
	
	/**
	 * Gets the data held in this tag.
	 * @return A long value.
	 */
	public long getData() {
		return data;
	}
	
	/**
	 * Sets the data held in this tag.
	 * @param data A long value.
	 */
	public void setData(long data) {
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
	public TagLong clone() {
		return new TagLong(name, getData());
	}

}

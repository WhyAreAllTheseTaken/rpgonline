package rpgonline.abt;

/**
 * A tag holding a boolean value.
 * @author Tomas
 *
 */
public class TagBoolean extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = -5639673941391616798L;
	/**
	 * The data held in this tag.
	 */
	private boolean data;
	
	/**
	 * Constructs a new boolean tag.
	 * @param name The name of the tag.
	 * @param data The data held in this tag.
	 */
	public TagBoolean(String name, boolean data) {
		super(name, 0x20);
	}
	
	/**
	 * Gets the data of this tag.
	 * @return A boolean value.
	 */
	public boolean getData() {
		return data;
	}
	
	/**
	 * Sets the data of this tag.
	 * @param data A boolean value.
	 */
	public void setData(boolean data) {
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
		appendBool(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagBoolean clone() {
		return new TagBoolean(name, getData());
	}

}

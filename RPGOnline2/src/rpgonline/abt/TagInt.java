package rpgonline.abt;

/**
 * A tag holding an integer value.
 * @author Tomas
 */
public class TagInt extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 1440056949202502049L;
	/**
	 * The data held in this tag.
	 */
	private int data;
	
	/**
	 * Constructs a new int tag.
	 * @param name The name of this tag.
	 * @param data The value of this tag.
	 */
	public TagInt(String name, int data) {
		super(name, 0x04);
	}
	
	/**
	 * Gets the data held in this tag.
	 * @return An int value.
	 */
	public int getData() {
		return data;
	}
	
	/**
	 * Sets the data held in this tag.
	 * @param data An int value.
	 */
	public void setData(int data) {
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
	public TagInt clone() {
		return new TagInt(name, getData());
	}

}

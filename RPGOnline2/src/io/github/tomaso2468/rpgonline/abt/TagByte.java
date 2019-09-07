package io.github.tomaso2468.rpgonline.abt;

/**
 * A tag holding a byte value.
 * @author Tomas
 *
 */
public class TagByte extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = -5823727651026695943L;
	/**
	 * The data held in this tag.
	 */
	private byte data;
	
	/**
	 * Constructs a new byte tag.
	 * @param name The name of this tag.
	 * @param data The data held in this tag.
	 */
	public TagByte(String name, byte data) {
		super(name, 0x02);
	}
	
	/**
	 * Gets the data held in this tag.
	 * @return A byte value.
	 */
	public byte getData() {
		return data;
	}
	
	/**
	 * Sets the data to hold in this tag.
	 * @param data A byte value.
	 */
	public void setData(byte data) {
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
	public TagByte clone() {
		return new TagByte(name, getData());
	}

}

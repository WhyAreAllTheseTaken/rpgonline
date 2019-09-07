package io.github.tomaso2468.rpgonline.abt;

/**
 * A tag holding a double value.
 * @author Tomas
 *
 */
public class TagDouble extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = -7131500564607511285L;
	/**
	 * The value held in this tag.
	 */
	private double data;
	
	/**
	 * Constructs a new double tag.
	 * @param name The name of this tag.
	 * @param data The value of this tag.
	 */
	public TagDouble(String name, double data) {
		super(name, 0x09);
	}
	
	/**
	 * Gets the data stored in this tag.
	 * @return A double value.
	 */
	public double getData() {
		return data;
	}
	
	/**
	 * Sets the data stored in this tag.
	 * @param data A double value.
	 */
	public void setData(double data) {
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
	public TagDouble clone() {
		return new TagDouble(name, getData());
	}

}

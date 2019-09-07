package io.github.tomaso2468.rpgonline.abt;

/**
 * A tag holding a float value.
 * @author Tomas
 *
 */
public class TagFloat extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 878957984413787929L;
	/**
	 * The data held in this tag.
	 */
	private float data;
	
	/**
	 * Constructs a new float tag.
	 * @param name The name of this tag.
	 * @param data The value of this tag.
	 */
	public TagFloat(String name, float data) {
		super(name, 0x08);
	}
	
	/**
	 * Gets the data held in this tag.
	 * @return A float value.
	 */
	public float getData() {
		return data;
	}
	
	/**
	 * Sets the data held in this tag.
	 * @param data A float value.
	 */
	public void setData(float data) {
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
	public TagFloat clone() {
		return new TagFloat(name, getData());
	}

}

package io.github.tomaso2468.rpgonline.abt;

import java.math.BigDecimal;

/**
 * A tag holding a BigDecimal
 * @author Tomas
 *
 */
@Deprecated
public class TagBigFloat extends TagString {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 7745491369069382304L;

	/**
	 * Constructs a TagBigFloat from a string.
	 * @param name The name of the tag.
	 * @param data The number to hold as a decimal string.
	 */
	public TagBigFloat(String name, String data) {
		super(name, data);
	}
	
	/**
	 * Constructs a TagBigFloat from a BigDecimal.
	 * @param name The name of the tag.
	 * @param data The number to hold as a BigDecimal.
	 */
	public TagBigFloat(String name, BigDecimal v) {
		super(name, v.toString());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getType() {
		return 0x0A;
	}

	/**
	 * Converts this tag to a BigDecimal.
	 * @return A BigDecimal object.
	 */
	public BigDecimal toBigDecimal() {
		return new BigDecimal(getData());
	}
	
	/**
	 * Sets the value of this tag.
	 * @param v A BigDecimal object.
	 */
	public void set(BigDecimal v) {
		setData(v.toString());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagBigFloat clone() {
		return new TagBigFloat(name, getData());
	}
}

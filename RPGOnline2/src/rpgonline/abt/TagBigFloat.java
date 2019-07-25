package rpgonline.abt;

import java.math.BigDecimal;

public class TagBigFloat extends TagString {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 7745491369069382304L;

	public TagBigFloat(String name, String data) {
		super(name, data);
	}
	
	public TagBigFloat(String name, BigDecimal v) {
		super(name, v.toString());
	}
	
	@Override
	public byte getType() {
		return 0x0A;
	}

	public BigDecimal toBigDecimal() {
		return new BigDecimal(getData());
	}
	
	public void set(BigDecimal v) {
		setData(v.toString());
	}
	
	@Override
	public TagBigFloat clone() {
		return new TagBigFloat(name, getData());
	}
}

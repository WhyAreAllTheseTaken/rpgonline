package rpgonline.abt;

import java.math.BigDecimal;

public class TagBigFloat extends TagString {
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
}

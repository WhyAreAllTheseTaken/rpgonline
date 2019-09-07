package io.github.tomaso2468.rpgonline.abt;

import java.math.BigInteger;

@Deprecated
public class TagBigInt extends TagByteArray {

	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 5935071441164263751L;

	public TagBigInt(String name, byte[] data) {
		super(name, data);
	}
	
	public TagBigInt(String name, BigInteger v) {
		super(name, v.toByteArray());
	}
	
	@Override
	public byte getType() {
		return 0x07;
	}

	public BigInteger toBigInt() {
		return new BigInteger(getData());
	}
	
	public void set(BigInteger v) {
		setData(v.toByteArray());
	}
	
	@Override
	public TagBigInt clone() {
		return new TagBigInt(name, getData());
	}
}

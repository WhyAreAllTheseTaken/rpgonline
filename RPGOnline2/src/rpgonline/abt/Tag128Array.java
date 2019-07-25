package rpgonline.abt;

public class Tag128Array extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 1678003219817661274L;

	/**
	 * Constructs an array of 128bit tags.
	 * @param name The tag name.
	 * @param data An array of the big-endian byte data for this array.
	 */
	public Tag128Array(String name, byte[][] data) {
		super(name);
		
		setData(data);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getType() {
		return 0x11;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof Tag128)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	/**
	 * Sets the value of this tag.
	 * @param data An array of the big-endian byte data for this array.
	 */
	public void setData(byte[][] data) {
		clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new Tag128(i + "", data[i]));
		}
	}
	
	/**
	 * Gets the value of this tag.
	 * @return An array of the big-endian byte data for this array.
	 */
	public byte[][] getData() {
		byte[][] data = new byte[size()][];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((Tag128) tags.get(i)).getData();
		}
		
		return data;
	}
	
	/**
	 * Sets the value at the specified index to the specified value.
	 * @param index An index.
	 * @param v The big-endian byte data for this number.
	 */
	public void set(int index, byte[] v) {
		((Tag128) getTag(index + "")).setData(v);
	}
	
	/**
	 * Gets the value at the specified index.
	 * @param index The index to get.
	 * @return The big-endian byte data for the number at the index.
	 */
	public byte[] get(int index) {
		return ((Tag128) getTag(index + "")).getData();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag128Array clone() {
		return new Tag128Array(name, getData());
	}
}
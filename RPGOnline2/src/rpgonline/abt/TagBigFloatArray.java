package rpgonline.abt;

/**
 * A tag holding an array of BigDecimals.
 * @author Tomas
 *
 */
@Deprecated
public class TagBigFloatArray extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 2758615385190637862L;

	/**
	 * Constructs a new TagBigFloatArray.
	 * @param name The name of the tag.
	 * @param data The data as string to be held in this array.
	 */
	public TagBigFloatArray(String name, String[] data) {
		super(name);
		
		setData(data);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte getType() {
		return 0x15;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagString)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	/**
	 * Sets the data of this tag.
	 * @param data The data to set.
	 */
	public void setData(String[] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagString(i + "", data[i]));
		}
	}
	
	/**
	 * Gets the data of this tag.
	 * @return The data held in this tag.
	 */
	public String[] getData() {
		String[] data = new String[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagString) tags.get(i)).getData();
		}
		
		return data;
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
		
		String[] data = new String[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = "\"" + sanitiseJSON(((TagString) tags.get(i)).getData()) + "\"";
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	/**
	 * Sets the value in one of the tags held in this array.
	 * @param index
	 * @param v
	 */
	public void set(int index, String v) {
		((TagString) getTag(index + "")).setData(v);
	}
	
	public String get(int index) {
		return ((TagString) getTag(index + "")).getData();
	}
	
	@Override
	public TagBigFloatArray clone() {
		return new TagBigFloatArray(name, getData());
	}
}

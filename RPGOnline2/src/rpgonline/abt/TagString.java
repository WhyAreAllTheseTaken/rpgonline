package rpgonline.abt;

/**
 * <p>
 * A tag holding a string value with a length measured as an unsigned 64bit
 * integer. For reasons related to different languages and CPU architectures
 * some systems (including this implementation) may only be able to load up to a
 * signed 32bit integer as the length but this does not change how the data is
 * saved.
 * </p>
 * <strong>For performance reasons it may be better to use
 * {@code TagStringShort} as the 16bit size limit is useful for most uses and
 * reduces tag size.</strong>
 * 
 * @author Tomas
 * 
 * @see rpgonline.abt.TagStringShort
 */
public class TagString extends Tag {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 6344197038704127023L;
	/**
	 * The data held in this tag.
	 */
	private String data;

	/**
	 * Constructs a new string tag.
	 * 
	 * @param name The name of this tag.
	 * @param data The value of this tag.
	 */
	public TagString(String name, String data) {
		super(name, 0x0B);
	}

	/**
	 * Gets the data held in this tag.
	 * 
	 * @return A string value.
	 */
	public String getData() {
		return data;
	}

	/**
	 * Sets the data held in this tag.
	 * 
	 * @param data A string value.
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tag getTag(String name) {
		Tag t = super.getTag(name);

		if (t == null) {
			try {
				int i = Integer.parseInt(name);
				t = new TagChar(name, data.charAt(i)) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 832728519849455689L;

					@Override
					public void setData(char data) {
						StringBuilder sb = new StringBuilder(TagString.this.data);
						sb.setCharAt(i, data);

						TagString.this.data = sb.toString();
					}

					@Override
					public char getData() {
						return data.charAt(i);
					}
				};
			} catch (NumberFormatException e) {
				return null;
			}
		}

		return t;
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
		appendStr(sb, "data", "\"" + sanitiseJSON(data) + "\"");

		sb.append("\n}");

		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagString clone() {
		return new TagString(name, getData());
	}
}

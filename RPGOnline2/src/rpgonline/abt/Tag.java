package rpgonline.abt;

import java.io.Serializable;

/**
 * A representation of an ABTTag.
 * 
 * @author Tomas
 */
public abstract class Tag implements Serializable, Cloneable {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 1432761640748613837L;
	/**
	 * The name of the tag.
	 */
	protected String name;
	/**
	 * The type of the tag.
	 */
	protected byte type;

	/**
	 * Constructs a new Tag.
	 * @param name The name of the tag.
	 * @param type The type of the tag.
	 */
	public Tag(String name, int type) {
		if(name == null) {
			throw new NullPointerException("name");
		}
		this.name = name;
		this.type = (byte) type;
	}

	/**
	 * Gets the name of the tag.
	 * @return A non-null string.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the tag.
	 * @param name A non-null string.
	 */
	public void setName(String name) {
		if(name == null) {
			throw new NullPointerException("name");
		}
		this.name = name;
	}

	/**
	 * Converts this tag to a string written in JSON format with new lines.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{\n");

		appendStr(sb, "name", getName());
		appendSep(sb);
		appendNum(sb, "type", getType());
		
		sb.append("\n}");

		return sb.toString();
	}

	/**
	 * Gets the type of this tag.
	 * @return A byte value.
	 */
	public byte getType() {
		return type;
	}

	/**
	 * A utility function for appending string data to JSON objects.
	 * @param sb The string builder to append to.
	 * @param key The variable name.
	 * @param v The value of the variable.
	 */
	protected static void appendStr(StringBuilder sb, String key, String v) {
		sb.append("\"");
		sb.append(sanitiseJSON(key));
		sb.append("\"");

		sb.append(":");

		sb.append("\"");
		sb.append(sanitiseJSON(v));
		sb.append("\"");
	}
	
	/**
	 * A utility function for appending number data to JSON objects.
	 * @param sb The string builder to append to.
	 * @param key The variable name.
	 * @param v The value of the variable.
	 */
	protected static void appendNum(StringBuilder sb, String key, double v) {
		sb.append("\"");
		sb.append(sanitiseJSON(key));
		sb.append("\"");

		sb.append(":");

		sb.append(v);
	}
	
	/**
	 * A utility function for appending boolean data to JSON objects.
	 * @param sb The string builder to append to.
	 * @param key The variable name.
	 * @param v The value of the variable.
	 */
	protected static void appendBool(StringBuilder sb, String key, boolean v) {
		sb.append("\"");
		sb.append(sanitiseJSON(key));
		sb.append("\"");

		sb.append(":");

		sb.append(v);
	}
	
	/**
	 * A utility function for appending array data to JSON objects.
	 * @param sb The string builder to append to.
	 * @param key The variable name.
	 * @param v The valid JSON to write.
	 */
	protected static void appendArray(StringBuilder sb, String key, String[] v) {
		sb.append("\"");
		sb.append(sanitiseJSON(key));
		sb.append("\"");

		sb.append(":");

		sb.append("[");
		
		for (int i = 0; i < v.length; i++) {
			sb.append(v[i]);
			if(i != v.length - 1) {
				sb.append(",\n");
			}
		}
		
		sb.append("]");
	}
	
	/**
	 * A utility function for appending JSON objects to JSON objects.
	 * @param sb The string builder to append to.
	 * @param key The variable name.
	 * @param v The valid JSON object to write.
	 */
	protected static void appendObject(StringBuilder sb, String key, String o) {
		sb.append("\"");
		sb.append(sanitiseJSON(key));
		sb.append("\"");

		sb.append(":");

		sb.append(o);
	}
	
	/**
	 * Appends a separator to a JSON object.
	 * @param sb The string builder to append to.
	 */
	protected static void appendSep(StringBuilder sb) {
		sb.append(",");
		sb.append("\n");
	}
	
	/**
	 * Santisises JSON strings by escaping special characters.
	 * @param in The string to sanitise.
	 * @return A sanitised string.
	 */
	protected static String sanitiseJSON(String in) {
		return in.replace("\\", "\\\\").replace("\b", "\\b").replace("\f", "\\f").replace("\n", "\\n")
				.replace("\r", "\\r").replace("\t", "\\t").replace("\"", "\\\"");
	}
	
	/**
	 * Gets a tag from within this tag.
	 * @param name The tag to find separated with {@code /}.
	 * @return A tag object or {@code null} if not tag could be found.
	 */
	public Tag getTag(String name) {
		String[] name2 = name.split("/");
		if(name2[0].equals("this")) {
			StringBuilder sb = new StringBuilder();
			
			for (int i = 1; i < name2.length; i++) {
				sb.append(name2[i]);
				
				if(i != name2.length - 1) {
					sb.append("/");
				}
			}
			
			return this.getTag(sb.toString());
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Tag clone() {
		return new Tag(name, type) {
			private static final long serialVersionUID = 4607805369849768459L;
		};
	}
}

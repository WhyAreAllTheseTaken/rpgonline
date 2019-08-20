package rpgonline.abt;

@Deprecated
public class TagFloatArray extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = -8424402304842808331L;

	public TagFloatArray(String name, float[] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x13;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagFloat)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(float[] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagFloat(i + "", data[i]));
		}
	}
	
	public float[] getData() {
		float[] data = new float[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagFloat) tags.get(i)).getData();
		}
		
		return data;
	}
	
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
			data[i] = ((TagFloat) tags.get(i)).getData() + "";
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	public void set(int index, float v) {
		((TagFloat) getTag(index + "")).setData(v);
	}
	
	public float get(int index) {
		return ((TagFloat) getTag(index + "")).getData();
	}
	
	@Override
	public TagFloatArray clone() {
		return new TagFloatArray(name, getData());
	}
}

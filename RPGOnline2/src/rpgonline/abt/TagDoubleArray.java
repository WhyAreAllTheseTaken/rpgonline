package rpgonline.abt;

public class TagDoubleArray extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = 3053649142957912892L;

	public TagDoubleArray(String name, double[] data) {
		super(name);
		
		setData(data);
	}

	@Override
	public byte getType() {
		return 0x14;
	}
	
	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagDouble)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(double[] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagDouble(i + "", data[i]));
		}
	}
	
	public double[] getData() {
		double[] data = new double[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagDouble) tags.get(i)).getData();
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
			data[i] = ((TagDouble) tags.get(i)).getData() + "";
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	public void set(int index, double v) {
		((TagDouble) getTag(index + "")).setData(v);
	}
	
	public double get(int index) {
		return ((TagDouble) getTag(index + "")).getData();
	}
	
	@Override
	public TagDoubleArray clone() {
		return new TagDoubleArray(name, getData());
	}
}

package rpgonline.abt;

import java.util.Arrays;

@Deprecated
class TagMatrix extends TagGroup {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = -7750785924888483349L;

	public TagMatrix(String name, double[][] data) {
		super(name);
	}

	@Override
	public boolean add(Tag e) {
		if (!(e instanceof TagDoubleArray)) {
			throw new IllegalArgumentException();
		}
		return super.add(e);
	}
	
	public void setData(double[][] data) {
		tags.clear();
		
		for (int i = 0; i < data.length; i++) {
			add(new TagDoubleArray(i + "", data[i]));
		}
	}
	
	public double[][] getData() {
		double[][] data = new double[size()][size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = ((TagDoubleArray) tags.get(i)).getData();
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
			data[i] = Arrays.toString(((TagDoubleArray) tags.get(i)).getData());
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	public void set(int x, int y, double v) {
		double[][] data = getData();
		data[x][y] = v;
		
		setData(data);
	}
	
	public double get(int x, int y) {
		return getData()[x][y];
	}
	
	@Override
	public TagMatrix clone() {
		return new TagMatrix(name, getData());
	}
}

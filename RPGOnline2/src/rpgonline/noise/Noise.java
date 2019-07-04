package rpgonline.noise;

public interface Noise {
	public default double get(double x) {
		return get(x, 0);
	}
	public default double get(double x, double y) {
		return get(x, y, 0);
	}
	public default double get(double x, double y, double z) {
		return get(x, y, z, 0);
	}
	public double get(double x, double y, double z, double w);
}

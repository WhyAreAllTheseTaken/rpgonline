package io.github.tomaso2468.rpgonline.noise;

/**
 * An API for 1D, 2D, 3D and 4D noise.
 * @author Tomas
 *
 */
public interface Noise {
	/**
	 * Gets the noise at the specified position.
	 * @param x The X position to get.
	 * @return A double value in the range 0..1.
	 */
	public default double get(double x) {
		return get(x, 0);
	}
	/**
	 * Gets the noise at the specified position.
	 * @param x The X position to get.
	 * @param y The Y position to get.
	 * @return A double value in the range 0..1.
	 */
	public default double get(double x, double y) {
		return get(x, y, 0);
	}
	/**
	 * Gets the noise at the specified position.
	 * @param x The X position to get.
	 * @param y The Y position to get.
	 * @param z The Z position to get.
	 * @return A double value in the range 0..1.
	 */
	public default double get(double x, double y, double z) {
		return get(x, y, z, 0);
	}
	/**
	 * Gets the noise at the specified position.
	 * @param x The X position to get.
	 * @param y The Y position to get.
	 * @param z The Z position to get.
	 * @param w The W position to get.
	 * @return A double value in the range 0..1.
	 */
	public double get(double x, double y, double z, double w);
}

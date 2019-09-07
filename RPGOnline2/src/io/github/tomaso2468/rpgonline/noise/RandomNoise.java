package io.github.tomaso2468.rpgonline.noise;

import java.util.Random;

/**
 * An implentation of seeded position based pseudo-random noise.
 * @author Tomas
 *
 */
public class RandomNoise implements Noise {
	/**
	 * The seed for this noise.
	 */
	private long seed;
	/**
	 * Constructs a new RandomNoise implentation.
	 * @param seed A seed.
	 */
	public RandomNoise(long seed) {
		this.seed = seed;
	}
	
	/**
	 * Constructs a new RandomNoise implentation with a random seed.
	 */
	public RandomNoise() {
		this(System.currentTimeMillis());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(double x, double y, double z, double w) {
		long seed = this.seed + (long) (x * Short.MAX_VALUE + y * Short.MAX_VALUE + z * Short.MAX_VALUE + w * Short.MAX_VALUE);
		Random r = new Random(seed);
		
		return r.nextDouble();
	}
}

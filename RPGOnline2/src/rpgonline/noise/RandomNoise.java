package rpgonline.noise;

import java.util.Random;

public class RandomNoise implements Noise {
	private long seed;
	public RandomNoise(long seed) {
		this.seed = seed;
	}
	
	@Override
	public double get(double x, double y, double z, double w) {
		long seed = this.seed + (long) (x * Short.MAX_VALUE + y * Short.MAX_VALUE + z * Short.MAX_VALUE + w * Short.MAX_VALUE);
		Random r = new Random(seed);
		
		return r.nextDouble();
	}
}

package rpgonline.noise;

import java.util.Random;

public class SimplexNoise implements Noise {
	public static final double SEED_AREA = 10000000;
	public double offsetX;
	public double offsetY;
	public double offsetZ;
	public double offsetW;
	public double scaleX;
	public double scaleY;
	public double scaleZ;
	public double scaleW;
	
	public SimplexNoise(long seed, double offsetX, double offsetY, double offsetZ, double offsetW, double scaleX, double scaleY, double scaleZ, double scaleW) {
		super();
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.offsetW = offsetW;
		
		Random r = new Random(seed);
		
		this.offsetX += r.nextDouble() * SEED_AREA;
		this.offsetY += r.nextDouble() * SEED_AREA;
		this.offsetZ += r.nextDouble() * SEED_AREA;
		this.offsetW += r.nextDouble() * SEED_AREA;
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
		this.scaleW = scaleW;
	}
	
	public SimplexNoise(long seed, double offsetX, double offsetY, double offsetZ, double scaleX, double scaleY, double scaleZ) {
		this(seed, offsetX, offsetY, offsetZ, 0, scaleX, scaleY, scaleZ, 1);
	}
	
	public SimplexNoise(long seed, double offsetX, double offsetY, double scaleX, double scaleY) {
		this(seed, offsetX, offsetY, 0, scaleX, scaleY, 1);
	}
	
	public SimplexNoise(long seed, double offsetX, double scaleX) {
		this(seed, offsetX, 0, scaleX, 1);
	}
	
	public SimplexNoise(long seed, double scaleX) {
		this(seed, 0, scaleX);
	}
	
	public SimplexNoise(double offsetX, double offsetY, double offsetZ, double offsetW, double scaleX, double scaleY, double scaleZ, double scaleW) {
		this(System.currentTimeMillis(), offsetX, offsetY, offsetZ, offsetW, scaleX, scaleY, scaleZ, scaleW);
	}
	
	public SimplexNoise(double offsetX, double offsetY, double offsetZ, double scaleX, double scaleY, double scaleZ) {
		this(System.currentTimeMillis(), offsetX, offsetY, offsetZ, 0, scaleX, scaleY, scaleZ, 1);
	}
	
	public SimplexNoise(double offsetX, double offsetY, double scaleX, double scaleY) {
		this(System.currentTimeMillis(), offsetX, offsetY, 0, scaleX, scaleY, 1);
	}
	
	public SimplexNoise(double offsetX, double scaleX) {
		this(System.currentTimeMillis(), offsetX, 0, scaleX, 1);
	}
	
	public SimplexNoise(double scaleX) {
		this(System.currentTimeMillis(), 0, scaleX);
	}
	
	public SimplexNoise(long seed) {
		this(seed, 1);
	}
	
	public SimplexNoise() {
		this(System.currentTimeMillis());
	}

	@Override
	public double get(double x, double y, double z, double w) {
		return (se.liu.itn.stegu.simplexnoise.SimplexNoise.noise((x + offsetX) * scaleX, (y + offsetY) * scaleY, (z + offsetZ) * scaleZ, (w + offsetW) * scaleW) + 1) / 2;
	}
	
	@Override
	public double get(double x, double y, double z) {
		return (se.liu.itn.stegu.simplexnoise.SimplexNoise.noise((x + offsetX) * scaleX, (y + offsetY) * scaleY, (z + offsetZ) * scaleZ) + 1) / 2;
	}
	
	@Override
	public double get(double x, double y) {
		return (se.liu.itn.stegu.simplexnoise.SimplexNoise.noise((x + offsetX) * scaleX, (y + offsetY) * scaleY) + 1) / 2;
	}
	
	@Override
	public double get(double x) {
		return (se.liu.itn.stegu.simplexnoise.SimplexNoise.noise((x + offsetX) * scaleX, 0) + 1) / 2;
	}
}

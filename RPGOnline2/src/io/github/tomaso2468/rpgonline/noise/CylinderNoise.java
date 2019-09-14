package io.github.tomaso2468.rpgonline.noise;

import org.apache.commons.math3.util.FastMath;

/**
 * An implentation that creates noise wrapped around a cylinder or circle (if in 1D).
 * @author Tomas
 *
 */
public class CylinderNoise extends SimplexNoise {
	/**
	 * The radius of the cylinder.
	 */
	private final double radius;
	
	public CylinderNoise(double radius) {
		super();
		this.radius = radius;
	}
	public CylinderNoise(double offsetX, double offsetY, double offsetZ, double offsetW, double scaleX, double scaleY,
			double scaleZ, double scaleW, double radius) {
		super(offsetX, offsetY, offsetZ, offsetW, scaleX, scaleY, scaleZ, scaleW);
		this.radius = radius;
	}
	public CylinderNoise(double offsetX, double offsetY, double offsetZ, double scaleX, double scaleY, double scaleZ, double radius) {
		super(offsetX, offsetY, offsetZ, scaleX, scaleY, scaleZ);
		this.radius = radius;
	}
	public CylinderNoise(double offsetX, double offsetY, double scaleX, double scaleY, double radius) {
		super(offsetX, offsetY, scaleX, scaleY);
		this.radius = radius;
	}
	public CylinderNoise(double offsetX, double scaleX, double radius) {
		super(offsetX, scaleX);
		this.radius = radius;
	}
	public CylinderNoise(double scaleX, double radius) {
		super(scaleX);
		this.radius = radius;
	}
	public CylinderNoise(long seed, double offsetX, double offsetY, double offsetZ, double offsetW, double scaleX,
			double scaleY, double scaleZ, double scaleW, double radius) {
		super(seed, offsetX, offsetY, offsetZ, offsetW, scaleX, scaleY, scaleZ, scaleW);
		this.radius = radius;
	}
	public CylinderNoise(long seed, double offsetX, double offsetY, double offsetZ, double scaleX, double scaleY,
			double scaleZ, double radius) {
		super(seed, offsetX, offsetY, offsetZ, scaleX, scaleY, scaleZ);
		this.radius = radius;
	}
	public CylinderNoise(long seed, double offsetX, double offsetY, double scaleX, double scaleY, double radius) {
		super(seed, offsetX, offsetY, scaleX, scaleY);
		this.radius = radius;
	}
	public CylinderNoise(long seed, double offsetX, double scaleX, double radius) {
		super(seed, offsetX, scaleX);
		this.radius = radius;
	}
	public CylinderNoise(long seed, double scaleX, double radius) {
		super(seed, scaleX);
		this.radius = radius;
	}
	public CylinderNoise(long seed, double radius) {
		super(seed);
		this.radius = radius;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(double x) {
		double a = x / (FastMath.PI * 2 * radius) * FastMath.PI * 2;
		
		double x2 = radius * FastMath.cos(a);
		double y2 = radius * FastMath.sin(a);
		
		return super.get(x2, y2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(double x, double y) {
		double a = x / (FastMath.PI * 2 * radius) * FastMath.PI * 2;
		
		double x2 = radius * FastMath.cos(a);
		double y2 = radius * FastMath.sin(a);
		
		return super.get(x2, y2, y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(double x, double y, double z) {
		double a = x / (FastMath.PI * 2 * radius) * FastMath.PI * 2;
		
		double x2 = radius * FastMath.cos(a);
		double y2 = radius * FastMath.sin(a);
		
		return super.get(x2, y2, y, z);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(double x, double y, double z, double w) {
		double a = x / (FastMath.PI * 2 * radius) * FastMath.PI * 2;
		
		double x2 = radius * FastMath.cos(a);
		double y2 = radius * FastMath.sin(a);
		
		return (super.get(x2, y2, y, z) + super.get(w)) / 2;
	}
	
}

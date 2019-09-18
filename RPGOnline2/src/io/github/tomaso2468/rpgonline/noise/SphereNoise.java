/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.noise;

import org.apache.commons.math3.util.FastMath;

/**
 * An implentation that creates noise wrapped around a sphere or circle (if in 1D).
 * @author Tomas
 */
public class SphereNoise extends SimplexNoise {
	private final double radius;
	
	public SphereNoise(double radius) {
		super();
		this.radius = radius;
	}
	public SphereNoise(double offsetX, double offsetY, double offsetZ, double offsetW, double scaleX, double scaleY,
			double scaleZ, double scaleW, double radius) {
		super(offsetX, offsetY, offsetZ, offsetW, scaleX, scaleY, scaleZ, scaleW);
		this.radius = radius;
	}
	public SphereNoise(double offsetX, double offsetY, double offsetZ, double scaleX, double scaleY, double scaleZ, double radius) {
		super(offsetX, offsetY, offsetZ, scaleX, scaleY, scaleZ);
		this.radius = radius;
	}
	public SphereNoise(double offsetX, double offsetY, double scaleX, double scaleY, double radius) {
		super(offsetX, offsetY, scaleX, scaleY);
		this.radius = radius;
	}
	public SphereNoise(double offsetX, double scaleX, double radius) {
		super(offsetX, scaleX);
		this.radius = radius;
	}
	public SphereNoise(double scaleX, double radius) {
		super(scaleX);
		this.radius = radius;
	}
	public SphereNoise(long seed, double offsetX, double offsetY, double offsetZ, double offsetW, double scaleX,
			double scaleY, double scaleZ, double scaleW, double radius) {
		super(seed, offsetX, offsetY, offsetZ, offsetW, scaleX, scaleY, scaleZ, scaleW);
		this.radius = radius;
	}
	public SphereNoise(long seed, double offsetX, double offsetY, double offsetZ, double scaleX, double scaleY,
			double scaleZ, double radius) {
		super(seed, offsetX, offsetY, offsetZ, scaleX, scaleY, scaleZ);
		this.radius = radius;
	}
	public SphereNoise(long seed, double offsetX, double offsetY, double scaleX, double scaleY, double radius) {
		super(seed, offsetX, offsetY, scaleX, scaleY);
		this.radius = radius;
	}
	public SphereNoise(long seed, double offsetX, double scaleX, double radius) {
		super(seed, offsetX, scaleX);
		this.radius = radius;
	}
	public SphereNoise(long seed, double scaleX, double radius) {
		super(seed, scaleX);
		this.radius = radius;
	}
	public SphereNoise(long seed, double radius) {
		super(seed);
		this.radius = radius;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(final double x) {
		double a = x / (FastMath.PI * 2 * radius) * FastMath.PI * 2;
		
		double x2 = radius * FastMath.cos(a);
		double y2 = radius * FastMath.sin(a);
		
		return super.get(x2, y2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(final double x, final double y) {
		double a = x / (FastMath.PI * 2 * radius) * FastMath.PI * 2;
		double a2 = y / (FastMath.PI * 2 * radius) * FastMath.PI * 2;
		
		// Rotation around the Z axis by a
		double x2 = radius * FastMath.cos(a);
		double y2 = radius * FastMath.sin(a);
		
		// Rotation around the X axis by a2
		double x3 = x2;
		double y3 = y2 * FastMath.cos(a2);
		double z3 = y2 * FastMath.sin(a2);
		
		return super.get(x3, y3, z3);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(final double x, final double y, final double z) {
		return (get(x, y) + get(z)) / 2;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get(final double x, final double y, final double z, final double w) {
		return (get(x, y) + get(z, w)) / 2;
	}
}

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

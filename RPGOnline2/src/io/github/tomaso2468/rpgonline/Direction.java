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
package io.github.tomaso2468.rpgonline;

import javax.annotation.Nonnull;

/**
 * An enum used to represent NESW directions. This class also provides based
 * geometry control for directions.
 * 
 * @author Tomas
 */
public enum Direction {
	SOUTH, NORTH, EAST, WEST,;
	/**
	 * Gets the distance along the x axis required to move in this direction at the
	 * inputed speed.
	 * 
	 * @param distance the distance to move.
	 * @return a float value to move along the x axis.
	 */
	public float getXD(float distance) {
		switch (this) {
		case EAST:
			return distance;
		case NORTH:
			return 0;
		case SOUTH:
			return 0;
		case WEST:
			return -distance;
		default:
			return 0;
		}
	}

	/**
	 * Gets the distance along the y axis required to move in this direction at the
	 * inputed speed.
	 * 
	 * @param distance the distance to move.
	 * @return a float value to move along the y axis.
	 */
	public float getYD(float distance) {
		switch (this) {
		case EAST:
			return 0;
		case NORTH:
			return -distance;
		case SOUTH:
			return distance;
		case WEST:
			return 0;
		default:
			return 0;
		}
	}

	/**
	 * Returns the inverse of this direction. e.g. {@code EAST.flip() = WEST}.
	 * 
	 * @return A direction.
	 */
	@Nonnull
	public Direction flip() {
		switch (this) {
		case EAST:
			return Direction.WEST;
		case NORTH:
			return Direction.SOUTH;
		case SOUTH:
			return Direction.NORTH;
		case WEST:
			return Direction.EAST;
		default:
			return Direction.NORTH;
		}
	}
}

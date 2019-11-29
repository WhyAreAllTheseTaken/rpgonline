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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DirectionTest {
	@Test
	void testGetXD() {
		for (float i = -10; i < 10; i++) {
			assertEquals(0, Direction.SOUTH.getXD(i));
			assertEquals(0, Direction.NORTH.getXD(i));
			assertEquals(i, Direction.EAST.getXD(i));
			assertEquals(-i, Direction.WEST.getXD(i));
		}
	}

	@Test
	void testGetYD() {
		for (float i = -10; i < 10; i++) {
			assertEquals(i, Direction.SOUTH.getYD(i));
			assertEquals(-i, Direction.NORTH.getYD(i));
			assertEquals(0, Direction.EAST.getYD(i));
			assertEquals(0, Direction.WEST.getYD(i));
		}
	}

	@Test
	void testFlip() {
		assertEquals(Direction.NORTH, Direction.SOUTH.flip());
		assertEquals(Direction.SOUTH, Direction.NORTH.flip());
		assertEquals(Direction.EAST, Direction.WEST.flip());
		assertEquals(Direction.WEST, Direction.EAST.flip());
	}

}

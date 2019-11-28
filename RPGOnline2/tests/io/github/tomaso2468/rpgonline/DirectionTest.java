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

package rpgonline;

import org.apache.commons.math3.util.FastMath;

/**
 * An enum used to represent NESW directions. This class also provides based
 * geometry control for directions.
 * 
 * @author Tomas
 */
public enum Direction {
	NORTH, SOUTH, EAST, WEST,;
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

	/**
	 * Returns the inverse of this direction 90% of the time. May also return a
	 * direction perpendicular to this direction. e.g. {@code EAST.flip() ~= WEST}.
	 * 
	 * @return A direction.
	 */
	public Direction bounce() {
		switch (this) {
		case EAST:
			if (FastMath.random() < 0.05) {
				return Direction.NORTH;
			}
			if (FastMath.random() < 0.1) {
				return Direction.SOUTH;
			}
			return Direction.WEST;
		case NORTH:
			if (FastMath.random() < 0.05) {
				return Direction.EAST;
			}
			if (FastMath.random() < 0.1) {
				return Direction.WEST;
			}
			return Direction.SOUTH;
		case SOUTH:
			if (FastMath.random() < 0.05) {
				return Direction.EAST;
			}
			if (FastMath.random() < 0.1) {
				return Direction.WEST;
			}
			return Direction.NORTH;
		case WEST:
			if (FastMath.random() < 0.05) {
				return Direction.NORTH;
			}
			if (FastMath.random() < 0.1) {
				return Direction.SOUTH;
			}
			return Direction.EAST;
		default:
			return Direction.NORTH;
		}
	}
}

package io.github.tomaso2468.rpgonline;

/**
 * An interface to allow methods to reference any loading screen.
 * 
 * @author Tomas
 */
public interface LoadCounter {
	/**
	 * Set the current loading value (in steps).
	 * 
	 * @param v a long in the range {@code 0} to {@code Long.MAX_VALUE}
	 */
	public void setValue(long v);

	/**
	 * Gets the current loading value (in steps).
	 * 
	 * @return a long in the range {@code 0} to {@code Long.MAX_VALUE}
	 */
	public long getValue();

	/**
	 * Increases the loading value by 1.
	 */
	public default void increment() {
		setValue(getValue() + 1);
	}

	/**
	 * Sets the maximum loading value (The value that is defined as 100% loaded).
	 * 
	 * @param v a long in the range {@code 0} to {@code Long.MAX_VALUE}
	 */
	public void setMax(long v);

	/**
	 * Gets the maximum loading value (The value that is defined as 100% loaded).
	 * 
	 * @return a long in the range {@code 0} to {@code Long.MAX_VALUE}
	 */
	public long getMax();
}

package io.github.tomaso2468.rpgonline.state;

/**
 * An interface allowing a state to except a float scaling value.
 * @author Tomas
 *
 */
public interface BaseScaleState {
	/**
	 * Scale the state to the specified scaling (1 == 100%).
	 * @param base The scaling for the state.
	 */
	public void scale(float base);
}

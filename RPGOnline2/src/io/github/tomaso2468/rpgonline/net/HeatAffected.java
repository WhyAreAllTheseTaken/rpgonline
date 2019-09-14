package io.github.tomaso2468.rpgonline.net;

/**
 * Indicates that the heat effect is supported in this client.
 * @author Tomas
 *
 */
public interface HeatAffected {
	/**
	 * Gets the strength of the heat effect.
	 * @return A float value.
	 */
	public float getHeatEffect();
}

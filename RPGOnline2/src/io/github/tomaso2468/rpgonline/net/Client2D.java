package io.github.tomaso2468.rpgonline.net;

import java.util.List;

import io.github.tomaso2468.rpgonline.abt.TagGroup;
import io.github.tomaso2468.rpgonline.world.World;

public interface Client2D extends Client {
	/**
	 * Gets the world that should be rendered.
	 * @return
	 */
	public World getWorld();
	/**
	 * Gets the X position of the player camera.
	 * @return a double value.
	 */
	public double getPlayerX();
	/**
	 * Gets the Y position of the player camera.
	 * @return a double value.
	 */
	public double getPlayerY();
	/**
	 * Gets the strength of the heat effect.
	 * @return A float value.
	 */
	public float getHeatEffect();
	/**
	 * Gets the strength of the wind effect. A negative number will reverse the wind.
	 * @return A float value.
	 */
	public float getWind();
	/**
	 * Called to indicate that the player has walked in the X axis.
	 * @param s The distance the player has walked (1 for button presses). This value will be different for control systems that are analogue.
	 * @param delta The time since the last game update in seconds.
	 */
	public void walkY(double s, double delta);
	/**
	 * Called to indicate that the player has walked in the Y axis.
	 * @param s The distance the player has walked (1 for button presses). This value will be different for control systems that are analogue.
	 * @param delta The time since the last game update in seconds.
	 */
	public void walkX(double s, double delta);
	/**
	 * Indicates a request from the world for a chunk at the specified position.
	 * @param x The X position of the chunk
	 * @param y The Y position of the chunk
	 * @param z The Z position of the chunk
	 * 
	 * @see io.github.tomaso2468.rpgonline.world.ABTWorld
	 * @see io.github.tomaso2468.rpgonline.world.ABTNetWorld
	 */
	public void requestChunk(long x, long y, long z);
	/**
	 * Gets the list of received requested chunks.
	 * @return A list of tag groups representing chunk data.
	 */
	public List<TagGroup> getRequestedChunks();
	/**
	 * Sets if the sprint controls are enabled.
	 * @param s {@code true} if sprint is pressed, {@code false} otherwise.
	 */
	public void setSprint(boolean s);
}

package rpgonline.part;

import java.util.List;

import org.newdawn.slick.Graphics;

import rpgonline.texture.TextureMap;
import rpgonline.world.World;

/**
 * An interface representing a particle.
 * @author Tomas
 *
 */
public interface Particle {
	/**
	 * Determines if the particle uses custom rendering.
	 * @return {@code true} if the particle uses custom rendering, {@code false} otherwise.
	 */
	public default boolean isCustom() {
		return false;
	}
	
	/**
	 * Determines if the particle is affected by light.
	 * @return{@code true} if the particle is affected by light, {@code false} otherwise.
	 */
	public default boolean isLightAffected() {
		return true;
	}
	
	/**
	 * Gets the texture of this particle.
	 * @return A texture ID.
	 */
	public default int getTexture() {
		return -1;
	}
	
	/**
	 * Renders the particle.
	 * @param g The current graphics context.
	 * @param sx The X position to render at.
	 * @param sy The Y position to render at.
	 */
	public default void render(Graphics g, float sx, float sy) {
		g.drawImage(TextureMap.getTexture(getTexture()), sx, sy);
	}
	/**
	 * Gets the X position of this particle.
	 * @return A float value.
	 */
	public float getX();
	/**
	 * Gets the Y position of this particle.
	 * @return A float value.
	 */
	public float getY();
	
	/**
	 * Performs behaviour calculations on this particle.
	 * @param w The world the particle is in.
	 * @param wind The current wind value.
	 * @param particles The list of all particles.
	 * @param delta The time since the last update in seconds.
	 */
	public default void doBehaviour(World w, float wind, List<Particle> particles, float delta) {
		
	}
	
	/**
	 * Gets the transparency of this particle.
	 * @return A float value in the range 0..1.
	 */
	public float getAlpha();
}

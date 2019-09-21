package io.github.tomaso2468.rpgonline.render;

/**
 * A class for managing render systems.
 * @author Tomas
 *
 */
public final class RenderManager {
	/**
	 * Prevent instantiation.
	 */
	private RenderManager() {
		
	}

	/**
	 * The renderer.
	 */
	private static Renderer renderer;
	
	/**
	 * Gets the renderer.
	 * @return A renderer object.
	 */
	public static Renderer getRenderer() {
		return renderer;
	}

	/**
	 * Sets the renderer.
	 * @param renderer A renderer object.
	 */
	public static void setRenderer(Renderer renderer) {
		RenderManager.renderer = renderer;
	}
}

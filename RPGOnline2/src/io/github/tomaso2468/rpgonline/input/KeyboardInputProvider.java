package io.github.tomaso2468.rpgonline.input;

/**
 * An interface for a provider for keyboard input.
 * @author Tomas
 *
 */
public interface KeyboardInputProvider {
	/**
	 * Gets the key code for the specified action.
	 * @param s The binding to get.
	 * @return A key code.
	 */
	public int getKeyCodeForAction(String s);
	
	/**
	 * Bind the command to the specified key.
	 * @param s The command to bind.
	 * @param code The key code to bind to.
	 */
	public void bind(String s, int code);
}

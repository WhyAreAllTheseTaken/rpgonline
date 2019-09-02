package rpgonline.input;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Input;

import rpgonline.RPGConfig;

/**
 * A class with utilities for input.
 * @author Tomas
 *
 */
public final class InputUtils {
	/**
	 Prevent instantiation
	 */
	private InputUtils() {
		
	}
	/**
	 * The binding for walking north.
	 */
	public static final String WALK_NORTH = "walk_north";
	/**
	 * The binding for walking east.
	 */
	public static final String WALK_EAST = "walk_east";
	/**
	 * The binding for walking south.
	 */
	public static final String WALK_SOUTH = "walk_south";
	/**
	 * The binding for walking west.
	 */
	public static final String WALK_WEST = "walk_west";
	/**
	 * The binding for exiting the game.
	 */
	public static final String EXIT = "exit";
	/**
	 * The binding for toggling the GUI.
	 */
	public static final String GUI_TOGGLE = "gui_toggle";
	/**
	 * The binding for the sprint the key.
	 */
	public static final String SPRINT = "sprint";
	/**
	 * The binding for moving north in the bullet menu.
	 */
	public static final String BULLET_NORTH = "bullet_north";
	/**
	 * The binding for moving east in the bullet menu.
	 */
	public static final String BULLET_EAST = "bullet_east";
	/**
	 * The binding for moving south in the bullet menu.
	 */
	public static final String BULLET_SOUTH = "bullet_south";
	/**
	 * The binding for moving west in the bullet menu.
	 */
	public static final String BULLET_WEST = "bullet_west";
	
	/**
	 * Determines if the binding is pressed.
	 * @param in The current input.
	 * @param func The binding to check.
	 * @return {@code true} if the binding is pressed, {@code false} otherwise.
	 */
	public static boolean isActionPressed(Input in, String func) {
		if (in.getControllerCount() > 0) {
			return Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(func)) 
					|| ControllerInputProvider.isButtonPressed(in, RPGConfig.getControllerInput().getBinding(func));
		} else {
			return Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(func));
		}
	}
	
}

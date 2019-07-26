package rpgonline.input;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Input;

import rpgonline.RPGConfig;

public final class InputUtils {
	/**
	 Prevent instantiation
	 */
	private InputUtils() {
		
	}
	public static final String WALK_NORTH = "walk_north";
	public static final String WALK_EAST = "walk_east";
	public static final String WALK_SOUTH = "walk_south";
	public static final String WALK_WEST = "walk_west";
	public static final String EXIT = "exit";
	public static final String GUI_TOGGLE = "gui_toggle";
	public static final String SPRINT = "sprint";
	public static final String BULLET_NORTH = "bullet_north";
	public static final String BULLET_EAST = "bullet_east";
	public static final String BULLET_SOUTH = "bullet_south";
	public static final String BULLET_WEST = "bullet_west";
	
	public static boolean isActionPressed(Input in, String func) {
		if (in.getControllerCount() > 0) {
			return Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(func)) 
					|| ControllerInputProvider.isButtonPressed(in, RPGConfig.getControllerInput().getBinding(func));
		} else {
			return Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(func));
		}
	}
	
}

package rpgonline;

import rpgonline.input.ControllerInputProvider;
import rpgonline.input.KeyboardInputProvider;
import rpgonline.input.MapControllerProvider;
import rpgonline.input.MapKeyProvider;

public class RPGConfig {
	private static int autoSpriteMapSize = 1024;
	private static boolean soundSystemDaemon = false;
	private static boolean wind = false;
	private static boolean shadow = false;
	private static int tileSize = 16;
	private static KeyboardInputProvider keyInput = new MapKeyProvider();
	private static float controllerActuation = 0.5f;
	private static ControllerInputProvider controllerInput = new MapControllerProvider();

	public static int getAutoSpriteMapSize() {
		return autoSpriteMapSize;
	}

	public static void setAutoSpriteMapSize(int autoSpriteMapSize) {
		RPGConfig.autoSpriteMapSize = autoSpriteMapSize;
	}

	public static boolean isSoundSystemDaemon() {
		return soundSystemDaemon;
	}

	public static void setSoundSystemDaemon(boolean soundSystemDaemon) {
		RPGConfig.soundSystemDaemon = soundSystemDaemon;
	}

	public static boolean isWind() {
		return wind;
	}

	public static void setWind(boolean wind) {
		RPGConfig.wind = wind;
	}

	public static boolean isShadow() {
		return shadow;
	}

	public static void setShadow(boolean shadow) {
		RPGConfig.shadow = shadow;
	}
	
	public static int getTileSize() {
		return tileSize;
	}
	
	public static void setTileSize(int tileSize) {
		RPGConfig.tileSize = tileSize;
	}

	public static KeyboardInputProvider getKeyInput() {
		return keyInput;
	}

	public static void setKeyInput(KeyboardInputProvider keyInput) {
		RPGConfig.keyInput = keyInput;
	}

	public static float getControllerActuation() {
		return controllerActuation;
	}

	public static void setControllerActuation(float controllerActuation) {
		RPGConfig.controllerActuation = controllerActuation;
	}

	public static ControllerInputProvider getControllerInput() {
		return controllerInput;
	}

	public static void setControllerInput(ControllerInputProvider controllerInput) {
		RPGConfig.controllerInput = controllerInput;
	}
}

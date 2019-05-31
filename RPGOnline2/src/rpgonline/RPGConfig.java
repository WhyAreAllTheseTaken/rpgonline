package rpgonline;

public class RPGConfig {
	private static int autoSpriteMapSize = 1024;
	private static boolean soundSystemDaemon = false;

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
}

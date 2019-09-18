package io.github.tomaso2468.rpgonline.gui.theme;

/**
 * The manager for GUI themes.
 * @author Tomas
 */
public class ThemeManager {
	/**
	 * The current theme.
	 */
	private static Theme theme = new DefaultTheme();

	/**
	 * Gets the current theme.
	 * @return A theme object.
	 */
	public static Theme getTheme() {
		return theme;
	}

	/**
	 * Sets the current theme.
	 * @param theme A theme object.
	 */
	public static void setTheme(Theme theme) {
		ThemeManager.theme = theme;
	}
}

package io.github.tomaso2468.rpgonline.gui.theme;

public class ThemeManager {
	private static Theme theme = new DefaultTheme();

	public static Theme getTheme() {
		return theme;
	}

	public static void setTheme(Theme theme) {
		ThemeManager.theme = theme;
	}
}

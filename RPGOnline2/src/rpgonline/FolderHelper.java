package rpgonline;

import java.io.File;

public class FolderHelper {
	public static File getAppDataFolder() {
		String os = System.getProperty("os.name").toLowerCase();
		
		if (os.contains("windows")) {
			return new File(System.getenv("APPDATA"));
		}
		
		if (os.contains("mac")) {
			return new File(System.getProperty("user.home"), "Library/Application Support");
		}
		
		return new File(System.getProperty("user.home"), ".rpgonline");
	}
	public static File createAppDataFolder(String... name) {
		File folder = getAppDataFolder();
		
		File f = folder;
		
		for (String s : name) {
			f = new File(f, s);
		}
		
		f.mkdirs();
		
		return f;
	}
}

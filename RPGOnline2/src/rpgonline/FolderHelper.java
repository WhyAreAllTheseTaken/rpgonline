package rpgonline;

import java.io.File;

import javax.annotation.Nonnull;

/**
 * A class to assist with storing save files.
 * @author Tomas
 */
public final class FolderHelper {
	/**
	 * Prevent instantiation
	 */
	private FolderHelper() {
		
	}
	/**
	 * <p>Gets a file representing a folder suitable for storing program save files / config files.</p>
	 * <p>The currently supported platforms are listed below:
	 * <table>
	 * <tr><td>Windows</td><td>%appdata%</td></tr>
	 * <tr><td>MacOS</td><td>~/Library/Application Support/</td></tr>
	 * <tr><td>Linux/Other systems</td><td>~/.rpgonline</td></tr>
	 * </table>
	 * @return A file representing a directory (that may not exist).
	 */
	@Nonnull
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
	/**
	 * <p>Gets a file representing a specific folder with a given name suitable for storing program save files / config files.</p>
	 * <p>The currently supported platforms are listed below:
	 * <table>
	 * <tr><td>Windows</td><td>%appdata%\name</td></tr>
	 * <tr><td>MacOS</td><td>~/Library/Application Support/name</td></tr>
	 * <tr><td>Linux/Other systems</td><td>~/.rpgonline/name</td></tr>
	 * </table>
	 * @return A file representing a directory that exists.
	 */
	@Nonnull
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

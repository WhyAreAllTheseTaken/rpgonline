package rpgonline;

import java.util.Properties;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

/**
 * A class that represents version info of the RPGOnline library.
 * 
 * @author Tomas
 */
public final class RPGOnline {
	/**
	 * A list of all previous versions of this library. This includes the current
	 * version.
	 */
	public static final Version[] VERSIONS = { new Version("0.0.0-d0"), new Version("0.1.0-d0"),
			new Version("0.1.1-d0"), new Version("0.1.2-d0"), new Version("0.1.3-d0"), new Version("0.1.3-d1"),
			new Version("0.1.4-d0"), new Version("0.1.5-d0"), new Version("0.1.6-d0"), new Version("0.2.0-d0"),
			new Version("0.3.0-d0+1549127426"), new Version("0.3.1-d0+1549134859"), new Version("0.3.2-d0+1549194835"),
			new Version("0.3.3-d0+1549907231"), new Version("0.3.3+1550312026"), new Version("0.3.3-p1+1550313026"),
			new Version("0.3.4+1550318158"), new Version("0.3.5+1551121021"), new Version("0.3.6+1551364566"),
			new Version("0.3.7+1551629276"), new Version("0.3.8+1551632798"), new Version("0.3.9+1551724618"),
			new Version("0.3.10+1552726489"), new Version("0.3.11+1554057287"), new Version("0.3.12+1554479444"),
			new Version("0.4.0+1555165066"), new Version("0.5.0+1556906194"), new Version("0.6.0+1557583232"),
			new Version("0.6.1+1557657120"), new Version("0.6.2+1557659837"), new Version("0.6.3+1557771460"),
			new Version("0.6.4+1557774208"), new Version("0.6.5+1557849408") };
	/**
	 * The current version of the RPGOnline library.
	 */
	public static final Version VERSION = new Version("0.7.0-d1+1559845313");
	/**
	 * The version of java that this library was compiled with.
	 */
	public static final Version JAVA_BUILD_VERSION = new Version("1.8.0_191");
	/**
	 * The current java version used by the JRE.
	 */
	public static final Version JAVA_VERSION = new Version(System.getProperty("java.version"));
	/**
	 * The minimum version that is required for java to work. Versions below this
	 * may still work but are unlikely to even run.
	 */
	public static final Version MIN_SUPPORTED_VERSION = new Version("1.8.0");

	/**
	 * The current version of LWJGL.
	 */
	public static final Version LWJGL_VERSION = new Version(org.lwjgl.Sys.getVersion());

	/**
	 * The current version of Slick2D.
	 */
	public static final Version SLICK_VERSION;
	
	//Load anything that requires a method call.
	static {
		int build = 0;
		try {
			Properties props = new Properties();
			props.load(ResourceLoader.getResourceAsStream("version"));

			build = Integer.parseInt(props.getProperty("build"));
		} catch (Exception e) {
			Log.error("Unable to determine Slick build number");
			build = 0;
		}
		SLICK_VERSION = new Version("1." + build);
	}

	/**
	 * The current OpenGL version.
	 */
	private static Version OPENGL_VERSION;

	/**
	 * Prints version info and displays warnings in the case of unsupported java
	 * versions.
	 */
	public static void queryVersionData() {
		Log.info("RPGOnline version: " + RPGOnline.VERSION.toDatedString());
		Log.info("RPGOnline java build version: " + RPGOnline.JAVA_BUILD_VERSION);
		Log.info("Java version: " + RPGOnline.JAVA_VERSION);
		Log.info("LWJGL version: " + RPGOnline.LWJGL_VERSION);
		Log.info("OpenGL version: " + getOpenGLVersion());
		Log.info("Slick2D version: " + SLICK_VERSION);
		if (JAVA_VERSION.compareTo(JAVA_BUILD_VERSION) < 0) {
			Log.warn(
					"The version of java in use is older than the version used to build the engine. Some problems may occur.");
		}
		if (JAVA_VERSION.compareTo(MIN_SUPPORTED_VERSION) < 0) {
			Log.warn("Java 8 or higher is required for this program to operate correctly.");
		}
	}

	/**
	 * Gets the current OpenGL version.
	 * @return A version object.
	 */
	public static Version getOpenGLVersion() {
		if (OPENGL_VERSION == null) {
			OPENGL_VERSION = new Version(GL11.glGetString(GL11.GL_VERSION).substring(0, 3));
		}
		return OPENGL_VERSION;
	}
}

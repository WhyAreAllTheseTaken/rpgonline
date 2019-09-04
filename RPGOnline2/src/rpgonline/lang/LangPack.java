package rpgonline.lang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.newdawn.slick.util.Log;

import rpgonline.RPGConfig;

/**
 * A class holding language data.
 * 
 * @author Tomas
 *
 */
public class LangPack {
	/**
	 * The files for this pack.
	 */
	private Properties[] data;

	/**
	 * Constructs a new language pack from the files specified.
	 * 
	 * @param src The files to load from.
	 */
	public LangPack(String[] src) {
		List<Properties> ldata = new ArrayList<Properties>();

		for (int i = 0; i < src.length; i++) {
			Log.debug("Loading language file " + src[i]);
			Properties p = new Properties();
			try {
				p.load(LangPack.class.getResourceAsStream(src[i]));
			} catch (IOException | NullPointerException e) {
				Log.warn("Error loading .lang file. Probably a lack of overrides", e);
				continue;
			}
			ldata.add(p);
		}

		data = ldata.toArray(new Properties[ldata.size()]);

		Log.debug("Loaded " + getCode());
	}

	/**
	 * Gets the files for the specified language.
	 * 
	 * @param locs The file suffixes to load.
	 * @param id   The language ID.
	 * @param id2  The country ID.
	 */
	private static String[] compile(String[] locs, String id, String id2) {
		String[] locs2 = new String[locs.length * 2];

		for (int i = 0; i < locs.length; i++) {
			locs2[i] = "/lang/" + id + "/" + id2 + "/" + locs[i] + ".lang";
		}
		for (int i = 0; i < locs.length; i++) {
			locs2[i + locs.length] = "/lang/" + id + "/" + locs[i] + ".lang";
		}

		return locs2;
	}

	/**
	 * Constructs a new language pack.
	 * 
	 * @param id  The language ID.
	 * @param id2 The region ID.
	 */
	public LangPack(String id, String id2) {
		this(compile(RPGConfig.getLangloc(), id, id2));
	}

	/**
	 * Constructs a new language pack.
	 * 
	 * @param id The pack ID.
	 */
	public LangPack(String id) {
		this(id.split("-")[0], id.split("-")[1]);
	}

	/**
	 * Finds the translation for the string with the specified ID.
	 * 
	 * @param s The string ID to translate.
	 * @return A translated string.
	 */
	public String translate(String s) {
		for (Properties p : data) {
			if (p.containsKey(s)) {
				return p.getProperty(s);
			}
		}
		return s;
	}

	/**
	 * Gets the code for this pack.
	 * 
	 * @return A string.
	 */
	public String getCode() {
		return translate("lang.code") + "-" + translate("lang.code2");
	}

	/**
	 * <p>
	 * Gets the language name of this pack.
	 * </p>
	 * <p>
	 * <b>For this method to work the properties {@code lang.name} and
	 * {@code lang.region} must be set.</b>
	 * </p>
	 * 
	 * @return A human readable language name.
	 */
	public String getName() {
		return translate("lang.name") + " (" + translate("lang.region") + ")";
	}

	/**
	 * Gets the voice clip location of the clip with the specified ID.
	 * @param id A voice clip ID.
	 * @return The location of the voice clip
	 */
	public String getVoiceClip(String id) {
		// TODO Voice translation
		return "voice.en" + id;
	}
}

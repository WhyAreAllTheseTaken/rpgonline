package io.github.tomaso2468.rpgonline.lang;

/**
 * An interface allowing the configuration of language settings to be managed by
 * the application.
 * 
 * @author Tomas
 *
 */
public interface LangProvider {
	/**
	 * Sets the language of the game.
	 * @param lang The language code to set.
	 */
	public void setLang(String lang);

	/**
	 * Gets the current language setting.
	 * @param def The default language if no language is defined.
	 * @return A language code.
	 */
	public String getLang(String def);
}

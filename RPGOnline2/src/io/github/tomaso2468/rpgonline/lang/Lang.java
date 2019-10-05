/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.RPGConfig;

/**
 * A class providing basic translation support.
 * @author Tomaso2468
 */
public final class Lang {
	/**
	 Prevent instantiation
	 */
	private Lang() {
		
	}
	/**
	 * The list of loaded, available translation packages.
	 */
	private static List<LangPack> packs = new ArrayList<LangPack>();
	
	/**
	 * Gets the language package with the specified ID.
	 * @param id The ID of the pack in the format {@code language-country}.
	 * @return A language pack object.
	 */
	public static LangPack getPack(String id) {
		for (LangPack langPack : packs) {
			if (langPack.getCode().equals(id)) {
				return langPack;
			}
		}
		for (LangPack langPack : packs) {
			if (langPack.getCode().split("-")[0].equals(id.split("-")[0])) {
				return langPack;
			}
		}
		packs.add(new LangPack(id));
		return getPack(id);
	}
	
	/**
	 * The language pack that is currently in use.
	 */
	private static LangPack pack;
	/**
	 * Gets the language pack that is currently in use.
	 * @return A language pack object.
	 */
	public static LangPack getPack() {
		return pack;
	}
	/**
	 * Sets the language pack.
	 * @param pack A language pack object.
	 */
	public static void setPack(LangPack pack) {
		Lang.pack = pack;
		RPGConfig.getLangProvider().setLang(pack.getCode());
	}
	/**
	 * Finds the translation for the string with the specified ID.
	 * @param s The string ID to translate.
	 * @return A translated string.
	 */
	public static String translate(String s) {
		return pack.translate(s);
	}
	/**
	 * Finds the translation for the string with the specified ID.
	 * @param s The string ID to translate.
	 * @return A translated string.
	 */
	public static String get(String s) {
		return translate(s);
	}
	/**
	 * Finds and formats the translation for the string with the specified ID.
	 * @param s The string ID to translate.
	 * @param args The arguments to use for formatting.
	 * @return A translated, formatted string.
	 * 
	 * @see java.lang.String#format(String, Object...)
	 */
	public static String getf(String s, Object... args) {
		return String.format(translate(s), args);
	}
	/**
	 * Gets the voice clip location of the clip with the specified ID.
	 * @param id A voice clip ID.
	 * @return The location of the voice clip
	 */
	public static String getVoiceClip(String id) {
		return pack.getVoiceClip(id);
	}
	
	static {
		Log.debug("Getting system locale");
		String region1 = Locale.getDefault().getLanguage().toLowerCase();
		String region2 = Locale.getDefault().getCountry().toLowerCase();
		String region = region1 + "-" + region2;
		Log.info("System language: " + region);
		
		Log.info("Loading langauges");
		
		loadLang("en-us");
		
		String lang = "";
		for(LangPack p : getPacks()) {
			if(p.getCode().equals(region)) {
				lang = region;
				break;
			}
		}
		if(lang.equals("")) {
			for(LangPack p : getPacks()) {
				if(p.getCode().split("-")[0].equals(region1)) {
					lang = p.getCode();
					break;
				}
			}
		}
		if(lang.equals("")) {
			lang = "en-us";
		}
		
		Log.info("Setting language to " + RPGConfig.getLangProvider().getLang(lang));
		pack = getPack(RPGConfig.getLangProvider().getLang(lang));
	}
	
	/**
	 * Loads the language pack with the specified ID.
	 * @param id The ID of the language pack to load.
	 */
	public static void loadLang(String id) {
		Log.debug("Loading language " + id);
		Log.debug("Loaded " + getPack(id).getName());
	}
	/**
	 * Gets a list of all loaded language packs.
	 * @return A new list object.
	 */
	public static List<LangPack> getPacks() {
		return new ArrayList<LangPack>(packs);
	}
}

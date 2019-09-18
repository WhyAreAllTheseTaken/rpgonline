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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.RPGConfig;

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

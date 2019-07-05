package rpgonline.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.newdawn.slick.util.Log;

import rpgonline.RPGConfig;

public class Lang {
	private static List<LangPack> packs = new ArrayList<LangPack>();
	
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
	
	private static LangPack pack;
	public static LangPack getPack() {
		return pack;
	}
	public static void setPack(LangPack pack) {
		Lang.pack = pack;
		RPGConfig.getLangProvider().setLang(pack.getCode());
	}
	public static String translate(String s) {
		return pack.translate(s);
	}
	public static String get(String s) {
		return translate(s);
	}
	public static String getf(String s, Object... args) {
		return String.format(translate(s), args);
	}
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
		
		loadLang("en-gb");
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
	
	public static void loadLang(String id) {
		Log.debug("Loading language " + id);
		Log.debug("Loaded " + getPack(id).getName());
	}
	public static List<LangPack> getPacks() {
		return new ArrayList<LangPack>(packs);
	}
}

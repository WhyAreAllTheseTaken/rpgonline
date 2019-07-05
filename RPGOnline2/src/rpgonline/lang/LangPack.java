package rpgonline.lang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.newdawn.slick.util.Log;

import rpgonline.RPGConfig;

public class LangPack {
	private Properties[] data;
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
	
	public LangPack(String id, String id2) {
		this(compile(RPGConfig.getLangloc(), id, id2));
	}
	
	public LangPack(String id) {
		this(id.split("-")[0], id.split("-")[1]);
	}
	
	public String translate(String s) {
		for(Properties p : data) {
			if(p.containsKey(s)) {
				return p.getProperty(s);
			}
		}
		return s;
	}
	
	public String getCode() {
		return translate("lang.code") + "-" + translate("lang.code2");
	}
	
	public String getName() {
		return translate("lang.name") + " (" + translate("lang.region") + ")";
	}
	
	public String getVoiceClip(String id) {
		//TODO Voice translation
		return "voice.en" + id;
	}
}

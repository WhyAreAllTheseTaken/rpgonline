package rpgonline.net.login;

import java.util.HashMap;
import java.util.Map;

public interface ServerProtocol {
	public default String request(String... args) {
		Map<String, String> map = new HashMap<String, String>();
		
		for (String s : args) {
			map.put(s.split("=")[0], s.split("=")[1]);
		}
		
		return request(map);
	}
	public String request(Map<String, String> args);
	public boolean isUp();
}

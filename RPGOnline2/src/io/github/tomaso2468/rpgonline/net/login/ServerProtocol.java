package io.github.tomaso2468.rpgonline.net.login;

import java.util.HashMap;
import java.util.Map;

/**
 * An interface representing a web connection.
 * @author Tomas
 */
public interface ServerProtocol {
	/**
	 * Sends a web request and returns the result as text.
	 * @param args Interleaves keys and values for the request.
	 * @return The returned text.
	 */
	public default String request(String... args) {
		Map<String, String> map = new HashMap<String, String>();
		
		for (String s : args) {
			map.put(s.split("=")[0], s.split("=")[1]);
		}
		
		return request(map);
	}
	/**
	 * Sends a web request and returns the result as text.
	 * @param args The request data.
	 * @return The returned text.
	 */
	public String request(Map<String, String> args);
	/**
	 * Determines if the server can be connected to.
	 * @return {@code true} if the server is available, {@code false} otherwise.
	 */
	public boolean isUp();
}

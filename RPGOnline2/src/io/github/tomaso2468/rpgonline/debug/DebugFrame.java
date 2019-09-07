package io.github.tomaso2468.rpgonline.debug;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A single frame of debug information.
 * @author Tomas
 */
public final class DebugFrame {
	/**
	 * The start times for currently running tasks.
	 */
	private final Map<String, Long> start = new HashMap<>();
	/**
	 * The total times for completed tasks.
	 */
	private final Map<String, Long> times = new HashMap<>();
	
	/**
	 * Starts a task.
	 * @param id The task ID.
	 */
	public void start(String id) {
		start.put(id, System.nanoTime());
	}
	/**
	 * Stops a task.
	 * @param id The task ID.
	 */
	public void stop(String id) {
		Long t = times.get(id);
		
		if (t == null) {
			t = 0L;
		}
		times.put(id, t + (System.nanoTime() - start.get(id)));
	}
	
	/**
	 * Gets a set of all times in this frame.
	 * @return A set object.
	 */
	public Set<Entry<String, Long>> getTimes() {
		return times.entrySet();
	}
}

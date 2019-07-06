package rpgonline.debug;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class DebugFrame {
	private final Map<String, Long> start = new HashMap<>();
	private final Map<String, Long> times = new HashMap<>();
	
	public void start(String id) {
		start.put(id, System.nanoTime());
	}
	public void stop(String id) {
		Long t = times.get(id);
		
		if (t == null) {
			t = 0L;
		}
		times.put(id, t + (System.nanoTime() - start.get(id)));
	}
	
	public Set<Entry<String, Long>> getTimes() {
		return times.entrySet();
	}
}

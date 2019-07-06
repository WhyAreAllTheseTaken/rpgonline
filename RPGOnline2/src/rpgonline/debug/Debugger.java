package rpgonline.debug;

import java.util.HashMap;
import java.util.Map;

import rpgonline.RPGConfig;

public final class Debugger {
	private static final Map<Thread, DebugFrame> debug = new HashMap<>();
	private static Thread renderThread;
	
	public static final void start() {
		if (RPGConfig.isDebug()) {
			debug.put(Thread.currentThread(), new DebugFrame());
			start("total");
		}
	}
	
	public static final void initRender() {
		if (RPGConfig.isDebug()) renderThread = Thread.currentThread();
	}
	
	public static final void stop() {
		if (RPGConfig.isDebug()) stop("total");
	}
	
	public static final void start(String id) {
		if (RPGConfig.isDebug()) {
			DebugFrame f = debug.get(Thread.currentThread());
			if(f != null) f.start(id);
		}
	}
	
	public static final void stop(String id) {
		if (RPGConfig.isDebug()) {
			DebugFrame f = debug.get(Thread.currentThread());
			if(f != null) f.stop(id);
		}
	}
	
	public static final DebugFrame getRenderFrame() {
		return debug.get(renderThread);
	}
	
	public static final DebugFrame getFrame(Thread t) {
		return debug.get(t);
	}
	
	public static final DebugFrame getFrame() {
		return getFrame(Thread.currentThread());
	}
}

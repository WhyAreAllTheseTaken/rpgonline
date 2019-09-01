package rpgonline.debug;

import java.util.HashMap;
import java.util.Map;

import rpgonline.RPGConfig;

/**
 * The primary class for the debugging API.
 * @author Tomas
 * @see rpgonline.RPGGame
 */
public final class Debugger {
	/**
	 Prevent instantiation
	 */
	private Debugger() {
		
	}
	
	/**
	 * The map of debug frames.
	 */
	private static final Map<Thread, DebugFrame> debug = new HashMap<>();
	/**
	 * The thread used for rendering.
	 */
	private static Thread renderThread;
	
	/**
	 * Starts the current debug frame. This will overright existing debug frames on this thread.
	 */
	public static final void start() {
		if (RPGConfig.isDebug()) {
			debug.put(Thread.currentThread(), new DebugFrame());
			start("total");
		}
	}
	
	/**
	 * Initialises this thread as the thread for render operations.
	 */
	public static final void initRender() {
		if (RPGConfig.isDebug()) renderThread = Thread.currentThread();
	}
	
	/**
	 * Stops the current debug frame.
	 */
	public static final void stop() {
		if (RPGConfig.isDebug()) stop("total");
	}
	
	/**
	 * Starts a task within a debug frame. This can be repeated multiple times per frame for events that occur many times.
	 * @param id The ID of the task.
	 */
	public static final void start(String id) {
		if (RPGConfig.isDebug()) {
			DebugFrame f = debug.get(Thread.currentThread());
			if(f != null) f.start(id);
		}
	}
	
	/**
	 * Stops a task within a debug frame. This can be repeated multiple times per frame for events that occur many times.
	 * @param id The ID of the task.
	 */
	public static final void stop(String id) {
		if (RPGConfig.isDebug()) {
			DebugFrame f = debug.get(Thread.currentThread());
			if(f != null) f.stop(id);
		}
	}
	
	/**
	 * Gets the most recent debug frame from the render thread.
	 * @return A possibly-incomplete debug frame object.
	 */
	public static final DebugFrame getRenderFrame() {
		return debug.get(renderThread);
	}
	
	/**
	 * Gets the most recent debug frame from the specified thread.
	 * @param t A non-null thread object.
	 * @return A possibly-incomplete debug frame object.
	 */
	public static final DebugFrame getFrame(Thread t) {
		return debug.get(t);
	}
	
	/**
	 * Gets the most recent debug frame from this thread.
	 * @return A possibly-incomplete debug frame object.
	 */
	public static final DebugFrame getFrame() {
		return getFrame(Thread.currentThread());
	}
}

package io.github.tomaso2468.rpgonline.threading;

public class ThreadTracker {
	private static Thread main_thread;
	private static Thread renderer_thread;

	public static Thread getCurrentThread() {
		return Thread.currentThread();
	}

	public static boolean isMainThread() {
		return getCurrentThread() == getMainThread();
	}
	
	public static Thread getMainThread() {
		return main_thread;
	}

	public static void setMainThread(Thread main_thread) {
		ThreadTracker.main_thread = main_thread;
	}
	
	public static boolean isRendererThread() {
		return getCurrentThread() == getRendererThread();
	}

	public static Thread getRendererThread() {
		return renderer_thread;
	}

	public static void setRendererThread(Thread renderer_thread) {
		ThreadTracker.renderer_thread = renderer_thread;
	}
}

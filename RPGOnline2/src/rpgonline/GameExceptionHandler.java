package rpgonline;

import java.lang.Thread.UncaughtExceptionHandler;

import org.newdawn.slick.util.Log;

public class GameExceptionHandler implements UncaughtExceptionHandler {
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		Log.error(e);
	}

}

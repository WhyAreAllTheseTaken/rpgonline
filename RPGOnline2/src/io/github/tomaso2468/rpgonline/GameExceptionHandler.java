package io.github.tomaso2468.rpgonline;

import java.lang.Thread.UncaughtExceptionHandler;

import org.newdawn.slick.util.Log;

/**
 * An exception handler that move java exceptions into the logging system.
 * @author Tomas
 */
public class GameExceptionHandler implements UncaughtExceptionHandler {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		Log.error(e);
	}

}

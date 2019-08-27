package rpgonline.net;

import java.util.stream.Stream;

import org.newdawn.slick.util.Log;

import rpgonline.GameExceptionHandler;
import rpgonline.debug.DebugFrame;

public interface TickBased {
	public default void start() {
		new Thread(getMainThreadName()) {
			public void run() {
				Thread.currentThread().setUncaughtExceptionHandler(new GameExceptionHandler());

				long last_update = System.nanoTime();

				if (ServerManager.getServer() == TickBased.this) {
					ServerManager.server_max_time = (long) (1000000000 / getTickSpeed());
				}
				if (ServerManager.getClient() == TickBased.this) {
					ServerManager.client_max_time = (long) (1000000000 / getTickSpeed());
				}
				
				try {
					init();
				} catch (Exception e) {
					Log.error("Could not start " + getType() + ".", e);

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						Log.error(e);
					}

					Log.info("Retrying " + getType() + " start.");
					try {
						init();
					} catch (Exception e2) {
						Log.error(getTypeTitle() + " start failed again. Aborting.", e2);
						return;
					}
				}
				try {
					while (true) {
						double delta = (System.nanoTime() - last_update) / 1000000000.0;
						if(delta > 0.5) {
							delta = 0.5;
						}
						last_update = System.nanoTime();
						update(delta);
						if (System.nanoTime() - last_update > (1000000000 / getTickSpeed())) {
							if (System.nanoTime() - last_update - 1000000000 / getTickSpeed() > 32000) {
								Log.warn(getType() + " is running "
										+ (System.nanoTime() - last_update - 1000000000 / getTickSpeed()) / 1000000
										+ " millis behind.");
							}
						}
						if (ServerManager.getServer() == TickBased.this) {
							ServerManager.server_time = System.nanoTime() - last_update;
						}
						if (ServerManager.getClient() == TickBased.this) {
							ServerManager.client_time = System.nanoTime() - last_update;
						}
						while (System.nanoTime() - last_update < (1000000000 / getTickSpeed())) {
							Thread.yield();
						}
					}
				} catch (Exception e) {
					Log.error("Error in " + getType() + " thread.", e);

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						Log.error(e);
					}

					Log.info("Re-entering " + getType() + " loop.");
					try {
						while (true) {
							double delta = (System.nanoTime() - last_update) / 1000000000.0;
							if(delta > 0.5) {
								delta = 0.5;
							}
							last_update = System.nanoTime();
							update(delta);
							if (System.nanoTime() - last_update > (1000000000 / getTickSpeed())) {
								if (System.nanoTime() - last_update - 1000000000 / getTickSpeed() > 32000) {
									Log.warn(getType() + " is running "
											+ (System.nanoTime() - last_update - 1000000000 / getTickSpeed()) / 1000000
											+ " millis behind.");
								}
							}
							while (System.nanoTime() - last_update < (1000000000 / getTickSpeed())) {
								Thread.yield();
							}
							if (ServerManager.getServer() == TickBased.this) {
								ServerManager.server_time = System.nanoTime() - last_update;
							}
							if (ServerManager.getClient() == TickBased.this) {
								ServerManager.client_time = System.nanoTime() - last_update;
							}
						}
					} catch (Exception e2) {
						Log.error(
								getTypeTitle() + " loop failed again. Assuming the error will repeat indefinetly. Stopping " + getType() + ".",
								e2);

					}
				}
			}
		}.start();
	}
	default String getType() {
		if (this instanceof Server) {
			return "server";
		}
		if (this instanceof Client) {
			return "client";
		}
		return getClass().getSimpleName().toLowerCase();
	}
	default String getTypeTitle() {
		return toTitleCase(getType());
	}
	static String toTitleCase(String word) {
	    return Stream.of(word.split(" "))
	            .map(w -> w.toUpperCase().charAt(0)+ w.toLowerCase().substring(1))
	            .reduce((s, s2) -> s + " " + s2).orElse("");
	}
	public void init();
	public void update(double delta);
	public double getTickSpeed();
	public String getMainThreadName();
	public void stop();
	public boolean isInit();
	public DebugFrame getDebugFrame();
}

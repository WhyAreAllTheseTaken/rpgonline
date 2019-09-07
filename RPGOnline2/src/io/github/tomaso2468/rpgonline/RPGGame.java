package io.github.tomaso2468.rpgonline;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import io.github.tomaso2468.rpgonline.debug.DebugFrame;
import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.lowlevel.LowLevelUtils;
import io.github.tomaso2468.rpgonline.net.ServerManager;

/**
 * <p>
 * A class with added support for RPGOnline games. This class is based on
 * {@code StateBasedGame}. In addtion, some parts of loading (e.g. Music) can be
 * defered by overriding the load method.
 * </p>
 * 
 * @author Tomas
 */
public abstract class RPGGame extends StateBasedGame {
	/**
	 * The maximum value of the loading bar. This should be a long in the range
	 * {@code 0} to {@code Long.MAX_VALUE}.
	 */
	private long max;
	/**
	 * The current value of the loading bar. This should be a long in the range
	 * {@code 0} to {@code Long.MAX_VALUE}.
	 */
	private long value;

	/**
	 * <p>
	 * Create a new {@code RPGGame}.
	 * </p>
	 * <p>
	 * The creation of this object will write some information to the log (via
	 * {@code RPGOnline.queryVersionData()}).
	 * </p>
	 * 
	 * @param title The name of the game.
	 */
	public RPGGame(String title) {
		super(title);

		addState(new BasicGameState() {

			@Override
			public void init(GameContainer container, StateBasedGame game) throws SlickException {
				RPGOnline.queryVersionData();
				try {
					textureLoad(container, RPGGame.this, new LoadCounter() {
						@Override
						public void setValue(long v) {
							value = v;
						}

						@Override
						public long getValue() {
							return value;
						}

						@Override
						public void setMax(long v) {
							max = v;
						}

						@Override
						public long getMax() {
							return max;
						}
					});
					new Thread("Load") {
						public void run() {
							try {
								load(container, RPGGame.this, new LoadCounter() {
									@Override
									public void setValue(long v) {
										value = v;
									}

									@Override
									public long getValue() {
										return value;
									}

									@Override
									public void setMax(long v) {
										max = v;
									}

									@Override
									public long getMax() {
										return max;
									}
								});
							} catch (SlickException e) {
								e.printStackTrace();
							}
							enterState(1);
						}
					}.start();
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
				g.setColor(Color.black);
				g.fillRect(0, 0, container.getWidth(), container.getHeight());

				String str = "Loading " + 100f / max * value + "%";

				g.drawString(str, container.getWidth() / 2 - g.getFont().getWidth(str) / 2,
						container.getHeight() / 2 - g.getFont().getHeight(str) / 2);
			}

			@Override
			public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

			}

			@Override
			public int getID() {
				return 0;
			}
		});
	}

	/**
	 * <p>
	 * A method during which textures are loaded. This should be overridden with a
	 * method to load the required textures.
	 * </p>
	 * 
	 * @param container The container holding the game.
	 * @param game      This game.
	 * @param counter   An interface providing access to a loading screen.
	 * @throws SlickException Indicates an internal error occurred in the game.
	 */
	public abstract void textureLoad(GameContainer container, RPGGame game, LoadCounter counter) throws SlickException;

	/**
	 * <p>
	 * A method during which music and other things that do not require an OpenGL
	 * context.
	 * </p>
	 * 
	 * @param container The container holding the game.
	 * @param game      This game.
	 * @param counter   An interface providing access to a loading screen.
	 * @throws SlickException Indicates an internal error occurred in the game.
	 */
	public abstract void load(GameContainer container, RPGGame game, LoadCounter counter) throws SlickException;

	/**
	 * A method that gets the hashmap containing all states in this game. This
	 * method uses reflection to bypass slick2Ds restrictions.
	 * 
	 * @return A hashmap.
	 * @throws SlickException If an error occurs trying to get all game states.
	 */
	@SuppressWarnings("rawtypes")
	public HashMap getStates() throws SlickException {
		Field f;
		try {
			f = StateBasedGame.class.getDeclaredField("states");
			f.setAccessible(true);
			return (HashMap) f.get(this);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new SlickException(e.toString());
		}
	}

	/**
	 * Gets the target frame rate of the game.
	 * 
	 * @return A positive int value or -1 if no frame rate is set.
	 * @throws SlickException If an error occurs getting the target frame rate.
	 */
	public int getTargetFrameRate() throws SlickException {
		Field f;
		try {
			f = GameContainer.class.getDeclaredField("targetFPS");
			f.setAccessible(true);
			return f.getInt(getContainer());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new SlickException(e.toString());
		}
	}

	/**
	 * Sets this game to fullscreen. This requires an AppGameContainer to be used.
	 * 
	 * @param fullscreen {@code true} if the game should be in fullscreen.,
	 *                   {@code false} otherwise.
	 * @throws SlickException If an error occurs setting the fullscreen settings.
	 */
	public void setFullscreen(boolean fullscreen) throws SlickException {
		if (isFullscreen() == fullscreen) {
			return;
		}
		if (fullscreen) {
			((AppGameContainer) getContainer()).setDisplayMode(getContainer().getScreenWidth(),
					getContainer().getScreenHeight(), true);
		} else {
			int[] size = getWindowedScreenSize();
			((AppGameContainer) getContainer()).setDisplayMode(size[0], size[1], false);
		}
	}

	/**
	 * Gets the size of the game when windowed.
	 * 
	 * @return An 2 int array containing width data at i=0 and height data at i=1.
	 */
	protected int[] getWindowedScreenSize() {
		return new int[] { (int) (getContainer().getScreenWidth() / 1.5f),
				(int) (getContainer().getScreenHeight() / 1.5f) };
	}

	/**
	 * Checks if the game is in fullscreen.
	 * 
	 * @return {@code true} if the game is in fullscreen, {@code false} otherwise.
	 */
	public boolean isFullscreen() {
		return Display.isFullscreen();
	}

	/**
	 * Gets the version of this game.
	 * 
	 * @return A version object.
	 */
	public abstract Version getVersion();

	/**
	 * Gets the flavour of this game. (e.g. Release, Beta, Snapshot, Modded (include
	 * client ID).
	 * 
	 * @return A non-null string.
	 */
	public String getVersionFlavour() {
		return "Release";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void preRenderState(GameContainer container, Graphics g) throws SlickException {
		super.preRenderState(container, g);
		Debugger.start();
	}

	/**
	 * The debug frame from the previous rendering frame.
	 */
	private DebugFrame lastFrame;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void postRenderState(GameContainer container, Graphics g) throws SlickException {
		super.postRenderState(container, g);

		if (RPGConfig.isDebug()) {
			Debugger.start("debug-screen");

			float y = 4;
			g.setColor(Color.white);

			y = drawDebugLineRYWHigh(g, "FPS", container.getFPS(),
					(container.isVSyncRequested() ? 60 : getTargetFrameRate()) - 3, 30, y, false);
			if (ServerManager.client_max_time != 0) {
				if (ServerManager.client_time == 0) {
					y = drawDebugLineRYWHigh(g, "Client TPS", 0, 1, 1, y, false);
				} else {
					y = drawDebugLineRYWHigh(g, "Client TPS",
							Math.min((int) (1000000000 / ServerManager.client_time),
									(int) (1000000000 / ServerManager.client_max_time)),
							(int) (1000000000 / ServerManager.client_max_time) - 3,
							(int) (1000000000 / ServerManager.client_max_time) / 2, y, false);
				}
				y = drawDebugLineRYWLow(g, "Client Time", ServerManager.client_time / 1000000.0,
						ServerManager.client_max_time / 1000000.0, ServerManager.client_max_time / 2 / 1000000.0, y,
						false);
			}
			if (ServerManager.server_max_time != 0) {
				if (ServerManager.server_time == 0) {
					y = drawDebugLineRYWHigh(g, "Server TPS", 0, 1, 1, y, false);
				} else {
					y = drawDebugLineRYWHigh(g, "Server TPS",
							Math.min((int) (1000000000 / ServerManager.server_time),
									(int) (1000000000 / ServerManager.server_max_time)),
							(int) (1000000000 / ServerManager.server_max_time) - 3,
							(int) (1000000000 / ServerManager.server_max_time) / 2, y, false);
				}
				y = drawDebugLineRYWLow(g, "Server Time", ServerManager.server_time / 1000000.0,
						ServerManager.server_max_time / 1000000.0, ServerManager.server_max_time / 2 / 1000000.0, y,
						false);
			}

			y = drawDebugLineRAM(g, "RAM Usage",
					(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000.0,
					Runtime.getRuntime().maxMemory() / 1000000.0, Runtime.getRuntime().maxMemory() * 0.75 / 1000000.0,
					Runtime.getRuntime().maxMemory() / 1000000.0, y, false);

			y = drawDebugLeft(g, y);

			DebugFrame render = lastFrame;
			if (render != null) {
				y = drawDebugTitle(g, "Render Thread", y, false);
				for (Entry<String, Long> t : render.getTimes()) {
					y = drawDebugLine(g, String.format("%20s : %s", t.getKey(), t.getValue() / 1000), y, false);
				}
			}

			if (ServerManager.getClient() != null) {
				DebugFrame client = ServerManager.getClient().getDebugFrame();
				if (client != null) {
					y = drawDebugTitle(g, "Client Thread", y, false);
					for (Entry<String, Long> t : client.getTimes()) {
						y = drawDebugLine(g, String.format("%20s : %s", t.getKey(), t.getValue() / 1000), y, false);
					}
				}
			}

			if (ServerManager.getServer() != null) {
				DebugFrame server = ServerManager.getServer().getDebugFrame();
				if (server != null) {
					y = drawDebugTitle(g, "Server Thread", y, false);
					for (Entry<String, Long> t : server.getTimes()) {
						y = drawDebugLine(g, String.format("%20s : %s", t.getKey(), t.getValue() / 1000), y, false);
					}
				}
			}

			// Right
			y = 4;

			y = drawDebugLineLabel(g, "Engine Version", RPGOnline.VERSION.toSimpleString(), y, true);
			y = drawDebugLineLabel(g, "Game Version", getVersion().toSimpleString() + " (" + getVersionFlavour() + ")",
					y, true);
			y = drawDebugLineLabel(g, "GPU", GL11.glGetString(GL11.GL_RENDERER), y, true);
			y = drawDebugLineLabel(g, "Display Size", container.getScreenWidth() + "x" + container.getScreenHeight(), y,
					true);
			y = drawDebugLineLabel(g, "Game Size", container.getWidth() + "x" + container.getHeight(), y, true);
			y = drawDebugLineLabel(g, "CPU", LowLevelUtils.LLU.getCPUModel(), y, true);
			y = drawDebugLineLabel(g, "CPU Threads", Runtime.getRuntime().availableProcessors() + "", y, true);
			y = drawDebugLineLabel(g, "Java Version", RPGOnline.JAVA_VERSION.toSimpleString(), y, true);
			y = drawDebugLineLabel(g, "LWJGL Version", RPGOnline.LWJGL_VERSION.toSimpleString(), y, true);
			y = drawDebugLineLabel(g, "OpenGL Version", GL11.glGetString(GL11.GL_VERSION), y, true);
			y = drawDebugLineLabel(g, "OpenGL Vendor", GL11.glGetString(GL11.GL_VENDOR), y, true);
			y = drawDebugLineLabel(g, "OpenAL Version", AL10.alGetString(AL10.AL_VERSION), y, true);
			y = drawDebugLineLabel(g, "OpenAL Vendor", AL10.alGetString(AL10.AL_VENDOR), y, true);
			y = drawDebugLineLabel(g, "Slick2D Version", RPGOnline.SLICK_VERSION.toSimpleString(), y, true);
			y = drawDebugLineLabel(g, "SlickShader Version", RPGOnline.SHADER_VERSION.toSimpleString(), y, true);
			y = drawDebugLineLabel(g, "OS Name",
					System.getProperty("os.name") + " " + "(" + System.getProperty("os.arch") + ")", y, true);
			y = drawDebugLineLabel(g, "OS Version", System.getProperty("os.version"), y, true);

			y = drawDebugRight(g, y);

			Debugger.stop("debug-screen");
		}

		Debugger.stop();
		lastFrame = Debugger.getRenderFrame();
	}

	/**
	 * Draws additional information of the left side of the debug screen.
	 * 
	 * @param g The current graphics context.
	 * @param y The Y position of text.
	 * @return The new Y position of text.
	 */
	protected float drawDebugLeft(Graphics g, float y) {
		return y;
	}

	/**
	 * Draws additional information of the right side of the debug screen.
	 * 
	 * @param g The current graphics context.
	 * @param y The Y position of text.
	 * @return The new Y position of text.
	 */
	protected float drawDebugRight(Graphics g, float y) {
		return y;
	}

	/**
	 * Draws debug information in a title style.
	 * 
	 * @param g    The current graphics context.
	 * @param s    The string to draw.
	 * @param y    The Y position of text.
	 * @param side {@code true} if the text should be rendered on the right of the
	 *             screen {@code false} otherwise.
	 * @return The new Y position of text.
	 */
	protected float drawDebugTitle(Graphics g, String s, float y, boolean side) {
		y += 2;
		if (!side) {
			g.drawString(s, 4, y);
		} else {
			g.drawString(s, this.getContainer().getWidth() - 4 - g.getFont().getWidth(s), y);
		}
		return y + g.getFont().getHeight("[]") + 1;
	}

	/**
	 * Draws debug information in the normal style.
	 * 
	 * @param g    The current graphics context.
	 * @param s    The string to draw.
	 * @param y    The Y position of text.
	 * @param side {@code true} if the text should be rendered on the right of the
	 *             screen {@code false} otherwise.
	 * @return The new Y position of text.
	 */
	protected float drawDebugLine(Graphics g, String s, float y, boolean side) {
		if (!side) {
			g.drawString(s, 4, y);
		} else {
			g.drawString(s, this.getContainer().getWidth() - 4 - g.getFont().getWidth(s), y);
		}
		return y + g.getFont().getHeight("[]") + 1;
	}

	/**
	 * Draws debug information as a labelled value.
	 * 
	 * @param g     The current graphics context.
	 * @param label The label of the data.
	 * @param data  The data to draw.
	 * @param y     The Y position of text.
	 * @param side  {@code true} if the text should be rendered on the right of the
	 *              screen {@code false} otherwise.
	 * @return The new Y position of text.
	 */
	protected float drawDebugLineLabel(Graphics g, String label, String data, float y, boolean side) {
		if (!side) {
			g.drawString(label + ": " + data, 4, y);
		} else {
			g.drawString(label + ": " + data,
					this.getContainer().getWidth() - 4 - g.getFont().getWidth(label + ": " + data), y);
		}
		return y + g.getFont().getHeight("[]") + 1;
	}

	/**
	 * Draws debug information as a labelled value in a different color depending on
	 * the value of the data. This method treats higher values as better so if the
	 * value is below {@code red} the text is a pale red, if the value is below
	 * {@code yellow} the text is a pale yellow and if the value is below both the
	 * text is a pale red.
	 * 
	 * @param g      The current graphics context.
	 * @param label  The label of the data.
	 * @param data   The data to draw.
	 * @param yellow The point where the text turns yellow.
	 * @param red    The point where the text turns red.
	 * @param y      The Y position of text.
	 * @param side   {@code true} if the text should be rendered on the right of the
	 *               screen {@code false} otherwise.
	 * @return The new Y position of text.
	 */
	protected float drawDebugLineRYWHigh(Graphics g, String label, long data, long yellow, long red, float y,
			boolean side) {
		if (data < yellow) {
			g.setColor(new Color(255, 255, 128));
		}
		if (data < red) {
			g.setColor(new Color(255, 128, 128));
		}

		if (!side) {
			g.drawString(label + ": " + data, 4, y);
		} else {
			g.drawString(label + ": " + data,
					this.getContainer().getWidth() - 4 - g.getFont().getWidth(label + ": " + data), y);
		}

		g.setColor(Color.white);
		return y + g.getFont().getHeight("[]") + 1;
	}

	/**
	 * Draws debug information as a labelled value in a different color depending on
	 * the value of the data. This method treats lower values as better so if the
	 * value is above {@code red} the text is a pale red, if the value is above
	 * {@code yellow} the text is a pale yellow and if the value is above both the
	 * text is a pale red.
	 * 
	 * @param g      The current graphics context.
	 * @param label  The label of the data.
	 * @param data   The data to draw.
	 * @param yellow The point where the text turns yellow.
	 * @param red    The point where the text turns red.
	 * @param y      The Y position of text.
	 * @param side   {@code true} if the text should be rendered on the right of the
	 *               screen {@code false} otherwise.
	 * @return The new Y position of text.
	 */
	protected float drawDebugLineRYWLow(Graphics g, String label, long data, long yellow, long red, float y,
			boolean side) {
		if (data > yellow) {
			g.setColor(new Color(255, 255, 128));
		}
		if (data > red) {
			g.setColor(new Color(255, 128, 128));
		}

		if (!side) {
			g.drawString(label + ": " + data, 4, y);
		} else {
			g.drawString(label + ": " + data,
					this.getContainer().getWidth() - 4 - g.getFont().getWidth(label + ": " + data), y);
		}

		g.setColor(Color.white);
		return y + g.getFont().getHeight("[]") + 1;
	}

	/**
	 * Draws debug information as a labelled value in a different color depending on
	 * the value of the data. This method treats higher values as better so if the
	 * value is below {@code red} the text is a pale red, if the value is below
	 * {@code yellow} the text is a pale yellow and if the value is below both the
	 * text is a pale red.
	 * 
	 * @param g      The current graphics context.
	 * @param label  The label of the data.
	 * @param data   The data to draw.
	 * @param yellow The point where the text turns yellow.
	 * @param red    The point where the text turns red.
	 * @param y      The Y position of text.
	 * @param side   {@code true} if the text should be rendered on the right of the
	 *               screen {@code false} otherwise.
	 * @return The new Y position of text.
	 */
	protected float drawDebugLineRYWHigh(Graphics g, String label, double data, double yellow, double red, float y,
			boolean side) {
		if (data < yellow) {
			g.setColor(new Color(255, 255, 128));
		}
		if (data < red) {
			g.setColor(new Color(255, 128, 128));
		}

		if (!side) {
			g.drawString(label + ": " + data, 4, y);
		} else {
			g.drawString(label + ": " + data,
					this.getContainer().getWidth() - 4 - g.getFont().getWidth(label + ": " + data), y);
		}

		g.setColor(Color.white);
		return y + g.getFont().getHeight("[]") + 1;
	}

	/**
	 * Draws debug information as a labelled value in a different color depending on
	 * the value of the data. This method treats lower values as better so if the
	 * value is above {@code red} the text is a pale red, if the value is above
	 * {@code yellow} the text is a pale yellow and if the value is above both the
	 * text is a pale red.
	 * 
	 * @param g      The current graphics context.
	 * @param label  The label of the data.
	 * @param data   The data to draw.
	 * @param yellow The point where the text turns yellow.
	 * @param red    The point where the text turns red.
	 * @param y      The Y position of text.
	 * @param side   {@code true} if the text should be rendered on the right of the
	 *               screen {@code false} otherwise.
	 * @return The new Y position of text.
	 */
	protected float drawDebugLineRYWLow(Graphics g, String label, double data, double yellow, double red, float y,
			boolean side) {
		if (data > yellow) {
			g.setColor(new Color(255, 255, 128));
		}
		if (data > red) {
			g.setColor(new Color(255, 128, 128));
		}

		if (!side) {
			g.drawString(label + ": " + data, 4, y);
		} else {
			g.drawString(label + ": " + data,
					this.getContainer().getWidth() - 4 - g.getFont().getWidth(label + ": " + data), y);
		}

		g.setColor(Color.white);
		return y + g.getFont().getHeight("[]") + 1;
	}

	/**
	 * Draws debug information as a labelled value in a different color depending on
	 * the value of the data. This method treats higher values as better so if the
	 * value is below {@code red} the text is a pale red, if the value is below
	 * {@code yellow} the text is a pale yellow and if the value is below both the
	 * text is a pale red. This method treats the inputs as ram information in mebibytes (MiB).
	 * 
	 * @param g      The current graphics context.
	 * @param label  The label of the data.
	 * @param data   The data to draw.
	 * @param yellow The point where the text turns yellow.
	 * @param red    The point where the text turns red.
	 * @param y      The Y position of text.
	 * @param side   {@code true} if the text should be rendered on the right of the
	 *               screen {@code false} otherwise.
	 * @return The new Y position of text.
	 */
	protected float drawDebugLineRAM(Graphics g, String label, double data, double max, double yellow, double red,
			float y, boolean side) {
		if (data >= yellow) {
			g.setColor(new Color(255, 255, 128));
		}
		if (data >= red) {
			g.setColor(new Color(255, 128, 128));
		}

		if (!side) {
			g.drawString(label + ": " + Math.round(data) + "/" + Math.round(max) + "MiB", 4, y);
		} else {
			g.drawString(label + ": " + Math.round(data) + "/" + Math.round(max) + "MiB", this.getContainer().getWidth()
					- 4 - g.getFont().getWidth(label + ": " + Math.round(data) + "/" + Math.round(max) + "MiB"), y);
		}

		g.setColor(Color.white);
		return y + g.getFont().getHeight("[]") + 1;
	}

}

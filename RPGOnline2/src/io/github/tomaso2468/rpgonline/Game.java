package io.github.tomaso2468.rpgonline;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.math3.util.FastMath;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.audio.AudioManager;
import io.github.tomaso2468.rpgonline.debug.DebugFrame;
import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.input.Input;
import io.github.tomaso2468.rpgonline.lowlevel.LowLevelUtils;
import io.github.tomaso2468.rpgonline.net.ServerManager;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderMode;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.transition.BlankTransition;
import io.github.tomaso2468.rpgonline.transition.Transition;
import io.github.tomaso2468.rpgonline.world2d.pathfinding.PathFindingManager;

public class Game {
	private String title;
	private Map<Integer, GameState> states = new HashMap<>();
	private GameState previousState;
	private GameState currentState;
	private GameState nextState;
	private Transition enter;
	private Transition leave;
	private int fpsCap = 0;
	private Renderer renderer;
	private final Version version;
	private boolean vsync = true;
	private Font font;
	private boolean antialias = false;
	private boolean clearEveryFrame = true;
	private String icon;
	private boolean mouseGrabbed = false;
	private float minDelta = 0;
	private float maxDelta = Float.POSITIVE_INFINITY;
	private long lastUpdateTime = System.nanoTime();
	private long lastRenderTime = System.nanoTime();
	private float fps;
	private boolean started;
	private boolean fullscreen;
	private Input input;

	public Game(String title, Version version) {
		this.title = title;
		this.version = version;

		currentState = new GameState() {
			@Override
			public void render(Game game, Renderer renderer) {
				renderer.setMode(RenderMode.MODE_2D_COLOR_NOVBO);
				
				renderer.scale2D(2, 2);
				renderer.drawQuad(100, 100, 200, 200, Color.orange);
			}

			@Override
			public int getID() {
				return Integer.MIN_VALUE;
			}
		};
	}

	public void start() throws RenderException {
		started = true;

		init(this);
		while (true) {
			loop();
		}
	}

	protected void loop() throws RenderException {
		float delta = (System.nanoTime() - lastUpdateTime) / 1000000000f;
		lastUpdateTime = System.nanoTime();

		fps = 1000000000f / (System.nanoTime() - lastRenderTime);
		lastRenderTime = System.nanoTime();

		if (delta > maxDelta) {
			delta = maxDelta;
		}
		if (delta < minDelta) {
			delta = minDelta;
		}

		update(this, delta);
		if (leave != null) {
			leave.update(this, currentState, nextState, delta);

			if (leave.isDone()) {
				leave = null;

				previousState = currentState;
				currentState.exitState(this);
				currentState = nextState;
				nextState.enterState(this);
			}
		} else if (enter != null) {
			enter.update(this, previousState, nextState, delta);

			if (enter.isDone()) {
				enter = null;
			}
		}

		if (renderer.displayClosePressed()) {
			exit(0);
		}

		if (clearEveryFrame) {
			renderer.clear();
		}

		renderer.resetTransform();
		render(this, renderer);
		if (leave != null) {
			leave.render(this, currentState, nextState, renderer);
		} else if (enter != null) {
			enter.render(this, previousState, nextState, renderer);
		}

		renderer.doUpdate();
		if (vsync) {
			renderer.sync(fpsCap);
		} else if (fpsCap > 0) {
			renderer.sync(fpsCap);
		}
	}

	public void init(Game game) throws RenderException {
		renderer.init(this);
		renderer.useHDRBuffers(RPGConfig.isHDR());
		if (font == null) {
			font = renderer.loadFont("Arial", Renderer.FONT_NORMAL, 18);
		}
		renderer.setFont(font);
		TextureMap.setRenderer(renderer);
		
		this.input = renderer.getInput();
		
		AudioManager.getSystem();
		
		for (Entry<Integer, GameState> state : getStates()) {
			state.getValue().init(game);
		}
	}

	public final void update(Game game, float delta) throws RenderException {
		preUpdate(game, delta);
		renderer.resetTransform();
		currentState.update(game, delta);
		renderer.resetTransform();
		postUpdate(game, delta);
		renderer.resetTransform();
	}

	public void preUpdate(Game game, float delta) throws RenderException {
		Debugger.start();
	}

	public void postUpdate(Game game, float delta) throws RenderException {
		Debugger.stop();
	}

	public final void render(Game game, Renderer renderer) throws RenderException {
		preRender(game, renderer);
		renderer.resetTransform();
		currentState.render(game, renderer);
		renderer.resetTransform();
		postRender(game, renderer);
	}

	public void preRender(Game game, Renderer renderer) throws RenderException {
		Debugger.start();
	}

	/**
	 * The debug frame from the previous rendering frame.
	 */
	private DebugFrame lastFrame;

	/**
	 * The debug frame from the previous game update.
	 */
	private DebugFrame lastUpdate;

	/**
	 * Rounds the value to 1 decimal place.
	 * 
	 * @param value The value to round.
	 * @return A rounded value.
	 */
	private double round1DP(double value) {
		return FastMath.round(value * 10) / 10.0;
	}

	public void postRender(Game game, Renderer renderer) throws RenderException {
		if (RPGConfig.isDebug()) {
			Debugger.start("debug-screen");
			Graphics g = renderer.getGUIGraphics();

			long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
			if (uptime < 1000) {
				uptime = 1000;
			}

			float y = 4;
			g.setColor(Color.white);

			y = drawDebugLineRYWHigh(g, "FPS", getFPS(), (vsync ? 60 : getFPSCap()) - 3, 30, y, false);
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

			if (uptime < 1000) {
				y = drawDebugLineLabel(g, "Uptime", uptime + " millis", y, false);
			} else if (uptime < 1000 * 60) {
				y = drawDebugLineLabel(g, "Uptime", round1DP(uptime / 1000.0) + " secs", y, false);
			} else if (uptime < 1000 * 60 * 60) {
				y = drawDebugLineLabel(g, "Uptime", round1DP(uptime / 1000.0 / 60.0) + " mins", y, false);
			} else if (uptime < 1000 * 60 * 60 * 24) {
				y = drawDebugLineLabel(g, "Uptime", round1DP(uptime / 1000.0 / 60.0 / 60.0) + " hours", y, false);
			} else if (uptime < 1000 * 60 * 60 * 24 * 365.25) {
				y = drawDebugLineLabel(g, "Uptime", round1DP(uptime / 1000.0 / 60.0 / 60.0 / 24.0) + " days", y, false);
			} else {
				y = drawDebugLineLabel(g, "Uptime", round1DP(uptime / 1000.0 / 60.0 / 60.0 / 24.0 / 365.24) + " years",
						y, false);
			}

			y = drawDebugLineLabel(g, "Pathfinding Ops/s",
					PathFindingManager.getPathfindingOperations() / (uptime / 1000) + "", y, false);

			y = drawDebugLeft(g, y);

			DebugFrame render = lastFrame;
			if (render != null) {
				y = drawDebugTitle(g, "Render Thread", y, false);
				for (Entry<String, Long> t : render.getTimes()) {
					y = drawDebugLineLabel(g, t.getKey(), (t.getValue() / 1000) + "", y, false);
				}
			}

			if (lastUpdate != null) {
				y = drawDebugTitle(g, "Main Thread Updates", y, false);
				for (Entry<String, Long> t : lastUpdate.getTimes()) {
					y = drawDebugLineLabel(g, t.getKey(), (t.getValue() / 1000) + "", y, false);
				}
			}

			if (ServerManager.getClient() != null) {
				DebugFrame client = ServerManager.getClient().getDebugFrame();
				if (client != null) {
					y = drawDebugTitle(g, "Client Thread", y, false);
					for (Entry<String, Long> t : client.getTimes()) {
						y = drawDebugLineLabel(g, t.getKey(), (t.getValue() / 1000) + "", y, false);
					}
				}
			}

			if (ServerManager.getServer() != null) {
				DebugFrame server = ServerManager.getServer().getDebugFrame();
				if (server != null) {
					y = drawDebugTitle(g, "Server Thread", y, false);
					for (Entry<String, Long> t : server.getTimes()) {
						y = drawDebugLineLabel(g, t.getKey(), (t.getValue() / 1000) + "", y, false);
					}
				}
			}

			// Right
			y = 4;

			y = drawDebugLineLabel(g, "Engine Version", RPGOnline.VERSION.toSimpleString(), y, true);
			y = drawDebugLineLabel(g, "Game Version", getVersion().toSimpleString() + " (" + getVersionFlavour() + ")",
					y, true);
			y = drawDebugLineLabel(g, "GPU", GL11.glGetString(GL11.GL_RENDERER), y, true);
			y = drawDebugLineLabel(g, "Display Size", renderer.getScreenWidth() + "x" + renderer.getScreenHeight(), y,
					true);
			y = drawDebugLineLabel(g, "Game Size", renderer.getWidth() + "x" + renderer.getHeight(), y, true);
			y = drawDebugLineLabel(g, "CPU", LowLevelUtils.LLU.getCPUModel(), y, true);
			y = drawDebugLineLabel(g, "CPU Threads", Runtime.getRuntime().availableProcessors() + "", y, true);
			y = drawDebugLineLabel(g, "OS Name",
					System.getProperty("os.name") + " " + "(" + System.getProperty("os.arch") + ")", y, true);
			y = drawDebugLineLabel(g, "OS Version", System.getProperty("os.version"), y, true);
			y = drawDebugLineLabel(g, "Java Version", RPGOnline.JAVA_VERSION.toSimpleString(), y, true);
			y = drawDebugLineLabel(g, "Current VM", ManagementFactory.getRuntimeMXBean().getName(), y, true);
			y = drawDebugLineLabel(g, "Java VM", ManagementFactory.getRuntimeMXBean().getVmName(), y, true);
			y = drawDebugLineLabel(g, "Java VM Version", ManagementFactory.getRuntimeMXBean().getVmVersion(), y, true);
			y = drawDebugLineLabel(g, "Java VM Vendor", ManagementFactory.getRuntimeMXBean().getVmVendor(), y, true);
			y = drawDebugLineLabel(g, "LWJGL Version", RPGOnline.LWJGL_VERSION.toSimpleString(), y, true);
			y = drawDebugLineLabel(g, "Renderer", renderer.getRendererName(), y, true);
			y = drawDebugLineLabel(g, "Renderer Library", renderer.getRendererGL(), y, true);
			y = drawDebugLineLabel(g, renderer.getRendererGL() + " Version", renderer.getVersion(), y, true);
			y = drawDebugLineLabel(g, renderer.getRendererGL() + " Vendor", renderer.getVendor(), y, true);
			y = drawDebugLineLabel(g, "OpenAL Version", AL10.alGetString(AL10.AL_VERSION), y, true);
			y = drawDebugLineLabel(g, "OpenAL Vendor", AL10.alGetString(AL10.AL_VENDOR), y, true);
			y = drawDebugLineLabel(g, "Slick2D Version", RPGOnline.SLICK_VERSION.toSimpleString(), y, true);
			y = drawDebugLineLabel(g, "SlickShader Version", RPGOnline.SHADER_VERSION.toSimpleString(), y, true);
			y = drawDebugLineLabel(g, "SpriteMap Size", RPGConfig.getAutoSpriteMapSize() + "", y, true);
			y = drawDebugLineLabel(g, "SpriteMap Enabled", RPGConfig.isMapped() + "", y, true);
			y = drawDebugLineLabel(g, "Texture Mode", RPGConfig.getFilterMode() + "", y, true);
			y = drawDebugLineLabel(g, "Path Sleep Delay", RPGConfig.getPathfindingSleepDelay() + "", y, true);
			y = drawDebugLineLabel(g, "Path Sleep Time", RPGConfig.getPathfindingSleepTime() + "", y, true);
			y = drawDebugLineLabel(g, "Path Threads", RPGConfig.getPathfindingThreads() + "", y, true);
			y = drawDebugLineLabel(g, "Tile Size", RPGConfig.getTileSize() + "", y, true);
			y = drawDebugLineLabel(g, "Hitbox Rendering", RPGConfig.isHitbox() + "", y, true);
			y = drawDebugLineLabel(g, "Lighting", RPGConfig.getLighting() + "", y, true);
			y = drawDebugLineLabel(g, "Particles", RPGConfig.isParticles() + "", y, true);
			y = drawDebugLineLabel(g, "Wind", RPGConfig.isWind() + "", y, true);

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
			g.drawString(s, renderer.getWidth() - 4 - g.getFont().getWidth(s), y);
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
			g.drawString(s, renderer.getWidth() - 4 - g.getFont().getWidth(s), y);
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
			g.drawString(label + ": " + data, renderer.getWidth() - 4 - g.getFont().getWidth(label + ": " + data), y);
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
			g.drawString(label + ": " + data, renderer.getWidth() - 4 - g.getFont().getWidth(label + ": " + data), y);
		}

		g.setColor(Color.white.darker());
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
			g.drawString(label + ": " + data, renderer.getWidth() - 4 - g.getFont().getWidth(label + ": " + data), y);
		}

		g.setColor(Color.white.darker());
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
			g.drawString(label + ": " + data, renderer.getWidth() - 4 - g.getFont().getWidth(label + ": " + data), y);
		}

		g.setColor(Color.white.darker());
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
			g.drawString(label + ": " + data, renderer.getWidth() - 4 - g.getFont().getWidth(label + ": " + data), y);
		}

		g.setColor(Color.white.darker());
		return y + g.getFont().getHeight("[]") + 1;
	}

	/**
	 * Draws debug information as a labelled value in a different color depending on
	 * the value of the data. This method treats higher values as better so if the
	 * value is below {@code red} the text is a pale red, if the value is below
	 * {@code yellow} the text is a pale yellow and if the value is below both the
	 * text is a pale red. This method treats the inputs as ram information in
	 * mebibytes (MiB).
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
			g.drawString(label + ": " + Math.round(data) + "/" + Math.round(max) + "MiB", renderer.getWidth() - 4
					- g.getFont().getWidth(label + ": " + Math.round(data) + "/" + Math.round(max) + "MiB"), y);
		}

		g.setColor(Color.white.darker());
		return y + g.getFont().getHeight("[]") + 1;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStateCount() {
		return states.size();
	}

	public Set<Entry<Integer, GameState>> getStates() {
		return states.entrySet();
	}

	public Map<Integer, GameState> getStateMap() {
		return states;
	}

	public GameState getCurrentState() {
		return currentState;
	}

	public int getCurrentStateID() {
		return currentState.getID();
	}

	public void addState(GameState state) {
		states.put(state.getID(), state);
	}

	public GameState getStateByID(int id) {
		return states.get(id);
	}

	public void changeState(int id, Transition enter, Transition leave) {
		nextState = getStateByID(id);
		this.enter = enter;
		this.leave = leave;
	}

	public void changeState(int id) {
		changeState(id, new BlankTransition(), new BlankTransition());
	}

	public int getFPSCap() {
		return fpsCap;
	}

	public void setFPSCap(int fpsCap) {
		this.fpsCap = fpsCap;
	}

	public float getFPS() {
		return fps;
	}

	public void setFullscreen(boolean fullscreen) throws RenderException {
		if (isFullscreen() == fullscreen) {
			return;
		}
		this.fullscreen = fullscreen;
		if (started) {
			if (fullscreen) {
				renderer.setDisplay(renderer.getScreenWidth(), renderer.getScreenHeight(), fullscreen);
			} else {
				renderer.setDisplay(renderer.getScreenWidth() / 2, renderer.getScreenHeight() / 2, fullscreen);
			}
		}
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}

	public Version getVersion() {
		return version;
	}

	public String getVersionFlavour() {
		return "Release";
	}

	public boolean isVsync() {
		return vsync;
	}

	public void setVsync(boolean vsync) {
		this.vsync = vsync;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
		if (started) renderer.setFont(font);
	}

	public boolean isAntialias() {
		return antialias;
	}

	public void setAntialias(boolean antialias) {
		this.antialias = antialias;
	}

	public boolean isClearEveryFrame() {
		return clearEveryFrame;
	}

	public void setClearEveryFrame(boolean clearEveryFrame) {
		this.clearEveryFrame = clearEveryFrame;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isMouseGrabbed() {
		return mouseGrabbed;
	}

	public void setMouseGrabbed(boolean mouseGrabbed) {
		this.mouseGrabbed = mouseGrabbed;
	}

	public float getMinDelta() {
		return minDelta;
	}

	public void setMinDelta(float minDelta) {
		this.minDelta = minDelta;
	}

	public float getMaxDelta() {
		return maxDelta;
	}

	public void setMaxDelta(float maxDelta) {
		this.maxDelta = maxDelta;
	}

	public void exit(int code) {
		renderer.exit(this);
		System.exit(code);
	}

	public Input getInput() {
		return input;
	}

	public int getHeight() {
		return renderer.getHeight();
	}
	
	public int getWidth() {
		return renderer.getWidth();
	}
}

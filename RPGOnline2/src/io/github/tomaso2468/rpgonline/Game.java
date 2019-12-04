/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.math3.util.FastMath;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.Color;
import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.audio.AudioSystem;
import io.github.tomaso2468.rpgonline.debug.DebugFrame;
import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.theme.DefaultTheme;
import io.github.tomaso2468.rpgonline.gui.theme.Theme;
import io.github.tomaso2468.rpgonline.input.Input;
import io.github.tomaso2468.rpgonline.lowlevel.LowLevelUtils;
import io.github.tomaso2468.rpgonline.net.ServerManager;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.RenderMode;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.transition.BlankTransition;
import io.github.tomaso2468.rpgonline.transition.Transition;
import io.github.tomaso2468.rpgonline.world2d.pathfinding.PathFindingManager;

/**
 * A class representing a game.
 * 
 * @author Tomaso2468
 * 
 * @see RPGConfig
 *
 */
public class Game {
	/**
	 * The title of the game.
	 */
	private String title;
	/**
	 * The states of the game.
	 */
	private Map<Long, GameState> states = new HashMap<>();
	/**
	 * The previous game state.
	 */
	private GameState previousState;
	/**
	 * The current game state.
	 */
	private GameState currentState;
	/**
	 * The next game state.
	 */
	private GameState nextState;
	/**
	 * The transition for entering the state.
	 */
	private Transition enter;
	/**
	 * The transition for leaving the state.
	 */
	private Transition leave;
	/**
	 * The FPS cap for the game.
	 */
	private float fpsCap = 0;
	/**
	 * The renderer for the game.
	 */
	private Renderer renderer;
	/**
	 * The version of the game.
	 */
	private final Version version;
	/**
	 * {@code true} if vsync should be used.
	 */
	private boolean vsync = true;
	/**
	 * The default font of the game. This is used for stuff like the debug menu.
	 */
	private Font font;
	/**
	 * {@code true} if antialias is enabled.
	 */
	private boolean antialias = true;
	/**
	 * {@code true} if the screen should be cleared after each frame.
	 */
	private boolean clearEveryFrame = true;
	/**
	 * The icon for the game.
	 */
	private URL icon;
	/**
	 * Whether or not the mouse is grabbed.
	 */
	private boolean mouseGrabbed = false;
	/**
	 * The minimum delta value for a game update.
	 */
	private float minDelta = 0;
	/**
	 * The maximum delta value for a game update.
	 */
	private float maxDelta = Float.POSITIVE_INFINITY;
	/**
	 * The last time the game was updated.
	 */
	private long lastUpdateTime = System.nanoTime();
	/**
	 * The last time the game was rendered.
	 */
	private long lastRenderTime = System.nanoTime();
	/**
	 * The target fps for the game.
	 */
	private float fps;
	/**
	 * Whether or not the game has started.
	 */
	private boolean started;
	/**
	 * Whether or not the game is in fullscreen mode.
	 */
	private boolean fullscreen;
	/**
	 * The input instance for the game.
	 */
	private Input input;
	
	/**
	 * The AudioSystem for this game.
	 */
	private AudioSystem audio;
	
	/**
	 * The map of textures for 2D sprites.
	 */
	private TextureMap textures;
	
	/**
	 * The theme for the game's GUI.
	 */
	private Theme theme;

	/**
	 * Constructs a new game.
	 * 
	 * @param title   The title of the game window.
	 * @param version The version number of the game.
	 */
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
			public long getID() {
				return Integer.MIN_VALUE;
			}
		};
	}

	/**
	 * Starts the game.
	 * 
	 * @throws RenderException If an error occurs in the renderer.
	 */
	public void start() throws RenderException {
		started = true;

		init();
		while (true) {
			loop();
		}
	}

	/**
	 * Compute and updates the state transitions.
	 * 
	 * @param delta The delta value in seconds.
	 * @throws RenderException
	 */
	protected void processTransitions(float delta) throws RenderException {
		if (leave != null) {
			leave.update(this, currentState, nextState, delta);

			if (leave.isDone()) {
				leave = null;

				// Swap state variables.
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
	}

	/**
	 * Runs a single game loop. This method includes the code for waiting for the
	 * next update and frame.
	 * 
	 * @throws RenderException If an error occurs in the renderer.
	 */
	protected void loop() throws RenderException {
		// Compute render times.
		float delta = (System.nanoTime() - lastUpdateTime) / 1000000000f;

		fps = 1000000000f / (System.nanoTime() - lastRenderTime);
		lastRenderTime = System.nanoTime();

		if (delta > maxDelta) {
			delta = maxDelta;
		}
		if (delta >= minDelta) {
			// Only update if delta is high enough.
			update(this, delta);
			processTransitions(delta);

			// Only change if an update has occurred.
			lastUpdateTime = System.nanoTime();
		}

		if (renderer.displayClosePressed()) {
			exit(0);
		}

		if (clearEveryFrame) {
			renderer.clear();
		}

		// Render the game.
		renderer.resetTransform();
		render(this, renderer);

		// Poll input and output frame.
		renderer.doUpdate();

		// Wait for next frame.
		if (vsync) {
			renderer.sync(fpsCap);
		} else if (fpsCap > 0) {
			renderer.sync(fpsCap);
		}
	}

	/**
	 * Initialises the game.
	 * 
	 * @throws RenderException If an error occurs in the renderer.
	 */
	protected void init() throws RenderException {
		// These should be set before starting the renderer as some APIs require this to
		// initialise the display.
		renderer.setAntialias(antialias);
		renderer.useHDRBuffers(RPGConfig.isHDR());

		// Start renderer.
		renderer.init(this);

		if (font == null) {
			font = renderer.loadFont("Arial", Renderer.FONT_NORMAL, 18);
		}

		// These should be set after the renderer is created.
		renderer.setFont(font);
		renderer.setVSync(vsync);
		renderer.setIcon(icon);
		renderer.setMouseGrab(mouseGrabbed);

		if (textures == null) {
			textures = new TextureMap();
		}
		textures.setRenderer(renderer);
		if (audio == null) {
			audio = new AudioSystem();
		}
		if (theme == null) {
			theme = new DefaultTheme();
		}

		this.input = renderer.getInput();

		// Initialise game states.
		for (Entry<Long, GameState> state : getStates()) {
			state.getValue().init(this);
		}
	}

	/**
	 * Updates the game.
	 * 
	 * <p>
	 * preUpdate and postUpdate should be overridden instead of this method.
	 * Rendering should not be done in this method but it will still work.
	 * </p>
	 * 
	 * @param game  The game to update.
	 * @param delta The delta value in seconds.
	 * @throws RenderException If an error occurs using the renderer.
	 * 
	 * @see #preUpdate(Game, float)
	 * @see #postUpdate(Game, float)
	 */
	public final void update(Game game, float delta) throws RenderException {
		Debugger.start();

		preUpdate(game, delta);
		renderer.resetTransform();

		currentState.update(game, delta);
		renderer.resetTransform();

		postUpdate(game, delta);
		renderer.resetTransform();

		Debugger.stop();
	}

	/**
	 * Called before a game update.
	 * 
	 * @param game  The game to update.
	 * @param delta The delta value in seconds.
	 * @throws RenderException If an error occurs using the renderer.
	 */
	public void preUpdate(Game game, float delta) throws RenderException {
	}

	/**
	 * Called after a game update.
	 * 
	 * @param game  The game to update.
	 * @param delta The delta value in seconds.
	 * @throws RenderException If an error occurs using the renderer.
	 */
	public void postUpdate(Game game, float delta) throws RenderException {
	}

	/**
	 * Renders the game.
	 * 
	 * @param game     The game to render.
	 * @param renderer The renderer to use.
	 * @throws RenderException If an error occurs using the renderer.
	 */
	public final void render(Game game, Renderer renderer) throws RenderException {
		preRender(game, renderer);
		renderer.resetTransform();

		currentState.render(game, renderer);
		renderer.resetTransform();

		postRender(game, renderer);
		renderer.resetTransform();

		if (leave != null) {
			leave.render(this, currentState, nextState, renderer);
		} else if (enter != null) {
			enter.render(this, previousState, nextState, renderer);
		}
	}

	/**
	 * Called before a render. This method also starts the debug information for
	 * this frame. <b>When overriding this method the superclass version should be
	 * called first before doing anything else.</b>
	 * 
	 * @param game     The game to render.
	 * @param renderer The renderer.
	 * @throws RenderException If an error occurs using the renderer.
	 */
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

	/**
	 * Called after a render. This method also end and renders the debug information
	 * for this frame. <b>When overriding this method the superclass version should
	 * be called last after any other code has been inserted.</b> If you want to add
	 * additional information override drawDebugLeft and drawDebugRight.
	 * 
	 * @param game     The game to render.
	 * @param renderer The renderer.
	 * @throws RenderException If an error occurs using the renderer.
	 * 
	 * @see #drawDebugLeft(Graphics, float)
	 * @see #drawDebugRight(Graphics, float)
	 */
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
			y = currentState.drawDebugLeft(game, g, y);

			DebugFrame render = lastFrame;
			if (render != null) {
				y = drawDebugTitle(g, "Render Thread", y, false);
				List<Entry<String,Long>> times = new ArrayList<>(render.getTimes());
				Collections.sort(times, new Comparator<Entry<String,Long>>() {
					@Override
					public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
						return o1.getKey().compareToIgnoreCase(o2.getKey());
					}
				});
				for (Entry<String, Long> t : times) {
					y = drawDebugLineLabel(g, t.getKey(), (t.getValue() / 1000) + "", y, false);
				}
			}

			if (lastUpdate != null) {
				y = drawDebugTitle(g, "Main Thread Updates", y, false);
				List<Entry<String,Long>> times = new ArrayList<>(lastUpdate.getTimes());
				Collections.sort(times, new Comparator<Entry<String,Long>>() {
					@Override
					public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
						return o1.getKey().compareToIgnoreCase(o2.getKey());
					}
				});
				for (Entry<String, Long> t : times) {
					y = drawDebugLineLabel(g, t.getKey(), (t.getValue() / 1000) + "", y, false);
				}
			}

			if (ServerManager.getClient() != null) {
				DebugFrame client = ServerManager.getClient().getDebugFrame();
				if (client != null) {
					y = drawDebugTitle(g, "Client Thread", y, false);
					List<Entry<String,Long>> times = new ArrayList<>(client.getTimes());
					Collections.sort(times, new Comparator<Entry<String,Long>>() {
						@Override
						public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
							return o1.getKey().compareToIgnoreCase(o2.getKey());
						}
					});
					for (Entry<String, Long> t : times) {
						y = drawDebugLineLabel(g, t.getKey(), (t.getValue() / 1000) + "", y, false);
					}
				}
			}

			if (ServerManager.getServer() != null) {
				DebugFrame server = ServerManager.getServer().getDebugFrame();
				if (server != null) {
					y = drawDebugTitle(g, "Server Thread", y, false);
					List<Entry<String,Long>> times = new ArrayList<>(server.getTimes());
					Collections.sort(times, new Comparator<Entry<String,Long>>() {
						@Override
						public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
							return o1.getKey().compareToIgnoreCase(o2.getKey());
						}
					});
					for (Entry<String, Long> t : times) {
						y = drawDebugLineLabel(g, t.getKey(), (t.getValue() / 1000) + "", y, false);
					}
				}
			}

			// Right
			y = 4;

			y = drawDebugLineLabel(g, "Engine Version", RPGOnline.VERSION.toSimpleString(), y, true);
			y = drawDebugLineLabel(g, "Game Version", getVersion().toSimpleString() + " (" + getVersionFlavour() + ")",
					y, true);
			y = drawDebugLineLabel(g, "GPU", renderer.getGPU(), y, true);
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
			y = drawDebugLineLabel(g, "SpriteMap Size", RPGConfig.getAutoSpriteMapSize() + "", y, true);
			y = drawDebugLineLabel(g, "SpriteMap Enabled", RPGConfig.isMapped() + "", y, true);
			y = drawDebugLineLabel(g, "Texture Mode", RPGConfig.getFilterMode() + "", y, true);
			y = drawDebugLineLabel(g, "Path Sleep Delay", RPGConfig.getPathfindingSleepDelay() + "", y, true);
			y = drawDebugLineLabel(g, "Path Sleep Time", RPGConfig.getPathfindingSleepTime() + "", y, true);
			y = drawDebugLineLabel(g, "Path Threads", RPGConfig.getPathfindingThreads() + "", y, true);
			y = drawDebugLineLabel(g, "Tile Size", RPGConfig.getTileSize() + "", y, true);
			y = drawDebugLineLabel(g, "Hitbox Rendering", RPGConfig.isHitbox() + "", y, true);
			y = drawDebugLineLabel(g, "Particles", RPGConfig.isParticles() + "", y, true);
			y = drawDebugLineLabel(g, "Wind", RPGConfig.isWind() + "", y, true);
			y = drawDebugRight(g, y);
			y = currentState.drawDebugRight(game, g, y);

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
		return y + g.getFont().getHeight() + 1;
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
		return y + g.getFont().getHeight() + 1;
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
		return y + g.getFont().getHeight() + 1;
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
		return y + g.getFont().getHeight() + 1;
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
		return y + g.getFont().getHeight() + 1;
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
		return y + g.getFont().getHeight() + 1;
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
		return y + g.getFont().getHeight() + 1;
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
		return y + g.getFont().getHeight() + 1;
	}

	/**
	 * Gets the title of this game.
	 * 
	 * @return A string.
	 * 
	 * @see #setTitle(String)
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of this game.
	 * 
	 * @param title A string.
	 * 
	 * @see #getTitle()
	 */
	public void setTitle(String title) {
		if (title == null) {
			title = "null";
		}
		this.title = title;
		if (started)
			renderer.setWindowTitle(title);
	}

	/**
	 * Gets the number of game states.
	 * 
	 * @return A positive or zero integer.
	 */
	public int getStateCount() {
		return states.size();
	}

	/**
	 * Returns a set of entries containing all game states.
	 * 
	 * @return An entry set.
	 */
	public Set<Entry<Long, GameState>> getStates() {
		return states.entrySet();
	}

	/**
	 * Returns the map used internally for states.
	 * 
	 * @return A map of integers to game states.
	 */
	public Map<Long, GameState> getStateMap() {
		return states;
	}

	/**
	 * Gets the current game state.
	 * 
	 * @return A game state object.
	 */
	public GameState getCurrentState() {
		return currentState;
	}

	/**
	 * Gets the ID of the current game state.
	 * 
	 * @return An integer.
	 */
	public long getCurrentStateID() {
		return currentState.getID();
	}

	/**
	 * Adds a new game state to the game.
	 * 
	 * @param state A game state object.
	 */
	public void addState(GameState state) {
		if (state == null) {
			throw new NullPointerException("state cannot be null");
		}
		states.put(state.getID(), state);
	}

	/**
	 * Gets a game state by its ID.
	 * 
	 * @param id A game state ID.
	 * @return A game state or null if no game state is found.
	 */
	public GameState getStateByID(long id) {
		return states.get(id);
	}

	/**
	 * Changes the game state to the one specified.
	 * 
	 * @param id    The ID of the state to enter.
	 * @param enter The transition used when entering the new state.
	 * @param leave The transition used when leaving the old state.
	 * 
	 * @see #changeState(int)
	 */
	public void changeState(long id, Transition enter, Transition leave) {
		GameState nextState = getStateByID(id);
		if (nextState == null) {
			throw new NullPointerException(id + " is not a valid state.");
		}
		this.nextState = nextState;
		this.enter = enter;
		this.leave = leave;
	}

	/**
	 * Changes the game state to the one specified.
	 * 
	 * @param id The ID of the state to enter.
	 * 
	 * @see #changeState(int, Transition, Transition)
	 */
	public void changeState(long id) {
		changeState(id, new BlankTransition(), new BlankTransition());
	}

	/**
	 * Gets the maximum FPS value allowed. This is not used if Vsync is on.
	 * 
	 * @return A positive float value or 0 to disable the FPS cap.
	 * 
	 * @see #setFPSCap(float)
	 * @see #setVsync(boolean)
	 * @see #isVsync()
	 * @see #getFPS()
	 */
	public float getFPSCap() {
		return fpsCap;
	}

	/**
	 * Sets the maximum FPS value allowed.
	 * 
	 * @param fpsCap A positive float value or 0 to disable the FPS cap.
	 * 
	 * @see #getFPSCap()
	 * @see #setVsync(boolean)
	 * @see #isVsync()
	 * @see #getFPS()
	 */
	public void setFPSCap(float fpsCap) {
		if (fpsCap < 0) {
			throw new IllegalArgumentException(fpsCap + " FPS is not a valid value.");
		}
		this.fpsCap = fpsCap;
	}

	/**
	 * Gets the current FPS of the game.
	 * 
	 * @return A float value.
	 * 
	 * @see #getFPSCap()
	 * @see #setFPSCap(float)
	 * @see #setVsync(boolean)
	 * @see #isVsync()
	 */
	public float getFPS() {
		return fps;
	}

	/**
	 * Changes the games fullscreen status. When the game is not in fullscreen mode
	 * it will take up half of the screen on each axis. This method does nothing if
	 * the game is already in the requested mode.
	 * 
	 * @param fullscreen {@code true} if the game should be in fullscreen,
	 *                   {@code false} otherwise.
	 * @throws RenderException If an error occurs changing between display modes.
	 * 
	 * @see #isFullscreen()
	 */
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

	/**
	 * Gets the fullscreen status of the game.
	 * 
	 * @return {@code true} if the game is in fullscreen, {@code false} otherwise.
	 * 
	 * @see #setFullscreen(boolean)
	 */
	public boolean isFullscreen() {
		return fullscreen;
	}

	/**
	 * Gets the renderer for the game.
	 * 
	 * @return A renderer object.
	 * 
	 * @see #setRenderer(Renderer)
	 */
	public Renderer getRenderer() {
		return renderer;
	}

	/**
	 * Sets the renderer for the game.
	 * 
	 * @param renderer A renderer object.
	 * 
	 * @see #getRenderer()
	 */
	public void setRenderer(Renderer renderer) {
		if (renderer == null) {
			throw new NullPointerException("renderer cannot be null");
		}
		this.renderer = renderer;
	}

	/**
	 * Gets the version number of the game.
	 * 
	 * @return A version object.
	 * 
	 * @see #getVersionFlavour()
	 */
	public Version getVersion() {
		return version;
	}

	/**
	 * Gets the version flavour of the game.
	 * 
	 * @return A string.
	 * 
	 * @see #getVersion()
	 */
	public String getVersionFlavour() {
		return "Release";
	}

	/**
	 * Gets if the game is using vsync.
	 * 
	 * @return {@code true} if vsync is enabled, {@code false} otherwise.
	 * 
	 * @see #setVsync(boolean)
	 */
	public boolean isVsync() {
		return vsync;
	}

	/**
	 * Sets if the game is using vsync.
	 * 
	 * @param vsync {@code true} if vsync is enabled, {@code false} otherwise.
	 * 
	 * @see #isVsync()
	 */
	public void setVsync(boolean vsync) {
		this.vsync = vsync;
		if (started)
			renderer.setVSync(vsync);
	}

	/**
	 * Gets the game's font.
	 * 
	 * @return A font object.
	 * 
	 * @see #setFont(Font)
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Sets the game's font.
	 * 
	 * @param font A font object compatible with the renderer.
	 * 
	 * @see #getFont()
	 */
	public void setFont(Font font) {
		if (font == null) {
			throw new NullPointerException("font cannot be null");
		}
		this.font = font;
		if (started)
			renderer.setFont(font);
	}

	/**
	 * Gets if the game is using antialiasing. This does not apply to post
	 * processing antialiasing used by PostProcessing.
	 * 
	 * @return {@code true} if antialiasing is enabled, {@code false} otherwise.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.PostProcessing
	 * @see #setAntialias(boolean)
	 */
	public boolean isAntialias() {
		return antialias;
	}

	/**
	 * Sets if antialiasing is enabled. This does not apply to post processing
	 * antialiasing used by PostProcessing.
	 * 
	 * @param antialias {@code true} if antialiasing should be enabled,
	 *                  {@code false} otherwise.
	 * 
	 * @see io.github.tomaso2468.rpgonline.post.PostProcessing
	 * @see #getAntialias()
	 */
	public void setAntialias(boolean antialias) {
		this.antialias = antialias;
		if (started)
			renderer.setAntialias(antialias);
	}

	/**
	 * Gets if the game should clear the screen before rendering a frame.
	 * 
	 * @return {@code true} if clearing is enabled, {@code false} otherwise.
	 * 
	 * @see #setClearEveryFrame(boolean)
	 */
	public boolean isClearEveryFrame() {
		return clearEveryFrame;
	}

	/**
	 * Sets if the game should clear the screen before rendering a frame.
	 * 
	 * @param clearEveryFrame {@code true} if clearing is enabled, {@code false}
	 *                        otherwise.
	 * 
	 * @see #isClearEveryFrame()
	 */
	public void setClearEveryFrame(boolean clearEveryFrame) {
		this.clearEveryFrame = clearEveryFrame;
	}

	/**
	 * Gets the URL of the icon for this game.
	 * 
	 * @return A URL object or null if no icon is set.
	 * 
	 * @see #setIcon(URL)
	 */
	public URL getIcon() {
		return icon;
	}

	/**
	 * Sets the icon for this game.
	 * 
	 * @param icon A URL object or null to set no icon (default).
	 * 
	 * @see #getIcon()
	 */
	public void setIcon(URL icon) {
		this.icon = icon;
		if (started)
			renderer.setIcon(icon);
	}

	/**
	 * Determines if the mouse is grabbed by the window.
	 * 
	 * @return {@code true} if the mouse is grabbed, {@code false} otherwise.
	 * 
	 * @see #setMouseGrabbed(boolean)
	 */
	public boolean isMouseGrabbed() {
		return mouseGrabbed;
	}

	/**
	 * Sets if the mouse is grabbed by the window.
	 * 
	 * @param mouseGrabbed {@code true} if the mouse is grabbed, {@code false}
	 *                     otherwise.
	 * 
	 * @see #isMouseGrabbed()
	 */
	public void setMouseGrabbed(boolean mouseGrabbed) {
		this.mouseGrabbed = mouseGrabbed;
		if (started)
			renderer.setMouseGrab(mouseGrabbed);
	}

	/**
	 * Gets the minimum delta value for this game.
	 * 
	 * @return A value in seconds.
	 * 
	 * @see #setMinDelta(float)
	 */
	public float getMinDelta() {
		return minDelta;
	}

	/**
	 * Sets the minimum delta value for this game.
	 * 
	 * @param minDelta A value in seconds.
	 * 
	 * @see #getMinDelta()
	 */
	public void setMinDelta(float minDelta) {
		if (minDelta < 0) {
			throw new IllegalArgumentException("delta cannot be negative.");
		}
		this.minDelta = minDelta;
	}

	/**
	 * Gets the maximum delta value for this game.
	 * 
	 * @return A value in seconds.
	 * 
	 * @see #setMaxDelta(float)
	 */
	public float getMaxDelta() {
		return maxDelta;
	}

	/**
	 * Sets the maximum delta value for this game.
	 * 
	 * @param maxDelta A value in seconds.
	 * 
	 * @see #getMaxDelta()
	 */
	public void setMaxDelta(float maxDelta) {
		if (maxDelta < 0) {
			throw new IllegalArgumentException("delta cannot be negative.");
		}
		this.maxDelta = maxDelta;
	}

	/**
	 * Exits the game. This method disposes of the renderer, audio system an then the application.
	 * 
	 * @param code The system exit code.
	 */
	public void exit(int code) {
		renderer.exit(this);
		audio.dispose();

		if (code > 0) {
			Log.warn(
					"Setting an exit code that is greater than 0 could be interpreted as an error by some programs and may interfere with the JVM.");
		}
		System.exit(code);
	}

	/**
	 * Gets the games input. A new instance is not created for each frame.
	 * @return An input instance.
	 */
	public Input getInput() {
		return input;
	}

	/**
	 * Gets the height of the game window.
	 * @return A positive integer.
	 * 
	 * @see #getWidth()
	 */
	public int getHeight() {
		return renderer.getHeight();
	}

	/**
	 * Gets the width of the game window.
	 * @return A positive integer.
	 * 
	 * @see #getHeight()
	 */
	public int getWidth() {
		return renderer.getWidth();
	}

	public AudioSystem getAudio() {
		return audio;
	}

	public void setAudio(AudioSystem audio) {
		this.audio = audio;
	}

	public TextureMap getTextures() {
		return textures;
	}

	public void setTextures(TextureMap textures) {
		this.textures = textures;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}
}

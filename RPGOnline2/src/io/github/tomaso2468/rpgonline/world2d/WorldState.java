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
package io.github.tomaso2468.rpgonline.world2d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math3.util.FastMath;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

import io.github.tomaso2468.rpgonline.BaseScaleState;
import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.GameState;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.RPGConfig;
import io.github.tomaso2468.rpgonline.RenderException;
import io.github.tomaso2468.rpgonline.TextureMap;
import io.github.tomaso2468.rpgonline.UpdateHook;
import io.github.tomaso2468.rpgonline.audio.AmbientMusic;
import io.github.tomaso2468.rpgonline.audio.AudioManager;
import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.GUI;
import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;
import io.github.tomaso2468.rpgonline.input.InputUtils;
import io.github.tomaso2468.rpgonline.net.ServerManager;
import io.github.tomaso2468.rpgonline.particle.Particle;
import io.github.tomaso2468.rpgonline.post.MDRMap;
import io.github.tomaso2468.rpgonline.post.MultiEffect;
import io.github.tomaso2468.rpgonline.post.NullPostProcessEffect;
import io.github.tomaso2468.rpgonline.post.PostEffect;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.sky.SkyLayer;
import io.github.tomaso2468.rpgonline.world2d.entity.Entity;
import io.github.tomaso2468.rpgonline.world2d.net.Client2D;
import io.github.tomaso2468.rpgonline.world2d.texture.TileTexture;
import io.github.tomaso2468.rpgonline.world2d.texture.entity.EntityTexture;

/**
 * A state for rendering worlds
 * 
 * @author Tomaso2468
 */
public class WorldState implements GameState, BaseScaleState {
	/**
	 * This state's id.
	 */
	private int id;
	/**
	 * The cached {@code x} position of the player.
	 */
	protected double x;
	/**
	 * The cached {@code y} position of the player.
	 */
	protected double y;
	/**
	 * Previous X position of the player used for velocity calculations.
	 */
	protected double px;
	/**
	 * Previous Y position of the player used for velocity calculations.
	 */
	protected double py;
	/**
	 * The world zoom.
	 */
	public float zoom = 1f;
	/**
	 * The strength of the shake effect.
	 */
	public float shake = 0f;
	/**
	 * The scale to scale the graphics by. This also effects GUI.
	 */
	public float base_scale = 1f;
	/**
	 * The GUI to use.
	 */
	private GUI guis = null;
	/**
	 * A list of tasks to run on game update.
	 */
	private List<UpdateHook> hooks = new ArrayList<UpdateHook>();
	/**
	 * The last game delta.
	 */
	public int last_delta;
	/**
	 * A buffer for shader effects.
	 */
	protected Image buffer;

	/**
	 * A second (smaller) buffer for pixel perfect effects.
	 */
	protected Image buffer2;

	/**
	 * A buffer for lighting.
	 */
	protected Image lightBuffer;

	/**
	 * The current shader effect.
	 */
	protected PostEffect post = null;

	/**
	 * The GUI toggle.
	 */
	protected boolean gui = true;

	/**
	 * Cooldown for GUI buttons.
	 */
	protected float gui_cooldown = 0.25f;

	/**
	 * The background for this world.
	 */
	protected SkyLayer sky;

	/**
	 * Currently displaying particles.
	 */
	protected List<Particle> particles = Collections.synchronizedList(new ArrayList<Particle>(128));

	/**
	 * If shaders are enabled.
	 */
	protected boolean post_enable = true;

	/**
	 * Creates a new {@code WorldState}.
	 * 
	 * @param id the ID of the state.
	 */
	public WorldState(int id) {
		this.id = id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(Game game) throws RenderException {
		guis.init(game.getWidth(), game.getHeight(), base_scale);
	}
	
	/**
	 * The MDR map for this world state.
	 */
	public static MDRMap mdr;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Game game, Renderer renderer) throws RenderException {
		Debugger.start("render");

		Debugger.start("game-render");
		render2(game, renderer);
		Debugger.stop("game-render");

		renderer.resetTransform();

		if (post_enable) {
			Debugger.start("effects");
			if (buffer == null) {
				buffer = new Image(renderer, game.getWidth(), game.getHeight());
			} else if (game.getWidth() != buffer.getWidth() || game.getHeight() != buffer.getHeight()) {
				buffer.destroy();
				buffer = new Image(renderer, game.getWidth(), game.getHeight());
			}
			
			if (post != null) {
				renderer.copyArea(buffer, 0, 0);

				if (!(post instanceof MultiEffect)) {
					Debugger.start("post-" + post.getClass());
				}
				post.doPostProcess(game, buffer, renderer);
				if (!(post instanceof MultiEffect)) {
					Debugger.stop("post-" + post.getClass());
				}
			}
			if(RPGConfig.isMDR()) {
				Debugger.start("mdr");
				renderer.copyArea(buffer, 0, 0);
				
				if (mdr == null) {
					mdr = new MDRMap();
				}
				mdr.doPostProcess(game, buffer, renderer);
				Debugger.stop("mdr");
			}
			Debugger.stop("effects");
		}
		

		if (gui) {
			Debugger.start("gui");
			
			renderer.resetTransform();
			
			Graphics g2 = renderer.getGUIGraphics();
			
			ThemeManager.getTheme().predraw(g2);
			guis.paint(g2, base_scale);

			Debugger.stop("gui");
		}

		Debugger.stop("render");
	}

	/**
	 * A reusable list of tile textures for faster rendering.
	 */
	protected List<TileTexture> textures = new ArrayList<>();
	/**
	 * A reusable list of entity textures for faster rendering.
	 */
	protected List<EntityTexture> entityTextures = new ArrayList<>();

	/**
	 * Computes lighting.
	 * @return A list of lights or null if lighting is off.
	 */
	public List<LightSource> computerLights() {
		List<LightSource> lights = null;
		if (RPGConfig.isLighting()) {
			Debugger.start("light-compute");

			lights = ((Client2D) ServerManager.getClient()).getWorld().getLights();

			if (lights.size() != 0) {
				lights.sort(new Comparator<LightSource>() {
					@Override
					public int compare(LightSource o1, LightSource o2) {
						double dist1 = FastMath.hypot(x - o1.getLX(), y - o1.getLY());
						double dist2 = FastMath.hypot(x - o2.getLX(), y - o2.getLY());

						if (dist1 < dist2) {
							return -1;
						}

						if (dist1 > dist2) {
							return 1;
						}

						return 0;
					}
				});

				for (int i = 0; i < lights.size(); i++) {
					if (i < lights.size()) {
						LightSource l = lights.get(i);
						double dist = FastMath.hypot(x - l.getLX(), y - l.getLY());
						if (dist > 40 * l.getBrightness()) {
							lights.remove(l);
							i -= 1;
						}
					}
				}

				while (lights.size() > 32) {
					lights.remove(lights.size() - 1);
				}
			}

			Debugger.stop("light-compute");
		}
		
		return lights;
	}
	
	/**
	 * A method that renders the world.
	 */
	public void render2(Game game, Renderer renderer) throws RenderException {
		List<LightSource> lights = computerLights();

		World world = ((Client2D) ServerManager.getClient()).getWorld();

		Debugger.start("sky");
		if (sky != null) {
			sky.render(renderer, game, x, y, 0, world, world.getLightColor());
		}
		Debugger.stop("sky");

		renderer.translate2D(game.getWidth() / 2, game.getHeight() / 2);

		renderer.scale2D(zoom * base_scale, zoom * base_scale);

		if (shake > 0) {
			renderer.translate2D((float) (FastMath.random() * shake * 5), (float) (FastMath.random() * shake * 5));
		}
		float sx = (float) (x * RPGConfig.getTileSize());
		float sy = (float) (y * RPGConfig.getTileSize());

		long dist_x = (long) (game.getWidth() / base_scale / zoom / RPGConfig.getTileSize() / 2) + 2;
		long dist_y = (long) (game.getHeight() / base_scale / zoom / RPGConfig.getTileSize() / 2) + 7;

		Debugger.start("entity-compute");
		List<Entity> entities1 = ((Client2D) ServerManager.getClient()).getWorld().getEntities();
		List<Entity> entities = new ArrayList<Entity>();

		Rectangle screen_bounds = new Rectangle((float) (x - dist_x), (float) (y - dist_y), (float) (dist_x * 2),
				(float) (dist_y * 2));

		synchronized (entities1) {
			for (Entity e : entities1) {
				if (screen_bounds.contains((float) e.getX(), (float) e.getY())) {
					entities.add(e);
				}
			}
		}
		Debugger.stop("entity-compute");

		List<Particle> particles_light = null;
		List<Particle> particles_nolight = null;

		if (RPGConfig.isParticles()) {
			Debugger.start("particle-compute");
			particles_light = new ArrayList<Particle>(particles.size());
			particles_nolight = new ArrayList<Particle>(32);

			for (int i = 0; i < particles.size(); i++) {
				Particle p = particles.get(i);

				if (screen_bounds.contains(p.getX(), p.getY())) {
					if (p.isLightAffected()) {
						particles_light.add(p);
					} else {
						particles_nolight.add(p);
					}
				}
			}
			Debugger.stop("particle-compute");
		}

		Debugger.start("world");
		long mix = (long) (x - dist_x);
		long max = (long) (x + dist_x);
		long miy = (long) (y - dist_y);
		long may = (long) (y + dist_y);
		long miz = world.getMinZ();
		long maz = world.getMaxZ();

		float wind = ((Client2D) ServerManager.getClient()).getWind();

		Image current = null;

		boolean hitbox = RPGConfig.isHitbox();
		for (long z = maz; z >= FastMath.min(-1, miz); z--) {
			for (long y = miy; y <= may; y++) {
				for (long x = mix; x <= max; x++) {
					Tile t = world.getTile(x, y, z);
					String state = world.getTileState(x, y, z);
					expandTexture(t.getTexture(), textures, x, y, z, world, t, state);

					for (TileTexture tex : textures) {
						if (tex.isCustom()) {
							Debugger.start("custom-tile");
							if (current != null)
								renderer.endUse(current);

							tex.render(renderer, x, y, z, world, state, t, x * RPGConfig.getTileSize() + tex.getX() - sx,
									y * RPGConfig.getTileSize() + tex.getY() - sy, wind);

							if (current != null)
								renderer.startUse(current);
							Debugger.stop("custom-tile");
						} else {
							Image img = TextureMap.getTexture(tex.getTexture(x, y, z, world, state, t));

							if (img != null) {
								if (TextureMap.getSheet(img) != current) {
									if (current != null)
										renderer.endUse(current);
									current = TextureMap.getSheet(img);
									renderer.startUse(current);
								}
								renderer.renderEmbedded(img, x * RPGConfig.getTileSize() + tex.getX() - sx,
										y * RPGConfig.getTileSize() + tex.getY() - sy, img.getWidth(), img.getHeight());
							}
						}
					}

					textures.clear();

					if (z == -1) {
						Debugger.start("entity");
						for (Entity e : entities) {
							if (!e.isFlying()) {
								if (FastMath.round(e.getX() + 0.5f) == x && FastMath.floor(e.getY() - 0.25) == y) {
									entityTextures.clear();

									expandTexture(e.getTexture(), entityTextures, e.getX(), e.getY(), world, wind, e);

									for (EntityTexture tex : entityTextures) {
										if (tex.isCustom()) {
											if (current != null)
												renderer.endUse(current);

											tex.render(renderer, x, y, -1, world, e, sx, sy, wind);

											if (current != null)
												renderer.startUse(current);
										} else {
											Image img = TextureMap
													.getTexture(tex.getTexture(e.getX(), e.getY(), -1, world, e, wind));

											if (img != null) {
												if (TextureMap.getSheet(img) != current) {
													if (current != null)
														renderer.endUse(current);
													current = TextureMap.getSheet(img);
													renderer.startUse(current);
												}
												renderer.renderEmbedded(img, (float) e.getX() * RPGConfig.getTileSize() + tex.getX() - sx,
														(float) e.getY() * RPGConfig.getTileSize() + tex.getY() - sy,
														img.getWidth(), img.getHeight());
											}
										}
									}
								}
							}
						}
						Debugger.stop("entity");
					}
				}
			}
		}

		Debugger.start("entity");
		for (Entity e : entities) {
			if (e.isFlying()) {
				entityTextures.clear();

				expandTexture(e.getTexture(), entityTextures, e.getX(), e.getY(), world, wind, e);

				for (EntityTexture tex : entityTextures) {
					if (tex.isCustom()) {
						if (current != null)
							renderer.endUse(current);

						tex.render(renderer, x, y, -3, world, e, sx, sy, wind);

						if (current != null)
							renderer.startUse(current);
					} else {
						Image img = TextureMap.getTexture(tex.getTexture(e.getX(), e.getY(), -3, world, e, wind));

						if (img != null) {
							if (TextureMap.getSheet(img) != current) {
								if (current != null)
									renderer.endUse(current);
								current = TextureMap.getSheet(img);
								renderer.startUse(current);
							}
							renderer.renderEmbedded(img, (float) e.getX() * RPGConfig.getTileSize() + tex.getX() - sx,
									(float) e.getY() * RPGConfig.getTileSize() + tex.getY() - sy,
									img.getWidth(), img.getHeight());
						}
					}
				}
			}
		}
		Debugger.stop("entity");
		
		Debugger.stop("world");

		if (RPGConfig.isParticles()) {
			Debugger.start("particles");
			Debugger.start("particle-light");
			for (Particle particle : particles_light) {
				if (particle.isCustom()) {
					if (current != null)
						renderer.endUse(current);
					particle.render(renderer, particle.getX() * RPGConfig.getTileSize() - sx,
							particle.getY() * RPGConfig.getTileSize() - sy);
					if (current != null)
						renderer.startUse(current);
				} else {
					Image img = TextureMap.getTexture(particle.getTexture());

					if (img != null) {
						if (TextureMap.getSheet(img) != current) {
							if (current != null)
								renderer.endUse(current);
							current = TextureMap.getSheet(img);
							renderer.startUse(current);
						}
						new Color(1, 1, 1, particle.getAlpha()).bind();
						renderer.renderEmbedded(img, particle.getX() * RPGConfig.getTileSize() - sx,
								particle.getY() * RPGConfig.getTileSize() - sy, img.getWidth(), img.getHeight());
					}
				}
			}

			Debugger.stop("particle-light");
			Debugger.stop("particles");
		}

		if (current != null)
			renderer.endUse(current);
		current = null;

		renderer.resetTransform();

		if (RPGConfig.isLighting()) {
			Debugger.start("lighting");

			if (lights.size() == 0) {
				Color light = world.getLightColor();
				g.setColor(RPGConfig.isMDR() && post_enable ? light.darker(0.5f) : light);
				g.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
				g.fillRect(0, 0, container.getWidth(), container.getHeight());
				g.setDrawMode(Graphics.MODE_NORMAL);
			} else {
				Graphics sg = g;

				if (lightBuffer == null) {
					lightBuffer = new Image(container.getWidth(), container.getHeight());
				} else if (container.getWidth() != lightBuffer.getWidth()
						|| container.getHeight() != lightBuffer.getHeight()) {
					lightBuffer.destroy();
					lightBuffer = new Image(container.getWidth(), container.getHeight());
				}

				g = lightBuffer.getGraphics();

				g.clear();

				g.setDrawMode(Graphics.MODE_NORMAL);

				Color wl = world.getLightColor();
				if (RPGConfig.isMDR() && post_enable) {
					wl = wl.darker(0.5f);
				}

				g.setColor(wl);
				g.fillRect(0, 0, container.getWidth(), container.getHeight());

				g.translate2d(container.getWidth() / 2, container.getHeight() / 2);

				g.scale(base_scale, base_scale);
				g.pushTransform();

				g.scale(zoom, zoom);
				if (shake > 0) {
					g.translate2d((float) (FastMath.random() * shake * 5), (float) (FastMath.random() * shake * 5));
				}

				g.setDrawMode(Graphics.MODE_ADD);

				for (LightSource l : lights) {
					Image img = TextureMap.getTexture("light").getScaledCopy(l.getBrightness() / 5);

					img.setImageColor(l.getR() * 50 * l.getBrightness(), l.getG() * 50 * l.getBrightness(), l.getB() * 50 * l.getBrightness());

					g.drawImage(img,
							(float) l.getLX() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 5 - sx,
							(float) l.getLY() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 5 - sy);
				}

				g.flush();

				sg.resetTransform();

				sg.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
				sg.drawImage(lightBuffer, 0, 0);
				sg.setDrawMode(Graphics.MODE_NORMAL);

				g = sg;
			}

			g.flush();

			Debugger.stop("lighting");
		} else if (post_enable && RPGConfig.isMDR()) {
			Debugger.start("mdr");
			Debugger.start("mdr-lightoverride");
			
			g.setColor(RPGConfig.isMDR() && post_enable ? Color.gray : Color.white);
			g.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
			g.fillRect(0, 0, container.getWidth(), container.getHeight());
			g.setDrawMode(Graphics.MODE_NORMAL);
			
			Debugger.stop("mdr-lightoverride");
			Debugger.stop("mdr");
		}

		g.resetTransform();

		if (RPGConfig.isParticles()) {
			Debugger.start("particles");
			Debugger.start("particles-nolight");

			RenderManager.getRenderer().translate(g, container.getWidth() / 2, container.getHeight() / 2);

			RenderManager.getRenderer().scale(g, zoom * base_scale, zoom * base_scale);

			if (shake > 0) {
				RenderManager.getRenderer().translate(g, (float) (FastMath.random() * shake * 5), (float) (FastMath.random() * shake * 5));
			}

			for (Particle particle : particles_nolight) {
				if (particle.isCustom()) {
					if (current != null)
						current.endUse();
					particle.render(g, particle.getX() * RPGConfig.getTileSize() - sx,
							particle.getY() * RPGConfig.getTileSize() - sy);
					if (current != null)
						current.startUse();
				} else {
					Image img = TextureMap.getTexture(particle.getTexture());

					if (img != null) {
						if (TextureMap.getSheet(img) != current) {
							if (current != null)
								current.endUse();
							current = TextureMap.getSheet(img);
							current.startUse();
						}
						new Color(1, 1, 1, particle.getAlpha()).bind();
						renderer.renderEmbedded(img, particle.getX() * RPGConfig.getTileSize() - sx,
								particle.getY() * RPGConfig.getTileSize() - sy, img.getWidth(), img.getHeight());
					}
				}
			}

			if (current != null)
				current.endUse();

			g.resetTransform();

			Debugger.stop("particles-nolight");
			Debugger.stop("particles");
		}
		
		if (hitbox) {
			Debugger.start("hitbox");
			
			RenderManager.getRenderer().translate(g, container.getWidth() / 2, container.getHeight() / 2);

			RenderManager.getRenderer().scale(g, zoom * base_scale, zoom * base_scale);

			if (shake > 0) {
				RenderManager.getRenderer().translate(g, (float) (FastMath.random() * shake * 5), (float) (FastMath.random() * shake * 5));
			}
			
			for (long y = miy; y <= may; y++) {
				for (long x = mix; x <= max; x++) {
					Tile t = world.getTile(x, y, -1);
					String state = world.getTileState(x, y, -1);
					
					if (hitbox && t.isSolid(state)) {
						boolean collision = false;
						for (Entity e : entities) {
							if (((x - e.getX()) * (x - e.getX()) + (y - e.getY()) * (y - e.getY())) <= (4 * 4)) {
								if(e.isSolid()) {
									collision = true;
									break;
								}
								
							}
						}
						if (collision) {
							g.setColor(Color.orange);
							g.draw(t.getHitBox()
									.transform(Transform.createScaleTransform(RPGConfig.getTileSize(),
											RPGConfig.getTileSize()))
									.transform(Transform.createTranslateTransform(x * RPGConfig.getTileSize() - sx,
											y * RPGConfig.getTileSize() - sy)));
						}
					}
				}
			}
			
			for (Entity e : entities) {
				g.setColor(Color.red);
				g.draw(e.getHitBox()
						.transform(Transform.createScaleTransform(RPGConfig.getTileSize(), RPGConfig.getTileSize()))
						.transform(Transform.createTranslateTransform(-sx, -sy)));
			}
			Debugger.stop("hitbox");
		}
	}

	/**
	 * Expands a single tile texture into all its subtextures.
	 * 
	 * @param t     The texture to expand.
	 * @param l     The list to expand into.
	 * @param x     The tile X position.
	 * @param y     The tile Y position.
	 * @param z     The tile Z position.
	 * @param world The current world.
	 * @param tile  The current tile.
	 * @param state The tile's current state.
	 */
	protected void expandTexture(TileTexture t, List<TileTexture> l, long x, long y, long z, World world, Tile tile,
			String state) {
		if (t.isPure()) {
			l.add(t);
		} else {
			TileTexture[] ta = t.getTextures(x, y, z, world, state, tile);

			for (TileTexture t2 : ta) {
				expandTexture(t2, l, x, y, z, world, tile, state);
			}
		}
	}

	/**
	 * Expands a single entity texture into all its subtextures.
	 * 
	 * @param t     The texture to expand.
	 * @param l     The list to expand into.
	 * @param x     The entity X position.
	 * @param y     The entity Y position.
	 * @param world The current world.
	 * @param wind  The current wind.
	 * @param e     The current entity.
	 */
	protected void expandTexture(EntityTexture t, List<EntityTexture> l, double x, double y, World world, float wind,
			Entity e) {
		if (t.isPure()) {
			l.add(t);
		} else {
			EntityTexture[] ta = t.getTextures(x, y, -1, world, e, wind);

			for (EntityTexture t2 : ta) {
				expandTexture(t2, l, x, y, world, wind, e);
			}
		}
	}
	
	/**
	 * Mouse positions.
	 */
	private float mx, my = 0;

	/**
	 * Updates the GUI.
	 * @param in The current input.
	 * @param container The current game container.
	 * @param game The game.
	 * @param delta The delta value.
	 */
	public void updateGUI(Input in, GameContainer container, StateBasedGame game, int delta) {
		if (guis != null) {
			Debugger.start("gui");
			if (gui) {
				Debugger.start("gui-container");
				guis.containerUpdate(container);
				Debugger.stop("gui-container");
				
				Debugger.start("gui-mouse");
				float ox = mx;
				float oy = my;
				
				mx = Mouse.getX();
				my = container.getHeight() - Mouse.getY();
				
				if (mx != ox || my != oy) {
					guis.mouseMoved(mx / base_scale, my / base_scale);
				}
				
				boolean[] data = new boolean[Math.max(3, Mouse.getButtonCount())];
				for (int i = 0; i < Mouse.getButtonCount(); i++) {
					data[i] = Mouse.isButtonDown(i);
				}
				guis.mouseState(mx / base_scale, my / base_scale, data);
				
				if (Mouse.hasWheel()) {
					guis.mouseWheel(Mouse.getDWheel());
				}
				Debugger.stop("gui-mouse");
				
				Debugger.start("gui-update");
				guis.update(delta / 1000f);
				Debugger.stop("gui-update");
			}
			Debugger.stop("gui");
		}
		
		gui_cooldown -= delta / 1000f;
		if (InputUtils.isActionPressed(in, InputUtils.GUI_TOGGLE) && gui_cooldown <= 0) {
			gui = !gui;
			gui_cooldown = 0.25f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F3) && gui_cooldown <= 0) {
			RPGConfig.setDebug(!RPGConfig.isDebug());
			gui_cooldown = 0.25f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F4) && gui_cooldown <= 0) {
			RPGConfig.setHitbox(!RPGConfig.isHitbox());
			gui_cooldown = 0.25f;
		}
	}
	
	/**
	 * Updates the GUI controls.
	 * @param in 
	 * @param container The game container.
	 * @param game The game.
	 * @param delf The delta value in seconds.
	 * @throws SlickException If an error occurs.
	 */
	public void updateControls(Input in, GameContainer container, StateBasedGame game, float delf) throws SlickException {
		Debugger.start("input");
		
		double walk_x = 0;
		double walk_y = 0;
		
		Debugger.start("keyboard");
		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_NORTH))) {
			walk_y += -1;
		}
		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_SOUTH))) {
			walk_y += 1;
		}

		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_EAST))) {
			walk_x += 1;
		}
		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_WEST))) {
			walk_x -= 1;
		}
		Debugger.stop("keyboard");
		
		Debugger.start("controller");
		if (in.getControllerCount() > 0) {
			if(in.getAxisCount(0) >= 2) {
				if(RPGConfig.getControllerInput().isLeftHanded()) {
					walk_y += in.getAxisValue(0, 2);
					walk_x += in.getAxisValue(0, 3);
				} else {
					walk_y += in.getAxisValue(0, 0);
					walk_x += in.getAxisValue(0, 1);
				}
			}
		}
		Debugger.stop("controller");
		
		if (walk_x > 1) {
			walk_x = 1;
		}
		if (walk_x < -1) {
			walk_x = -1;
		}
		if (walk_y > 1) {
			walk_y = 1;
		}
		if (walk_y < -1) {
			walk_y = -1;
		}
		
		((Client2D) ServerManager.getClient()).walkX(walk_x, delf);
		((Client2D) ServerManager.getClient()).walkY(walk_y, delf);
		
		((Client2D) ServerManager.getClient()).setSprint(InputUtils.isActionPressed(in, InputUtils.SPRINT));

		x = ((Client2D) ServerManager.getClient()).getPlayerX();
		y = ((Client2D) ServerManager.getClient()).getPlayerY();
		
		if (InputUtils.isActionPressed(in, InputUtils.EXIT)) {
			exit();
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			post_enable = false;
		} else {
			post_enable = true;
		}
		
		Debugger.stop("input");
	}
	
	/**
	 * Updates the graphical effects.
	 * @param delf The delta value in seconds.
	 * @throws SlickException If an error occurs.
	 */
	public void updateEffects(float delf) throws SlickException {
		Debugger.start("effects");
		
		Debugger.start("effects-shake");
		if (shake > 0) {
			shake -= delf;
		}
		Debugger.stop("effects-shake");
		
		
		Debugger.start("particles");
		float wind = ((Client2D) ServerManager.getClient()).getWind();
		World world = ((Client2D) ServerManager.getClient()).getWorld();
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).doBehaviour(world, wind, particles, delf);
		}

		particles.sort(new Comparator<Particle>() {
			@Override
			public int compare(Particle o1, Particle o2) {
				int a = o1.getClass().equals(o2.getClass()) ? 0 : 1;

				if (!o1.isLightAffected()) {
					a += 2;
				}

				return a;
			}
		});
		Debugger.stop("particles");
		
		Debugger.stop("effects");
	}
	
	/**
	 * Updates the world.
	 * @param delf The delta value in seconds.
	 */
	public void updateWorld(float delf) {
		Debugger.start("world");
		((Client2D) ServerManager.getClient()).getWorld().doUpdateClient();
		Debugger.stop("world");
	}
	
	/**
	 * Updates the state hooks.
	 * @param container The game container.
	 * @param game The game.
	 * @param delta The delta value.
	 * @throws SlickException If an error occurs.
	 */
	public void updateHooks(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Debugger.start("hooks");
		for (UpdateHook hook : hooks) {
			hook.update(container, game, delta);
		}
		Debugger.stop("hooks");
	}
	
	/**
	 * Updates the audio engine.
	 * @param container The game container.
	 * @param game The game.
	 * @param delta The delta value.
	 * @throws SlickException If an error occurs.
	 */
	public void updateAudio(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		float delf = delta / 1000f;
		Debugger.start("audio");
		AmbientMusic music = ServerManager.getClient().getMusic();
		AudioManager.setMusic(music);

		AudioManager.setPlayerPos((float) x, 0, (float) y);
		AudioManager.setPlayerVelocity((float) (x - px) / delf, 0, (float) (y - py) / delf);
		Debugger.stop("audio");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Debugger.initRender();

		this.last_delta = delta;
		float delf = delta / 1000f;
		
		Input in = container.getInput();

		updateControls(in, container, game, delf);
		updateWorld(delf);
		updateGUI(in, container, game, delta);
		updateHooks(container, game, delta);
		updateAudio(container, game, delta);
		updateEffects(delf);
		
		px = x;
		py = y;
	}

	/**
	 * A method called upon game exit.
	 */
	public void exit() {
		AudioManager.dispose();
		System.exit(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getID() {
		return id;
	}

	/**
	 * Gets the current GUI.
	 * @return A GUI object.
	 */
	public GUI getGUI() {
		return guis;
	}
	
	/**
	 * Sets the current GUI.
	 * @param gui a GUI object.
	 */
	public void setGUI(GUI gui) {
		this.guis = gui;
	}

	/**
	 * Adds a task to perform during a game update.
	 * 
	 * @param e An update hook.
	 */
	public void addHook(UpdateHook e) {
		hooks.add(e);
	}

	/**
	 * Removes a task from the state.
	 * 
	 * @param o An update hook.
	 */
	public void removeHook(UpdateHook o) {
		hooks.remove(o);
	}

	/**
	 * Adds an task at a specific point.
	 * 
	 * @param index   The index of the element.
	 * @param element An update hook.
	 */
	public void addHook(int index, UpdateHook element) {
		hooks.add(index, element);
	}

	/**
	 * Gets the current shader effect.
	 * 
	 * @return A {@code PostEffect} object.
	 */
	public PostEffect getPost() {
		return post;
	}

	/**
	 * Sets the current shader effect.
	 * 
	 * @param post A {@code PostEffect} object.
	 */
	public void setPost(PostEffect post) {
		if (post == null) {
			post = NullPostProcessEffect.INSTANCE;
		}
		this.post = post;
	}

	/**
	 * Determines if the GUI is currently displayed.
	 * 
	 * @return {@code true} if the GUI is enabled, {@code false} otherwise.
	 */
	public boolean isGuiShown() {
		return gui;
	}

	/**
	 * Sets if the GUI is currently displayed.
	 * 
	 * @param gui {@code true} if the GUI should be enabled, {@code false}
	 *            otherwise.
	 */
	public void setGuiShown(boolean gui) {
		this.gui = gui;
	}

	/**
	 * Gets the current sky.
	 * 
	 * @return A {@code SkyLayer} or {@code null} if no sky is set.
	 */
	public SkyLayer getSky() {
		return sky;
	}

	/**
	 * Sets the current sky.
	 * 
	 * @param sky A {@code SkyLayer} or {@code null} to disable sky rendering.
	 */
	public void setSky(SkyLayer sky) {
		this.sky = sky;
	}

	/**
	 * Adds a particle to this state.
	 * 
	 * @param p The particle to add.
	 */
	public void addParticle(Particle p) {
		particles.add(p);
	}

	/**
	 * Adds a collection of particles to this state.
	 * 
	 * @param p The particles to add.
	 */
	public void addParticles(Collection<? extends Particle> p) {
		particles.addAll(p);
	}

	/**
	 * Clears all particles.
	 */
	public void clearParticles() {
		particles.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void scale(GameContainer container, float base) {
		base_scale = base;
		guis.init(container.getWidth(), container.getHeight(), base_scale);
	}
}

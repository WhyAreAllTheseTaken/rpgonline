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
package io.github.tomaso2468.rpgonline.world2d.editor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.Image;
import io.github.tomaso2468.rpgonline.RPGConfig;
import io.github.tomaso2468.rpgonline.TextureMap;
import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;
import io.github.tomaso2468.rpgonline.input.Input;
import io.github.tomaso2468.rpgonline.input.InputUtils;
import io.github.tomaso2468.rpgonline.net.ServerManager;
import io.github.tomaso2468.rpgonline.particle.Particle;
import io.github.tomaso2468.rpgonline.render.ColorMode;
import io.github.tomaso2468.rpgonline.render.Graphics;
import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.RenderMode;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.world2d.LightSource;
import io.github.tomaso2468.rpgonline.world2d.Tile;
import io.github.tomaso2468.rpgonline.world2d.World;
import io.github.tomaso2468.rpgonline.world2d.WorldState;
import io.github.tomaso2468.rpgonline.world2d.entity.Entity;
import io.github.tomaso2468.rpgonline.world2d.net.Client2D;
import io.github.tomaso2468.rpgonline.world2d.texture.TileTexture;

/**
 * A class that allows for the editing of game worlds.
 * @author Tomaso2468
 * @see io.github.tomaso2468.rpgonline.world2d.WorldState
 */
public class WorldEditor extends WorldState {
	/**
	 * The current world to edit.
	 */
	private World world;
	/**
	 * If lights/other effects should be enabled.
	 */
	private boolean light;
	/**
	 * The currently selected tile.
	 */
	private int tile = 0;
	/**
	 * The currently selected tile set.
	 */
	private int set = 0;
	/**
	 * The tile sets.
	 */
	private final List<List<Tile>> tiles;
	/**
	 * The current z value.
	 */
	private long z;
	/**
	 * The cooldown for actions.
	 */
	private float cooldown = 0;
	/**
	 * The brush size.
	 */
	private int size = 2;
	/**
	 * <p>The brush mode.</p>
	 * <table>
	 * <tr>
	 * <td>0</td><td>Draw Mode</td>
	 * <td>1</td><td>Random (very light)</td>
	 * <td>2</td><td>Delete</td>
	 * <td>3</td><td>Random (light)</td>
	 * </tr>
	 * <table>
	 */
	private int brush_mode = 0;

	/**
	 * Creates a new {@code WorldEditor}.
	 * 
	 * @param id the ID of the state.
	 */
	public WorldEditor(int id, List<List<Tile>> sets, World world) {
		super(id);
		this.tiles = sets;
		this.world = world;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(Game game, Renderer renderer) throws RenderException {
		Debugger.start("render");

		if (post_enable) {
			if (buffer == null) {
				buffer = new Image(renderer, game.getWidth(), game.getHeight());
			} else if (game.getWidth() != buffer.getWidth() || game.getHeight() != buffer.getHeight()) {
				buffer.destroy();
				buffer = new Image(renderer, game.getWidth(), game.getHeight());
			}
			renderer.setRenderTarget(buffer);
			renderer.clear();
		}

		Debugger.start("game-render");
		render2(game, renderer);
		Debugger.stop("game-render");

		renderer.resetTransform();

		if (post_enable) {
			Debugger.start("effects");
			if (buffer2 == null) {
				buffer2 = new Image(renderer, game.getWidth(), game.getHeight());
			} else if (game.getWidth() != buffer2.getWidth() || game.getHeight() != buffer2.getHeight()) {
				buffer2.destroy();
				buffer2 = new Image(renderer, game.getWidth(), game.getHeight());
			}
			renderer.setRenderTarget(buffer2);
			renderer.clear();
			if (post != null) {
				Debugger.start("post-" + post.getClass());
				post.postProcess(buffer, buffer2, renderer);
				Debugger.stop("post-" + post.getClass());
			} else {
				renderer.drawImage(buffer, 0, 0);
			}
			renderer.setRenderTarget(null);
			if (RPGConfig.isHDR()) {
				Debugger.start("mdr");

				if (hdr == null) {
					hdr = renderer.createShader(WorldState.class.getResource("/generic.vrt"),
							WorldState.class.getResource("/hdr.frg"));
				}
				renderer.useShader(hdr);
				hdr.setUniform("exposure", 1f);
				hdr.setUniform("gamma", 1.2f);

				renderer.drawImage(buffer2, 0, 0);

				renderer.useShader(null);

				Debugger.stop("mdr");
			} else {
				renderer.drawImage(buffer2, 0, 0);
			}

			Debugger.stop("effects");
		}

		if (!light) {
			Debugger.start("gui");
			
			Graphics g = renderer.getGUIGraphics();

			g.setColor(new Color(0, 0, 0, 128));
			g.fillRect(0, 0, 512, 512);

			g.scale(zoom * base_scale, zoom * base_scale);

			Tile t = tiles.get(set).get(tile);
			String state = "";
			expandTexture(t.getTexture(), textures, Math.round(x), Math.round(y), z, world, t, state);

			for (TileTexture tex : textures) {
				if (tex.isCustom()) {
					Debugger.start("custom-tile");

					tex.render(renderer, Math.round(x), Math.round(y), z, world, state, t, 32 + tex.getX(), 32 + tex.getY(),
							0.1f);

					Debugger.stop("custom-tile");
				} else {
					Image img = TextureMap.getTexture(tex.getTexture(Math.round(x), Math.round(y), z, world, state, t));

					if (img != null) {
						g.drawImage(img, 32 + tex.getX(), 32 + tex.getY());
					}
				}
			}

			textures.clear();

			g.resetTransform();

			g.setColor(Color.white);

			g.drawString(Math.round(x) + " " + Math.round(y) + " " + z + " : " + set + "-" + t.getID(), 8, 8 + 32);
			switch (brush_mode) {
			case 0:
				g.drawString("Draw", 8, 8 + 32 * 2);
				break;
			case 1:
				g.drawString("Random - weak", 8, 8 + 32 * 2);
				break;
			case 2:
				g.drawString("Remove", 8, 8 + 32 * 2);
				break;
			case 3:
				g.drawString("Random - medium", 8, 8 + 32 * 2);
				break;
			}

			Debugger.stop("gui");
		}

		Debugger.stop("render");
	}

	List<TileTexture> textures = new ArrayList<>();

	/**
	 * A method that renders the world.
	 */
	public void render2(Game game, Renderer renderer) throws RenderException {
		List<LightSource> lights = computeLights();

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

		Rectangle box = new Rectangle(Math.round(x) - size / 2, Math.round(y) - size / 2, size, size);
		
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

							tex.render(renderer, x, y, z, world, state, t,
									x * RPGConfig.getTileSize() + tex.getX() - sx,
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
					
					if (!light && box.contains(x, y) && z == this.z) {
						if (current != null)
							renderer.endUse(current);

						Color c = Color.magenta;
						switch (brush_mode) {
						case 0:
							c = new Color(0, 255, 255, 128);
							break;
						case 1:
							c = new Color(255, 255, 0, 128);
							break;
						case 2:
							c = new Color(255, 0, 0, 128);
							break;
						case 3:
							c = new Color(255, 128, 0, 128);
							break;
						}

						renderer.drawQuad(x * RPGConfig.getTileSize() - sx, y * RPGConfig.getTileSize() - sy,
								RPGConfig.getTileSize(), RPGConfig.getTileSize(), c);

						if (current != null)
							renderer.startUse(current);
					}
				}
			}
		}

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

		if (RPGConfig.getLighting()) {
			Debugger.start("lighting");

			if (lights.size() == 0) {
				Color light = world.getLightColor();
				renderer.setColorMode(ColorMode.MULTIPLY);
				renderer.drawQuad(0, 0, game.getWidth(), game.getHeight(),
						RPGConfig.isHDR() && post_enable ? light.darker(0.5f) : light);
				renderer.setColorMode(ColorMode.NORMAL);
			} else {
				if (lightBuffer == null) {
					lightBuffer = new Image(renderer, game.getWidth(), game.getHeight());
				} else if (game.getWidth() != lightBuffer.getWidth()
						|| game.getHeight() != lightBuffer.getHeight()) {
					lightBuffer.destroy();
					lightBuffer = new Image(renderer, game.getWidth(), game.getHeight());
				}

				renderer.setRenderTarget(lightBuffer);

				renderer.clear();

				renderer.setColorMode(ColorMode.NORMAL);

				Color wl = world.getLightColor();
				if (RPGConfig.isHDR() && post_enable) {
					wl = wl.darker(0.5f);
				}

				renderer.drawQuad(0, 0, renderer.getWidth(), renderer.getHeight(), wl);

				renderer.translate2D(game.getWidth() / 2, game.getHeight() / 2);

				renderer.scale2D(base_scale, base_scale);
				renderer.pushTransform();

				renderer.scale2D(zoom, zoom);
				if (shake > 0) {
					renderer.translate2D((float) (FastMath.random() * shake * 5), (float) (FastMath.random() * shake * 5));
				}

				renderer.setColorMode(ColorMode.ADD);

				for (LightSource l : lights) {
					Image img = TextureMap.getTexture("light").getScaledCopy(l.getBrightness() / 5);

					renderer.renderFiltered(img, (float) l.getLX() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 5 - sx,
							(float) l.getLY() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 5 - sy, img.getWidth(), img.getHeight(), new Color(l.getR() * 50 * l.getBrightness(), l.getG() * 50 * l.getBrightness(),
							l.getB() * 50 * l.getBrightness()));
				}

				renderer.resetTransform();

				renderer.setRenderTarget(null);
				
				renderer.setColorMode(ColorMode.MULTIPLY);
				renderer.drawImage(lightBuffer, 0, 0);
				renderer.setColorMode(ColorMode.NORMAL);
			}

			Debugger.stop("lighting");
		} else if (post_enable && RPGConfig.isHDR()) {
			Debugger.start("mdr");
			Debugger.start("mdr-lightoverride");

			Color light = Color.white;
			renderer.setColorMode(ColorMode.MULTIPLY);
			renderer.drawQuad(0, 0, game.getWidth(), game.getHeight(),
					RPGConfig.isHDR() && post_enable ? light.darker(0.5f) : light);
			renderer.setColorMode(ColorMode.NORMAL);

			Debugger.stop("mdr-lightoverride");
			Debugger.stop("mdr");
		}

		renderer.resetTransform();

		if (RPGConfig.isParticles()) {
			Debugger.start("particles");
			Debugger.start("particles-nolight");

			renderer.translate2D(game.getWidth() / 2, game.getHeight() / 2);

			renderer.scale2D(zoom * base_scale, zoom * base_scale);

			if (shake > 0) {
				renderer.translate2D((float) (FastMath.random() * shake * 5),
						(float) (FastMath.random() * shake * 5));
			}

			for (Particle particle : particles_nolight) {
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

			if (current != null)
				renderer.startUse(current);

			renderer.resetTransform();

			Debugger.stop("particles-nolight");
			Debugger.stop("particles");
		}

		if (hitbox) {
			Debugger.start("hitbox");

			renderer.setMode(RenderMode.MODE_2D_LINES_NOVBO);
			
			renderer.translate2D(game.getWidth() / 2, game.getHeight() / 2);

			renderer.scale2D(zoom * base_scale, zoom * base_scale);

			if (shake > 0) {
				renderer.translate2D((float) (FastMath.random() * shake * 5),
						(float) (FastMath.random() * shake * 5));
			}

			for (long y = miy; y <= may; y++) {
				for (long x = mix; x <= max; x++) {
					Tile t = world.getTile(x, y, -1);
					String state = world.getTileState(x, y, -1);

					if (hitbox && t.isSolid(state)) {
						boolean collision = false;
						for (Entity e : entities) {
							if (((x - e.getX()) * (x - e.getX()) + (y - e.getY()) * (y - e.getY())) <= (4 * 4)) {
								if (e.isSolid()) {
									collision = true;
									break;
								}

							}
						}
						if (collision) {
							renderer.draw(t.getHitBox()
									.transform(Transform.createScaleTransform(RPGConfig.getTileSize(),
											RPGConfig.getTileSize()))
									.transform(Transform.createTranslateTransform(x * RPGConfig.getTileSize() - sx,
											y * RPGConfig.getTileSize() - sy)), Color.orange);
						}
					}
				}
			}

			for (Entity e : entities) {
				renderer.draw(e.getHitBox()
						.transform(Transform.createScaleTransform(RPGConfig.getTileSize(), RPGConfig.getTileSize()))
						.transform(Transform.createTranslateTransform(-sx, -sy)), Color.red);
			}
			Debugger.stop("hitbox");
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Game game, float delf) throws RenderException {
		this.last_delta = delf;

		Input in = game.getInput();

		boolean sprint = InputUtils.isActionPressed(in, InputUtils.SPRINT);

		double walk_x = 0;
		double walk_y = 0;
		if (in.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_NORTH))) {
			walk_y += -1;
		}
		if (in.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_SOUTH))) {
			walk_y += 1;
		}

		if (in.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_EAST))) {
			walk_x += 1;
		}
		if (in.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_WEST))) {
			walk_x -= 1;
		}

		x += walk_x * (sprint ? 30 : 10) * delf;
		y += walk_y * (sprint ? 30 : 10) * delf;

		if (InputUtils.isActionPressed(in, InputUtils.EXIT)) {
			exit();
		}

		if (in.isKeyDown(Input.KEY_Q)) {
			zoom /= 1.01f;
		}
		if (in.isKeyDown(Input.KEY_E)) {
			zoom *= 1.01f;
		}

		if (cooldown <= 0) {
			if (in.isKeyDown(Input.KEY_P)) {
				light = !light;
				cooldown = 1f;
			}
			if (!light) {
				if (in.isKeyDown(Input.KEY_DOWN)) {
					z += 1;
					cooldown = 1f;
				}
				if (in.isKeyDown(Input.KEY_UP)) {
					z -= 1;
					cooldown = 1f;
				}
				if (in.isKeyDown(Input.KEY_LEFT)) {
					tile -= 1;
					cooldown = 0.4f;
				}
				if (in.isKeyDown(Input.KEY_RIGHT)) {
					tile += 1;
					cooldown = 0.4f;
				}
				if (in.isKeyDown(Input.KEY_EQUALS)) {
					set += 1;
					tile = 0;
					cooldown = 0.5f;
				}
				if (in.isKeyDown(Input.KEY_MINUS)) {
					set -= 1;
					tile = 0;
					cooldown = 0.5f;
				}
				if (in.isKeyDown(Input.KEY_LBRACKET)) {
					size -= 1;
					cooldown = 0.2f;
				}
				if (in.isKeyDown(Input.KEY_RBRACKET)) {
					size += 1;
					cooldown = 0.2f;
				}
				if (in.isKeyDown(Input.KEY_0)) {
					size = 2;
					cooldown = 0.2f;
				}
				if (in.isKeyDown(Input.KEY_9)) {
					size = 50;
					cooldown = 0.2f;
				}
				if (in.isKeyDown(Input.KEY_8)) {
					size = 200;
					cooldown = 0.2f;
				}
				if (in.isKeyDown(Input.KEY_1)) {
					brush_mode = 0;
					cooldown = 0.2f;
				}
				if (in.isKeyDown(Input.KEY_2)) {
					brush_mode = 1;
					cooldown = 0.2f;
				}
				if (in.isKeyDown(Input.KEY_3)) {
					brush_mode = 2;
					cooldown = 0.2f;
				}
				if (in.isKeyDown(Input.KEY_4)) {
					brush_mode = 3;
					cooldown = 0.2f;
				}

				if (set < 0) {
					set = 0;
				}
				if (set >= tiles.size()) {
					set = tiles.size() - 1;
				}
				if (tile < 0) {
					tile = 0;
				}
				if (tile >= tiles.get(set).size()) {
					tile = tiles.get(set).size() - 1;
				}
				if (size < 2) {
					size = 2;
				}

				if (in.isKeyDown(Input.KEY_SPACE)) {
					for (long tx = Math.round(x) - size / 2 + 1; tx < Math.round(x) - size / 2 + size; tx++) {
						for (long ty = Math.round(y) - size / 2 + 1; ty < Math.round(y) - size / 2 + size; ty++) {
							switch (brush_mode) {
							case 1:
								if (Math.random() < 0.0025) {
									world.setTile(tx, ty, z, tiles.get(set).get(tile));
								}
								break;
							case 0:
								world.setTile(tx, ty, z, tiles.get(set).get(tile));
								break;
							case 2:
								world.setTile(tx, ty, z, null);
								break;
							case 3:
								if (Math.random() < 0.05) {
									world.setTile(tx, ty, z, tiles.get(set).get(tile));
								}
								break;
							}
						}
					}
					cooldown = 0.075f;
				}

				if (in.isKeyDown(Input.KEY_RETURN)) {
					try {
						world.save();
					} catch (IOException e) {
						Log.error("Error saving world.", e);
					}
					cooldown = 1f;
				}
			}
		}

		cooldown -= delf;
	}

}

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
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math3.util.FastMath;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.RPGConfig;
import io.github.tomaso2468.rpgonline.TextureMap;
import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.input.InputUtils;
import io.github.tomaso2468.rpgonline.post.MultiEffect;
import io.github.tomaso2468.rpgonline.world2d.LightSource;
import io.github.tomaso2468.rpgonline.world2d.Tile;
import io.github.tomaso2468.rpgonline.world2d.World;
import io.github.tomaso2468.rpgonline.world2d.WorldState;
import io.github.tomaso2468.rpgonline.world2d.texture.TileTexture;

/**
 * A class that allows for the editing of game worlds.
 * @author Tomas
 * 
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
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		Debugger.initRender();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		Debugger.start("render");

		Debugger.start("game-render");
		render2(container, game, g);

		g.resetTransform();
		Debugger.stop("game-render");

		if (post != null && post_enable) {
			Debugger.start("effects");

			if (buffer == null) {
				buffer = new Image(container.getWidth(), container.getHeight());
			} else if (container.getWidth() != buffer.getWidth() || container.getHeight() != buffer.getHeight()) {
				buffer.destroy();
				buffer = new Image(container.getWidth(), container.getHeight());
			}

			g.copyArea(buffer, 0, 0);

			if (!(post instanceof MultiEffect)) {
				Debugger.start("post-" + post.getClass());
			}
			post.doPostProcess(container, game, buffer, g);
			if (!(post instanceof MultiEffect)) {
				Debugger.stop("post-" + post.getClass());
			}

			Debugger.stop("effects");
		}
		g.resetTransform();
		g.flush();

		if (!light) {
			Debugger.start("gui");

			g.setColor(new Color(0, 0, 0, 128));
			g.fillRect(0, 0, 512, 512);

			g.scale(zoom * base_scale, zoom * base_scale);

			Tile t = tiles.get(set).get(tile);
			String state = "";
			expandTexture(t.getTexture(), textures, Math.round(x), Math.round(y), z, world, t, state);

			for (TileTexture tex : textures) {
				if (tex.isCustom()) {
					Debugger.start("custom-tile");

					tex.render(g, Math.round(x), Math.round(y), z, world, state, t, 32 + tex.getX(), 32 + tex.getY(),
							0.1f);

					Debugger.stop("custom-tile");
				} else {
					Image img = TextureMap.getTexture(tex.getTexture(Math.round(x), Math.round(y), z, world, state, t));

					if (img != null) {
						img.draw(32 + tex.getX(), 32 + tex.getY(), img.getWidth(), img.getHeight());
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

		g.flush();

		Debugger.stop("render");
	}

	List<TileTexture> textures = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	public void render2(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		List<LightSource> lights = null;
		if (RPGConfig.isLighting() && light) {
			Debugger.start("light-compute");

			lights = world.getLights();

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

		Debugger.start("sky");
		if (sky != null) {
			sky.render(g, container, x, y, 0, world, world.getLightColor());
		}
		Debugger.stop("sky");

		g.translate(container.getWidth() / 2, container.getHeight() / 2);

		g.scale(base_scale, base_scale);

		g.scale(zoom, zoom);

		float sx = (float) (x * RPGConfig.getTileSize());
		float sy = (float) (y * RPGConfig.getTileSize());

		long dist_x = (long) (container.getWidth() / base_scale / zoom / RPGConfig.getTileSize() / 2) + 2;
		long dist_y = (long) (container.getHeight() / base_scale / zoom / RPGConfig.getTileSize() / 2) + 7;

		Debugger.start("world");
		long mix = (long) (x - dist_x);
		long max = (long) (x + dist_x);
		long miy = (long) (y - dist_y);
		long may = (long) (y + dist_y);

		float wind = 0.25f;

		Image current = null;

		Rectangle box = new Rectangle(Math.round(x) - size / 2, Math.round(y) - size / 2, size, size);

		for (long z = 0; z >= -2; z--) {
			for (long y = miy; y <= may; y++) {
				for (long x = mix; x <= max; x++) {
					Tile t = world.getTile(x, y, z);
					String state = world.getTileState(x, y, z);
					expandTexture(t.getTexture(), textures, x, y, z, world, t, state);

					for (TileTexture tex : textures) {
						if (tex.isCustom()) {
							Debugger.start("custom-tile");
							if (current != null)
								current.endUse();

							tex.render(g, x, y, z, world, state, t, x * RPGConfig.getTileSize() + tex.getX() - sx,
									y * RPGConfig.getTileSize() + tex.getY() - sy, wind);

							if (current != null)
								current.startUse();
							Debugger.stop("custom-tile");
						} else {
							Image img = TextureMap.getTexture(tex.getTexture(x, y, z, world, state, t));

							if (img != null) {
								if (TextureMap.getSheet(img) != current) {
									if (current != null)
										current.endUse();
									current = TextureMap.getSheet(img);
									current.startUse();
								}
								img.drawEmbedded(x * RPGConfig.getTileSize() + tex.getX() - sx,
										y * RPGConfig.getTileSize() + tex.getY() - sy, img.getWidth(), img.getHeight());
							}
						}
					}

					textures.clear();

					if (!light && box.contains(x, y) && z == this.z) {
						if (current != null)
							current.endUse();

						switch (brush_mode) {
						case 0:
							g.setColor(new Color(0, 255, 255, 128));
							break;
						case 1:
							g.setColor(new Color(255, 255, 0, 128));
							break;
						case 2:
							g.setColor(new Color(255, 0, 0, 128));
							break;
						case 3:
							g.setColor(new Color(255, 128, 0, 128));
							break;
						}

						g.fillRect(x * RPGConfig.getTileSize() - sx, y * RPGConfig.getTileSize() - sy,
								RPGConfig.getTileSize(), RPGConfig.getTileSize());

						if (current != null)
							current.startUse();
					}
				}
			}
		}

		Debugger.stop("world");

		if (current != null)
			current.endUse();
		current = null;

		g.flush();

		g.resetTransform();

		if (RPGConfig.isLighting() && light) {
			Debugger.start("lighting");

			if (lights.size() == 0) {
				g.setColor(world.getLightColor());
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

				float red = wl.r;
				float green = wl.g;
				float blue = wl.b;

				float lum = (red + green + blue) / 3;

				float rscale = FastMath.max(lum * 10, 1);
				float gscale = FastMath.max(lum * 10, 1);
				float bscale = FastMath.max(lum * 10, 1);

				g.setColor(wl);
				g.fillRect(0, 0, container.getWidth(), container.getHeight());

				g.translate(container.getWidth() / 2, container.getHeight() / 2);

				g.scale(base_scale, base_scale);
				g.pushTransform();

				g.scale(zoom, zoom);
				if (shake > 0) {
					g.translate((float) (FastMath.random() * shake * 5), (float) (FastMath.random() * shake * 5));
				}

				g.setDrawMode(Graphics.MODE_SCREEN);

				for (LightSource l : lights) {
					Image img = TextureMap.getTexture("light").getScaledCopy(l.getBrightness() / 2);

					img.setImageColor(l.getR() / rscale, l.getG() / gscale, l.getB() / bscale);

					g.drawImage(img,
							(float) l.getLX() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 2
									- sx * RPGConfig.getTileSize(),
							(float) l.getLY() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 2
									- sy * RPGConfig.getTileSize());
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
		}

		g.resetTransform();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.last_delta = delta;

		float delf = delta / 1000f;

		Input in = container.getInput();

		boolean sprint = InputUtils.isActionPressed(in, InputUtils.SPRINT);

		double walk_x = 0;
		double walk_y = 0;
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

		x += walk_x * (sprint ? 30 : 10) * delf;
		y += walk_y * (sprint ? 30 : 10) * delf;

		if (InputUtils.isActionPressed(in, InputUtils.EXIT)) {
			exit();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			zoom /= 1.01f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			zoom *= 1.01f;
		}

		if (cooldown <= 0) {
			if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
				light = !light;
				cooldown = 1f;
			}
			if (!light) {
				if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
					z += 1;
					cooldown = 1f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
					z -= 1;
					cooldown = 1f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
					tile -= 1;
					cooldown = 0.4f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
					tile += 1;
					cooldown = 0.4f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
					set += 1;
					tile = 0;
					cooldown = 0.5f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
					set -= 1;
					tile = 0;
					cooldown = 0.5f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_LBRACKET)) {
					size -= 1;
					cooldown = 0.2f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_RBRACKET)) {
					size += 1;
					cooldown = 0.2f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
					size = 2;
					cooldown = 0.2f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_9)) {
					size = 50;
					cooldown = 0.2f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_8)) {
					size = 200;
					cooldown = 0.2f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
					brush_mode = 0;
					cooldown = 0.2f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
					brush_mode = 1;
					cooldown = 0.2f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
					brush_mode = 2;
					cooldown = 0.2f;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
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

				if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
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

				if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
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

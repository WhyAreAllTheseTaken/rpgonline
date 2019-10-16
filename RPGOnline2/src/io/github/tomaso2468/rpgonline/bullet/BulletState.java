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
package io.github.tomaso2468.rpgonline.bullet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.math3.util.FastMath;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import io.github.tomaso2468.rpgonline.BaseScaleState;
import io.github.tomaso2468.rpgonline.RPGConfig;
import io.github.tomaso2468.rpgonline.TextureMap;
import io.github.tomaso2468.rpgonline.audio.AmbientMusic;
import io.github.tomaso2468.rpgonline.audio.AudioManager;
import io.github.tomaso2468.rpgonline.debug.Debugger;
import io.github.tomaso2468.rpgonline.gui.GUI;
import io.github.tomaso2468.rpgonline.gui.theme.ThemeManager;
import io.github.tomaso2468.rpgonline.input.InputUtils;
import io.github.tomaso2468.rpgonline.particle.Particle;
import io.github.tomaso2468.rpgonline.post.MultiEffect;
import io.github.tomaso2468.rpgonline.post.NullPostProcessEffect;
import io.github.tomaso2468.rpgonline.post.PostEffect;
import io.github.tomaso2468.rpgonline.render.RenderManager;
import io.github.tomaso2468.rpgonline.render.Renderer;
import io.github.tomaso2468.rpgonline.sky.SkyLayer;

/**
 * A state for bullet based games.
 * @author Tomaso2468
 */
public abstract class BulletState extends BasicGameState implements BaseScaleState {
	/**
	 * The state ID.
	 */
	private final int id;
	
	/**
	 * Player x position.
	 */
	public float x;
	/**
	 * Player y position.
	 */
	public float y;
	/**
	 * Player x position on the previous frame (used of targeting/velocity calculations).
	 */
	public float px;
	/**
	 * Player y position on the previous frame (used of targeting/velocity calculations).
	 */
	public float py;
	/**
	 * The x velocity of the player.
	 */
	public float xv;
	/**
	 * The y velocity of the player.
	 */
	public float yv;
	/**
	 * If the camera should be centred around the player.
	 */
	public boolean center_camera = false;
	
	/**
	 * The zoom of the state.
	 */
	public float zoom = 1;
	/**
	 * A factor used for display scaling.
	 */
	public float base_scale = 1;
	/**
	 * If the player should appear above bullets.
	 */
	public boolean player_top = true;
	
	/**
	 * Factor for the shake effect.
	 */
	public float shake = 0f;
	/**
	 * The speed of the players movements.
	 */
	public double speed = 10;
	/**
	 * The wind speed (used for particles).
	 */
	public float wind;
	
	/**
	 * The background of the state.
	 */
	protected SkyLayer sky;
	
	/**
	 * A buffer used for post processing.
	 */
	protected Image buffer;
	/**
	 * The current post processing effects.
	 */
	protected PostEffect post = null;
	
	/**
	 * A list of GUIs used in this state.
	 */
	private GUI gui;
	/**
	 * A list of particles to display.
	 */
	protected List<Particle> particles = Collections.synchronizedList(new ArrayList<Particle>(256));
	/**
	 * The list of on-screen bullets.
	 */
	protected List<Bullet> bullets = new ArrayList<Bullet>(256);
	
	/**
	 * Constructs a new bullet state.
	 * @param id The state ID.
	 */
	public BulletState(int id) {
		this.id = id;
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
		Debugger.stop("game-render");

		g.resetTransform();

		if (post != null) {
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

		Debugger.start("gui");

		Rectangle world_clip = g.getWorldClip();
		Rectangle clip = g.getClip();
		g.resetTransform();
		
		io.github.tomaso2468.rpgonline.render.Graphics g2 = RenderManager.getRenderer().getGUIGraphics(g);
		g2.endSlick();
		
		ThemeManager.getTheme().predraw(g2);
		gui.paint(g2, base_scale);
		
		g2.beginSlick();
		g.setWorldClip(world_clip);
		g.setClip(clip);
		g.resetTransform();

		Debugger.stop("gui");
		
		g.flush();
		
		Debugger.stop("render");
	}
	
	/**
	 * Performs the rendering of the actual battle.
	 * @param container The game container.
	 * @param game The game
	 * @param g The current graphics context.
	 * @throws SlickException If a rendering error occurs.
	 */
	public void render2(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		Renderer renderer = RenderManager.getRenderer();
		
		List<Particle> particles = new ArrayList<Particle>(this.particles);
		
		Debugger.start("sky");
		if (sky != null) {
			sky.render(g, container, center_camera ? x : 0, center_camera ? x : 0, 0, null, Color.white);
		}
		Debugger.stop("sky");
		
		g.translate2d(container.getWidth() / 2, container.getHeight() / 2);

		g.scale(base_scale, base_scale);

		g.scale(zoom, zoom);
		
		if (shake > 0) {
			g.translate2d((float) (FastMath.random() * shake * 5), (float) (FastMath.random() * shake * 5));
		}
		
		float sx = center_camera ? x : 0;
		float sy = center_camera ? y : 0;
		
		if (!player_top) {
			Debugger.start("player");
			renderPlayer(container, game, g, sx, sy);
			Debugger.stop("player");
		}
		
		Debugger.start("bullets");
		Image current = null;
		for (Bullet b : bullets) {
			if (b.isCustom()) {
				if (current != null) current.endUse();
				b.render(x, y, xv, yv, this, g, sx, sy);
				if (current != null) current.startUse();
			} else if (b.isCombined()) {
				current = b.renderEmbedded(x, y, xv, yv, this, g, current, sx, sy);
			} else {
				Image img = TextureMap.getTexture(b.getTexture());
				
				if (img != null) {
					if (TextureMap.getSheet(img) != current) {
						if (current != null) current.endUse();
						current = TextureMap.getSheet(img);
						if (current != null) current.startUse();
					}
					
					renderer.renderEmbedded(img, b.getX() - sx - img.getWidth() / 2, b.getY() - sy - img.getHeight() / 2, img.getWidth(), img.getHeight());
				} else {
					if (current != null) current.endUse();
					g.setColor(Color.white);
					g.fillRect(b.getX() - sx, b.getY() - sy, 8, 8);
					if (current != null) current.startUse();
				}
			}
		}
		Debugger.stop("bullets");
		
		if (RPGConfig.isParticles()) {
			Debugger.start("particles");
			for (Particle particle : particles) {
				if (particle.isCustom()) {
					if (current != null) current.endUse();
					particle.render(g, particle.getX() * RPGConfig.getTileSize() - sx, particle.getY() * RPGConfig.getTileSize() - sy);
					if (current != null) current.startUse();
				} else {
					Image img = TextureMap.getTexture(particle.getTexture());
					
					if (img != null) {
						if(TextureMap.getSheet(img) != current) {
							if (current != null) current.endUse();
							current = TextureMap.getSheet(img);
							current.startUse();
						}
						new Color(1, 1, 1, particle.getAlpha()).bind();
						renderer.renderEmbedded(img, particle.getX() * RPGConfig.getTileSize() - sx, particle.getY() * RPGConfig.getTileSize() - sy, img.getWidth(), img.getHeight());
					}
				}
			}
			
			Debugger.stop("particles");
		}
		
		if (current != null) current.endUse();
		
		g.flush();
		
		if (player_top) {
			Debugger.start("player");
			renderPlayer(container, game, g, x - sx, y - sx);
			Debugger.stop("player");
		}
	}
	
	/**
	 * Renders the player to the screen/
	 * @param container The game container.
	 * @param game The game
	 * @param g The current graphics context.
	 * @param sx The x position of the player.
	 * @param sy The y position of the player.
	 */
	public abstract void renderPlayer(GameContainer container, StateBasedGame game, Graphics g, float sx, float sy);

	/**
	 * The mouse data.
	 */
	private float mx, my = 0;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input in = container.getInput();
		
		float delf = delta / 1000f;
		
		preUpdate(container, game, delf, in);
		
		double walk_x = 0;
		double walk_y = 0;
		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.BULLET_NORTH))) {
			walk_y += -1;
		}
		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.BULLET_SOUTH))) {
			walk_y += 1;
		}

		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.BULLET_EAST))) {
			walk_x += 1;
		}
		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.BULLET_WEST))) {
			walk_x -= 1;
		}

		x += walk_x * speed * delf;
		y += walk_y * speed * delf;
		
		Debugger.start("gui");
		
		Debugger.start("gui-container");
		gui.containerUpdate(container);
		Debugger.stop("gui-container");
		
		Debugger.start("gui-mouse");
		float ox = mx;
		float oy = my;
		
		mx = Mouse.getX();
		my = container.getHeight() - Mouse.getY();
		
		if (mx != ox || my != oy) {
			gui.mouseMoved(mx / base_scale, my / base_scale);
		}
		
		boolean[] data = new boolean[Math.max(3, Mouse.getButtonCount())];
		for (int i = 0; i < Mouse.getButtonCount(); i++) {
			data[i] = Mouse.isButtonDown(i);
		}
		gui.mouseState(mx / base_scale, my / base_scale, data);
		
		if (Mouse.hasWheel()) {
			gui.mouseWheel(Mouse.getDWheel());
		}
		Debugger.stop("gui-mouse");
		
		Debugger.start("gui-update");
		gui.update(delta / 1000f);
		Debugger.stop("gui-update");
		
		Debugger.stop("gui");
		
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).doBehaviour(null, wind, particles, delta / 1000f);
		}
		
		particles.sort(new Comparator<Particle>() {
			@Override
			public int compare(Particle o1, Particle o2) {
				return o1.getClass().equals(o2.getClass()) ? 0 : 1;
			}
		});
		
		if (InputUtils.isActionPressed(in, InputUtils.EXIT)) {
			exit();
		}
		
		xv = x - px;
		yv = y - py;
		xv /= delf;
		yv /= delf;
		
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			b.update(container, game, delf, x, y, x - px, y - py, this, bullets);
		}
		
		AudioManager.setPlayerPos(x, 0, y);
		AudioManager.setPlayerVelocity(xv / RPGConfig.getTileSize(), 0, yv / RPGConfig.getTileSize());
		
		AudioManager.setMusic(getMusic());
		
		px = x;
		py = y;
		
		if (shake > 0) {
			shake -= delf;
		}
		
		postUpdate(container, game, delf, in);
	}
	
	/**
	 * Called before any updates occur
	 * @param container The current game container.
	 * @param game The current game
	 * @param delf A float value in seconds measuring the time since the last update.
	 * @param in The current input
	 */
	public void preUpdate(GameContainer container, StateBasedGame game, float delf, Input in) {
		
	}
	
	/**
	 * Called after all updates occur
	 * @param container The current game container.
	 * @param game The current game
	 * @param delf A float value in seconds measuring the time since the last update.
	 * @param in The current input
	 */
	public void postUpdate(GameContainer container, StateBasedGame game, float delf, Input in) {
		
	}
	
	/**
	 * The command to execute when exit is pressed.
	 * @see io.github.tomaso2468.rpgonline.RPGConfig#setKeyInput(io.github.tomaso2468.rpgonline.input.KeyboardInputProvider)
	 * @see io.github.tomaso2468.rpgonline.RPGConfig#setControllerInput(io.github.tomaso2468.rpgonline.input.ControllerInputProvider)
	 * @see io.github.tomaso2468.rpgonline.input.InputUtils
	 * @see io.github.tomaso2468.rpgonline.input.InputUtils#EXIT
	 */
	public void exit() {
		AudioManager.dispose();
		System.exit(0);
	}
	
	/**
	 * Gets the music that should play during the battle
	 * @return A ambient music object or null to play no music.
	 */
	public AmbientMusic getMusic() {
		return null;
	}
	
	/**
	 * Gets the bounds that objects can move in within the state.
	 * @param c The game container.
	 * @return A rectangle based around the centre of the screen being at (0,0)
	 */
	public Rectangle getStateBounds(GameContainer c) {
		return new Rectangle(-c.getWidth() / 2f / base_scale / zoom - 32, -c.getHeight() / 2f / base_scale / zoom - 32, c.getWidth() / base_scale / zoom + 64, c.getHeight() / base_scale / zoom + 64);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getID() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void scale(GameContainer container, float base) {
		this.base_scale = base;
	}
	
	/**
	 * Gets the currently used post processing effects.
	 * @return A PostEffect or null if no effect is active.
	 */
	public PostEffect getPost() {
		return post;
	}

	/**
	 * Sets the post processing effects.
	 * @param post A PostEffect or null if no effect should be active.
	 */
	public void setPost(PostEffect post) {
		if (post == null) {
			post = NullPostProcessEffect.INSTANCE;
		}
		this.post = post;
	}
	
	/**
	 * Gets the background of the state.
	 * @return A sky layer or null if no background exists.
	 */
	public SkyLayer getSky() {
		return sky;
	}

	/**
	 * Sets the background of the state.
	 * @param sky A sky layer or null to remove the background.
	 */
	public void setSky(SkyLayer sky) {
		this.sky = sky;
	}
	
	/**
	 * Adds a particle to the state.
	 * @param p A particle object.
	 */
	public void addParticle(Particle p) {
		particles.add(p);
	}
	
	/**
	 * Adds a collection of particles to the state.
	 * @param p A collection of particles.
	 */
	public void addParticles(Collection<? extends Particle> p) {
		particles.addAll(p);
	}
	
	/**
	 * Removes all particles from the state.
	 */
	public void clearParticles() {
		particles.clear();
	}
	
	/**
	 * Gets the current GUI.
	 * @return A GUI object.
	 */
	public GUI getGUI() {
		return gui;
	}
	
	/**
	 * Sets the current GUI.
	 * @param gui a GUI object.
	 */
	public void setGUI(GUI gui) {
		this.gui = gui;
	}
	
	/**
	 * Adds a bullet to the state.
	 * @param p The bullet to add.
	 */
	public void addBullet(Bullet p) {
		bullets.add(p);
	}
	
	/**
	 * Adds a collection of bullets to the state.
	 * @param p A collection of bullets.
	 */
	public void addBullet(Collection<? extends Bullet> p) {
		bullets.addAll(p);
	}
	
	/**
	 * Removes all bullets.
	 */
	public void clearBullets() {
		bullets.clear();
	}

}

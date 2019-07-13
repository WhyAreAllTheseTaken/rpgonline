package rpgonline.bullet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import rpgonline.RPGConfig;
import rpgonline.audio.AmbientMusic;
import rpgonline.audio.AudioManager;
import rpgonline.debug.Debugger;
import rpgonline.input.InputUtils;
import rpgonline.part.Particle;
import rpgonline.post.MultiEffect;
import rpgonline.post.NullPostProcessEffect;
import rpgonline.post.PostEffect;
import rpgonline.sky.SkyLayer;
import rpgonline.state.BaseScaleState;
import rpgonline.state.GUIItem;
import rpgonline.texture.TextureMap;

public class BulletState extends BasicGameState implements BaseScaleState {
	private final int id;
	
	public float x;
	public float y;
	public float px;
	public float py;
	public float xv;
	public float yv;
	public boolean center_camera = false;
	
	public float zoom = 1;
	public float base_scale = 1;
	public boolean player_top = true;
	
	public float shake = 0f;
	public double speed = 10;
	public float wind;
	
	protected SkyLayer sky;
	
	protected Image buffer;
	protected PostEffect post = null;
	
	private List<GUIItem> guis = new ArrayList<GUIItem>();
	protected List<Particle> particles = Collections.synchronizedList(new ArrayList<Particle>(256));
	protected List<Bullet> bullets = new ArrayList<Bullet>();
	
	public BulletState(int id) {
		this.id = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		Debugger.initRender();
	}

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
		for (GUIItem gui : guis) {
			if (gui.isCentered()) {
				g.translate(container.getWidth() / 2, container.getHeight() / 2);
				g.scale(base_scale, base_scale);
			} else {
				g.scale(base_scale, base_scale);
			}
			gui.render(g, container, game, container.getWidth() / base_scale, container.getHeight() / base_scale);

			g.resetTransform();
			g.setWorldClip(world_clip);
			g.setClip(clip);
		}
		
		Debugger.stop("gui");
		
		g.flush();
		
		Debugger.stop("render");
	}
	
	public void render2(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		List<Particle> particles = new ArrayList<Particle>(this.particles);
		
		Debugger.start("sky");
		if (sky != null) {
			sky.render(g, container, x, y, 0, null, Color.white);
		}
		Debugger.stop("sky");
		
		g.translate(container.getWidth() / 2, container.getHeight() / 2);

		g.scale(base_scale, base_scale);

		g.scale(zoom, zoom);
		
		if (shake > 0) {
			g.translate((float) (FastMath.random() * shake * 5), (float) (FastMath.random() * shake * 5));
		}
		
		float sx = center_camera ? (float) (x * RPGConfig.getTileSize()) : 0;
		float sy = center_camera ? (float) (y * RPGConfig.getTileSize()) : 0;
		
		if (!player_top) {
			Debugger.start("player");
			renderPlayer(container, game, g, sx, sy);
			Debugger.stop("player");
		}
		
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
					
					img.drawEmbedded(b.getX() - sx - img.getWidth() / 2, b.getY() - sy - img.getHeight() / 2, img.getWidth(), img.getHeight());
				}
			}
		}
		
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
						img.drawEmbedded(particle.getX() * RPGConfig.getTileSize() - sx, particle.getY() * RPGConfig.getTileSize() - sy, img.getWidth(), img.getHeight());
					}
				}
			}
			
			Debugger.stop("particles");
		}
		
		if (current != null) current.endUse();
		
		if (player_top) {
			Debugger.start("player");
			renderPlayer(container, game, g, sx, sy);
			Debugger.stop("player");
		}
	}
	
	public void renderPlayer(GameContainer container, StateBasedGame game, Graphics g, float sx, float sy) {
		
	}

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
		
		for (GUIItem gui : guis) {
			gui.update(container, game, delta);
		}
		
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
		
		for (Bullet b : bullets) {
			b.update(container, game, delf, x, y, x - px, y - py, this, bullets);
		}
		
		xv = x - px;
		yv = y - px;
		AudioManager.setPlayerPos(x, 0, y);
		AudioManager.setPlayerVelocity((x - px) / delf, 0, (y - py) / delf);
		
		px = x;
		py = y;
		
		postUpdate(container, game, delf, in);
	}
	
	public void preUpdate(GameContainer container, StateBasedGame game, float delf, Input in) {
		
	}
	
	public void postUpdate(GameContainer container, StateBasedGame game, float delf, Input in) {
		
	}
	
	public void exit() {
		AudioManager.dispose();
		System.exit(0);
	}
	
	public AmbientMusic getMusic() {
		return null;
	}
	
	public Rectangle getStateBounds(GameContainer c) {
		return new Rectangle(-c.getWidth() / 2f / base_scale / zoom, -c.getHeight() / 2f / base_scale / zoom, c.getWidth() / base_scale / zoom, c.getHeight() / base_scale / zoom);
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void scale(float base) {
		this.base_scale = base;
	}
	
	public PostEffect getPost() {
		return post;
	}

	public void setPost(PostEffect post) {
		if (post == null) {
			post = NullPostProcessEffect.INSTANCE;
		}
		this.post = post;
	}
	
	public SkyLayer getSky() {
		return sky;
	}

	public void setSky(SkyLayer sky) {
		this.sky = sky;
	}
	
	public void addParticle(Particle p) {
		particles.add(p);
	}
	
	public void addParticles(Collection<? extends Particle> p) {
		particles.addAll(p);
	}
	
	public void clearParticles() {
		particles.clear();
	}
	
	public void addGUI(GUIItem e) {
		guis.add(e);
	}

	public void removeGUI(GUIItem o) {
		guis.remove(o);
	}

	public void addGUI(int index, GUIItem element) {
		guis.add(index, element);
	}

}

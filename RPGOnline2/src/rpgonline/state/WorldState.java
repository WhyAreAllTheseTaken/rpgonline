package rpgonline.state;

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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import rpgonline.RPGConfig;
import rpgonline.audio.AmbientMusic;
import rpgonline.audio.AudioManager;
import rpgonline.entity.Entity;
import rpgonline.input.InputUtils;
import rpgonline.net.ServerManager;
import rpgonline.post.MultiEffect;
import rpgonline.post.NullPostProcessEffect;
import rpgonline.post.PostEffect;
import rpgonline.sky.SkyLayer;
import rpgonline.texture.TextureMap;
import rpgonline.texture.TileTexture;
import rpgonline.texture.entity.EntityTexture;
import rpgonline.tile.Tile;
import rpgonline.world.LightSource;
import rpgonline.world.World;

/**
 * A state for rendering worlds
 * 
 * @author Tomas
 */
public class WorldState extends BasicGameState {
	/**
	 * This state's id.
	 */
	private int id;
	/**
	 * The cached {@code x} position of the player.
	 */
	private double x;
	/**
	 * The cached {@code y} position of the player.
	 */
	private double y;
	/**
	 * The world zoom.
	 */
	public float zoom = 2.75f;
	/**
	 * The strength of the shake effect.
	 */
	public float shake = 1f;
	/**
	 * The scale to scale the graphics by. This also effects GUI.
	 */
	public float base_scale = 1f;
	/**
	 * A list of GUI elements.
	 */
	private List<GUIItem> guis = new ArrayList<GUIItem>();
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
	private Image buffer;

	/**
	 * A buffer for lighting.
	 */
	private Image lightBuffer;

	/**
	 * The current shader effect.
	 */
	private PostEffect post = new MultiEffect();

	private boolean gui = true;

	private float gui_cooldown = 0.25f;
	
	private SkyLayer sky;

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
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		if (buffer == null) {
			buffer = new Image(container.getWidth(), container.getHeight());
		} else if (container.getWidth() != buffer.getWidth() || container.getHeight() != buffer.getHeight()) {
			buffer.destroy();
			buffer = new Image(container.getWidth(), container.getHeight());
		}

		render2(container, game, g);

		g.resetTransform();

		g.copyArea(buffer, 0, 0);

		post.doPostProcess(container, game, buffer, g);

		if (gui) {
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
		}
	}

	List<TileTexture> textures = new ArrayList<>();
	List<EntityTexture> entityTextures = new ArrayList<>();
	
	/**
	 * A method that renders the world.
	 */
	public void render2(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		List<LightSource> lights = ServerManager.getClient().getWorld().getLights();

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
		
		World world = ServerManager.getClient().getWorld();
		
		if (sky != null) {
			sky.render(g, container, x, y, 0, world, world.getLightColor());
		}

		g.translate(container.getWidth() / 2, container.getHeight() / 2);

		g.scale(base_scale, base_scale);

		g.scale(zoom, zoom);

		if (shake > 0) {
			g.translate((float) (FastMath.random() * shake * 5), (float) (FastMath.random() * shake * 5));
		}
		float sx = (float) (x * RPGConfig.getTileSize());
		float sy = (float) (y * RPGConfig.getTileSize());

		long dist_x = (long) (container.getWidth() / base_scale / zoom / RPGConfig.getTileSize() / 2) + 5;
		long dist_y = (long) (container.getHeight() / base_scale / zoom / RPGConfig.getTileSize() / 2) + 10;

		List<Entity> entities1 = ServerManager.getClient().getWorld().getEntities();
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
		
//		if (lightBuffer == null) {
//			lightBuffer = new Image(container.getWidth(), container.getHeight());
//		} else if (container.getWidth() != lightBuffer.getWidth() || container.getHeight() != lightBuffer.getHeight()) {
//			lightBuffer.destroy();
//			lightBuffer = new Image(container.getWidth(), container.getHeight());
//		}
//
//		Graphics lg = lightBuffer.getGraphics();
//
//		lg.clear();
//
//		lg.setDrawMode(Graphics.MODE_NORMAL);
//
//		Color wl = world.getLightColor();
//
//		float red = wl.r;
//		float green = wl.g;
//		float blue = wl.b;
//
//		float lum = (red + green + blue) / 3;
//
//		float rscale = FastMath.max(lum * 10, 1);
//		float gscale = FastMath.max(lum * 10, 1);
//		float bscale = FastMath.max(lum * 10, 1);
//
//		lg.setColor(wl);
//		lg.fillRect(0, 0, container.getWidth(), container.getHeight());
//
//		lg.translate(container.getWidth() / 2, container.getHeight() / 2);
//
//		lg.scale(base_scale, base_scale);
//		lg.pushTransform();
//
//		lg.scale(zoom, zoom);
//		if (shake > 0) {
//			lg.translate((float) (FastMath.random() * shake * 5), (float) (FastMath.random() * shake * 5));
//		}

//		/*
//		 * for (long y = miy; y < may; y++) { for (long x = mix; x < max; x++) {
//		 * renderLightingTile(g, x, y, x - sx, y - sy, lights, world); } }
//		 */
//
//		lg.setDrawMode(Graphics.MODE_SCREEN);
//
//		for (LightSource l : lights) {
//			Image img = TextureMap.getTexture("light").getScaledCopy(l.getBrightness() / 2);
//
//			img.setImageColor(l.getR() / rscale, l.getG() / gscale, l.getB() / bscale);
//
//			lg.drawImage(img,
//					(float) l.getLX() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 2 - sx * RPGConfig.getTileSize(),
//					(float) l.getLY() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 2
//							- sy * RPGConfig.getTileSize());
//		}
//		
//		lightBuffer.flushPixelData();

		long mix = (long) (x - dist_x);
		long max = (long) (x + dist_x);
		long miy = (long) (y - dist_y);
		long may = (long) (y + dist_y);
		long miz = world.getMinZ();
		long maz = world.getMaxZ();
		
		float wind = ServerManager.getClient().getWind();

		Image current = null;

		for (long z = maz; z >= FastMath.min(-1, miz); z--) {
			for (long y = miy; y <= may; y++) {
				for (long x = mix; x <= max; x++) {
					Tile t = world.getTile(x, y, z);
					String state = world.getTileState(x, y, z);
					expandTexture(t.getTexture(), textures, x, y, z, world, t, state);
					
					for(TileTexture tex : textures) {
						if (tex.isCustom()) {
							if (current != null) current.endUse();
							
							tex.render(g, x, y, z, world, state, t, x * RPGConfig.getTileSize() + tex.getX() - sx, y * RPGConfig.getTileSize() + tex.getY() - sy, wind);
							
							if (current != null) current.startUse();
						} else {
							Image img = TextureMap.getTexture(tex.getTexture(x, y, z, world, state, t));
							
							if (img != null) {
								if(TextureMap.getSheet(img) != current) {
									if (current != null) current.endUse();
									current = TextureMap.getSheet(img);
									current.startUse();
								}
								img.drawEmbedded(x * RPGConfig.getTileSize() + tex.getX() - sx, y * RPGConfig.getTileSize() + tex.getY() - sy, img.getWidth(), img.getHeight());
							}
						}
					}
					
					textures.clear();
					
					if(z == -1) {
						for (Entity e : entities) {
							if(!e.isFlying()) {
								if (FastMath.round(e.getX() + 0.5f) == x
										&& FastMath.floor(e.getY() - 0.25) == y) {
									entityTextures.clear();
									
									if (current != null) current.endUse();
									
									if (current != null) current.startUse();
									
									expandTexture(e.getTexture(), entityTextures, e.getX(), e.getY(), world, wind, e);
									
									for(EntityTexture tex : entityTextures) {
										if (tex.isCustom()) {
											if (current != null) current.endUse();
											
											tex.render(g, x, y, -1, world, e, sx, sy, wind);
											
											if (current != null) current.startUse();
										} else {
											Image img = TextureMap.getTexture(tex.getTexture(e.getX(), e.getY(), -1, world, e, wind));
											
											if (img != null) {
												if(TextureMap.getSheet(img) != current) {
													if (current != null) current.endUse();
													current = TextureMap.getSheet(img);
													current.startUse();
												}
												img.drawEmbedded((float) e.getX() * RPGConfig.getTileSize() + tex.getX() - sx, (float) e.getY() * RPGConfig.getTileSize() + tex.getY() - sy, img.getWidth(), img.getHeight());
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		for (Entity e : entities) {
			if(e.isFlying()) {
				entityTextures.clear();
				
				expandTexture(e.getTexture(), entityTextures, e.getX(), e.getY(), world, wind, e);
				
				for(EntityTexture tex : entityTextures) {
					if (tex.isCustom()) {
						if (current != null) current.endUse();
						
						tex.render(g, x, y, -3, world, e, sx, sy, wind);
						
						if (current != null) current.startUse();
					} else {
						Image img = TextureMap.getTexture(tex.getTexture(e.getX(), e.getY(), -3, world, e, wind));
						
						if (img != null) {
							if(TextureMap.getSheet(img) != current) {
								if (current != null) current.endUse();
								current = TextureMap.getSheet(img);
								current.startUse();
							}
							img.drawEmbedded((float) e.getX() * RPGConfig.getTileSize() + tex.getX() - sx, (float) e.getY() * RPGConfig.getTileSize() + tex.getY() - sy, img.getWidth(), img.getHeight());
						}
					}
				}
			}
		}
		
		if(current != null) current.endUse();
		
		g.resetTransform();
		
		Graphics sg = g;

		if (lightBuffer == null) {
			lightBuffer = new Image(container.getWidth(), container.getHeight());
		} else if (container.getWidth() != lightBuffer.getWidth() || container.getHeight() != lightBuffer.getHeight()) {
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
					(float) l.getLX() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 2 - sx * RPGConfig.getTileSize(),
					(float) l.getLY() * RPGConfig.getTileSize() - 256 * l.getBrightness() / 2
							- sy * RPGConfig.getTileSize());
		}

		sg.resetTransform();

		sg.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
		sg.drawImage(lightBuffer, 0, 0);
		sg.setDrawMode(Graphics.MODE_NORMAL);
	}
	
	protected void expandTexture(TileTexture t, List<TileTexture> l, long x, long y, long z, World world, Tile tile, String state) {
		if (t.isPure()) {
			l.add(t);
		} else {
			TileTexture[] ta = t.getTextures(x, y, z, world, state, tile);
			
			for (TileTexture t2 : ta) {
				expandTexture(t2, l, x, y, z, world, tile, state);
			}
		}
	}
	
	protected void expandTexture(EntityTexture t, List<EntityTexture> l, double x, double y, World world, float wind, Entity e) {
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
	 * {@inheritDoc}
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		this.last_delta = delta;

		float delf = delta / 1000f;

		x = ServerManager.getClient().getPlayerX() + 0.5;
		y = ServerManager.getClient().getPlayerY() - 0.1;

		Input in = container.getInput();
		
		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_NORTH))) {
			ServerManager.getClient().walkY(-1);
		} else {
			ServerManager.getClient().walkY(0);
		}
		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_EAST))) {
			ServerManager.getClient().walkX(1);
		} else {
			ServerManager.getClient().walkX(0);
		}
		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_WEST))) {
			ServerManager.getClient().walkX(-1);
		} else {
			ServerManager.getClient().walkX(0);
		}
		if (Keyboard.isKeyDown(RPGConfig.getKeyInput().getKeyCodeForAction(InputUtils.WALK_SOUTH))) {
			ServerManager.getClient().walkY(1);
		} else {
			ServerManager.getClient().walkY(0);
		}
		
//		if (in.getControllerCount() > 0) {
//			if(RPGConfig.getControllerInput().isLeftHanded()) {
//				ServerManager.getClient().walkY(in.getAxisValue(0, 2));
//				ServerManager.getClient().walkX(in.getAxisValue(0, 3));
//			} else {
//				ServerManager.getClient().walkY(in.getAxisValue(0, 0));
//				ServerManager.getClient().walkX(in.getAxisValue(0, 1));
//			}
//		}
		ServerManager.getClient().setSprint(InputUtils.isActionPressed(in, InputUtils.SPRINT));
		
		if (InputUtils.isActionPressed(in, InputUtils.ZOOM_IN)) {
			zoom *= 1 + 0.25f * delf;
		}
		if (InputUtils.isActionPressed(in, InputUtils.ZOOM_OUT)) {
			zoom /= 1 + 0.25f * delf;
		}
		if (InputUtils.isActionPressed(in, InputUtils.ZOOM_NORMAL)) {
			zoom = 2.25f;
		}
		if (zoom > 190) {
			zoom = 190;
		}
		if (zoom < 0.5f) {
			zoom = 0.5f;
		}

		ServerManager.getClient().getWorld().doUpdateClient();

		if (InputUtils.isActionPressed(in, InputUtils.EXIT)) {
			exit();
		}

		if (shake > 0) {
			shake -= delf;
		}

		for (GUIItem gui : guis) {
			gui.update(container, game, delta);
		}

		for (UpdateHook hook : hooks) {
			hook.update(container, game, delta);
		}

		AmbientMusic music = ServerManager.getClient().getMusic();
		AudioManager.setMusic(music);

		gui_cooldown -= delf;
		if (InputUtils.isActionPressed(in, InputUtils.GUI_TOGGLE) && gui_cooldown <= 0) {
			gui = !gui;
			gui_cooldown = 0.25f;
		}
	}

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
	 * Adds a GUI to the state.
	 * 
	 * @param e A GUI element.
	 */
	public void addGUI(GUIItem e) {
		guis.add(e);
	}

	/**
	 * Removes a GUI from the state.
	 * 
	 * @param o A GUI element.
	 */
	public void removeGUI(GUIItem o) {
		guis.remove(o);
	}

	/**
	 * Adds a GUI element at a specific point.
	 * 
	 * @param index   The index of the element.
	 * @param element A GUI element.
	 */
	public void addGUI(int index, GUIItem element) {
		guis.add(index, element);
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

	public boolean isGuiShown() {
		return gui;
	}

	public void setGuiShown(boolean gui) {
		this.gui = gui;
	}

	public SkyLayer getSky() {
		return sky;
	}

	public void setSky(SkyLayer sky) {
		this.sky = sky;
	}
}

package rpgonline;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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
	 * @throws SlickException Indicates an internal error occured in the game.
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
	 * @throws SlickException Indicates an internal error occured in the game.
	 */
	public abstract void load(GameContainer container, RPGGame game, LoadCounter counter) throws SlickException;
}

package rpgonline.cutscene;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.Transition;

/**
 * A state that holds cutscene information.
 * @author Tomas
 */
public abstract class CutsceneState extends BasicGameState {
	/**
	 * The ID of the state.
	 */
	private final int id;
	/**
	 * The ID of the state to switch to after the cutscene is finished.
	 */
	private final int nextState;
	/**
	 * The cutscene to play.
	 */
	private final Cutscene scene;
	/**
	 * The transition to play when leaving this state.
	 */
	private final Transition leave;
	/**
	 * The transition to play when entering the next state.
	 */
	private final Transition enter;
	/**
	 * Constructs a new Cutscene state.
	 * @param id The ID of the state.
	 * @param nextState The next state ID to switch to after the cutscene plays.
	 * @param scene The scene to play.
	 * @param leave The transition to play when leaving this state.
	 * @param enter The transition to play when entering the next state.
	 */
	public CutsceneState(int id, int nextState, Cutscene scene, Transition leave, Transition enter) {
		super();
		this.id = id;
		this.nextState = nextState;
		this.scene = scene;
		this.leave = leave;
		this.enter = enter;
	}
	/**
	 * Constructs a new Cutscene state.
	 * @param id The ID of the state.
	 * @param nextState The next state ID to switch to after the cutscene plays.
	 * @param scene The scene to play.
	 * @param c The color of the transition screen.
	 */
	public CutsceneState(int id, int nextState, Cutscene scene, Color c) {
		this(id, nextState, scene, new FadeOutTransition(c, 500), new FadeInTransition(c, 50));
	}
	/**
	 * Constructs a new Cutscene state.
	 * @param id The ID of the state.
	 * @param nextState The next state ID to switch to after the cutscene plays.
	 * @param scene The scene to play.
	 */
	public CutsceneState(int id, int nextState, Cutscene scene) {
		this(id, nextState, scene, Color.black);
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
		scene.render(container, game, g, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		scene.update(delta / 1000f);
		if (scene.isDone()) {
			game.enterState(nextState, leave, enter);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		scene.reset();
		scene.onStart();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getID() {
		return id;
	}

}

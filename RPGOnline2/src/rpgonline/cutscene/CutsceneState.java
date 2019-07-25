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

public abstract class CutsceneState extends BasicGameState {
	private final int id;
	private final int nextState;
	private final Cutscene scene;
	private final Transition leave;
	private final Transition enter;
	public CutsceneState(int id, int nextState, Cutscene scene, Transition leave, Transition enter) {
		super();
		this.id = id;
		this.nextState = nextState;
		this.scene = scene;
		this.leave = leave;
		this.enter = enter;
	}
	public CutsceneState(int id, int nextState, Cutscene scene, Color c) {
		this(id, nextState, scene, new FadeOutTransition(c, 500), new FadeInTransition(c, 50));
	}
	public CutsceneState(int id, int nextState, Cutscene scene) {
		this(id, nextState, scene, Color.black);
	}
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		scene.render(container, game, g, this);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		scene.update(delta / 1000f);
		if (scene.isDone()) {
			game.enterState(nextState, leave, enter);
		}
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		scene.reset();
		scene.onStart();
	}

	@Override
	public int getID() {
		return id;
	}

}

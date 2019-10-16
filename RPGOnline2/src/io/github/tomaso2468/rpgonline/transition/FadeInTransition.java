package io.github.tomaso2468.rpgonline.transition;

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.GameState;
import io.github.tomaso2468.rpgonline.render.Renderer;

public class FadeInTransition implements Transition {
	private Color c;
	private float time;
	private float maxTime;

	public FadeInTransition(Color c, float time) {
		super();
		this.c = c;
		this.time = time;
		this.maxTime = time;
	}

	@Override
	public void update(Game game, GameState currentState, GameState nextState, float delta) {
		time -= delta;
	}

	@Override
	public void render(Game game, GameState currentState, GameState nextState, Renderer renderer) {
		renderer.drawQuad(0, 0, game.getWidth(), game.getHeight(), c.multiply(new Color(1, 1, 1, time / maxTime)));
	}

	@Override
	public boolean isDone() {
		return time < 0;
	}

}

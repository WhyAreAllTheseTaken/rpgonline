package io.github.tomaso2468.rpgonline.transition;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.GameState;
import io.github.tomaso2468.rpgonline.render.Renderer;

public interface Transition {
	public void start(Game game, GameState currentState, GameState nextState);
	public void update(Game game, GameState currentState, GameState nextState, float delta);
	public void render(Game game, GameState currentState, GameState nextState, Renderer renderer);
	public boolean isDone();
}

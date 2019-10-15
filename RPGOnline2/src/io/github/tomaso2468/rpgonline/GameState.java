package io.github.tomaso2468.rpgonline;

import io.github.tomaso2468.rpgonline.render.Renderer;

public interface GameState {
	public default void enterState(Game game) {
		
	}
	
	public default void exitState(Game game) {
		
	}
	
	public default void init(Game game) {
		
	}

	public default void update(Game game, float delta) {

	}
	
	public void render(Game game, Renderer renderer);
	
	public int getID();
}

package io.github.tomaso2468.rpgonline;

import io.github.tomaso2468.rpgonline.render.RenderException;
import io.github.tomaso2468.rpgonline.render.Renderer;

public interface GameState {
	public default void enterState(Game game) throws RenderException {
		
	}
	
	public default void exitState(Game game) throws RenderException {
		
	}
	
	public default void init(Game game) throws RenderException {
		
	}

	public default void update(Game game, float delta) throws RenderException {

	}
	
	public void render(Game game, Renderer renderer) throws RenderException ;
	
	public int getID();
}

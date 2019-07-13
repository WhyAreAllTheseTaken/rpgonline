package rpgonline.bullet;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public interface Bullet {
	public float getX();
	public float getY();
	
	public void update(GameContainer container, StateBasedGame game, float delf, float px, float py, float xv, float yv, BulletState state, List<Bullet> bullets);
	
	public default boolean isCustom() {
		return false;
	}
	public int getTexture();
	public default void render(float px, float py, float xv, float yv, BulletState state, Graphics g, float sx, float sy) {
		
	}
	public default boolean isCombined() {
		return false;
	}
	public default Image renderEmbedded(float px, float py, float xv, float yv, BulletState state, Graphics g, Image current, float sx, float sy) {
		return current;
	}
}

package rpgonline.part;

import java.util.List;

import org.newdawn.slick.Graphics;

import rpgonline.texture.TextureMap;
import rpgonline.world.World;

public interface Particle {
	public default boolean isCustom() {
		return false;
	}
	
	public default boolean isLightAffected() {
		return true;
	}
	
	public default int getTexture() {
		return -1;
	}
	
	public default void render(Graphics g, float sx, float sy) {
		g.drawImage(TextureMap.getTexture(getTexture()), sx, sy);
	}
	
	public float getX();
	public float getY();
	
	public default void doBehaviour(World w, float wind, List<Particle> particles, float delta) {
		
	}
	
	public float getAlpha();
}

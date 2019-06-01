package rpgonline.texture.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import rpgonline.RPGConfig;
import rpgonline.entity.Entity;
import rpgonline.texture.TextureMap;
import rpgonline.world.World;

public interface EntityTexture {
	public default boolean isPure() {
		return true;
	}
	
	public default boolean isCustom() {
		return false;
	}
	
	public int getTexture(double x, double y, double z, World w, Entity e, float wind);
	
	public default EntityTexture[] getTextures() {
		return new EntityTexture[] {this};
	}
	
	public default void render(Graphics g, double x, double y, double z, World w, Entity e, float sx, float sy, float wind, Color light) {
		Image img = TextureMap.getTexture(getTexture(x, y, z, w, e, wind));
		
		if (img == null) {
			return;
		}
		
		if (isLightAffected()) {
			g.drawImage(img, sx * RPGConfig.getTileSize(), sy * RPGConfig.getTileSize(), light);
		} else {
			g.drawImage(img, sx * RPGConfig.getTileSize(), sy * RPGConfig.getTileSize());
		}
	}
	
	public default float getX() {
		return 0;
	}
	
	public default float getY() {
		return 0;
	}
	
	public default boolean isLightAffected() {
		return true;
	}
}

package rpgonline.texture.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import rpgonline.entity.Entity;
import rpgonline.world.World;

public class GroupedEntityTexture implements EntityTexture {
	private final EntityTexture[] textures;
	
	public GroupedEntityTexture(EntityTexture[] textures) {
		super();
		this.textures = textures;
	}
	
	@Override
	public boolean isPure() {
		return false;
	}

	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		return -1;
	}
	
	@Override
	public void render(Graphics g, double x, double y, double z, World w, Entity e, float sx, float sy, float wind,
			Color light) {
		for (EntityTexture t : getTextures()) {
			t.render(g, x, y, z, w, e, sx, sy, wind, light);
		}
	}
	
	@Override
	public EntityTexture[] getTextures() {
		return textures;
	}

}

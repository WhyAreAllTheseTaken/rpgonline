package rpgonline.texture.entity;

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
	public EntityTexture[] getTextures(double x, double y, double z, World w, Entity e, float wind) {
		return textures;
	}

}

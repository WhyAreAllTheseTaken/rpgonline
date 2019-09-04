package rpgonline.texture.entity;

import rpgonline.entity.Entity;
import rpgonline.world.World;

/**
 * An entity texture made of multiple entity textures.
 * @author Tomas
 *
 */
public class GroupedEntityTexture implements EntityTexture {
	/**
	 * The array of textures.
	 */
	private final EntityTexture[] textures;
	
	/**
	 * Constructs a new GroupedEntityTexture.
	 * @param textures The array of textures.
	 */
	public GroupedEntityTexture(EntityTexture[] textures) {
		super();
		this.textures = textures;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPure() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		return -1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityTexture[] getTextures(double x, double y, double z, World w, Entity e, float wind) {
		return textures;
	}

}

package rpgonline.part;

import java.util.List;

import rpgonline.texture.TextureMap;
import rpgonline.world.World;

/**
 * A particle with a texture.
 * @author Tomas
 *
 */
public class TextureParticle implements Particle {
	/**
	 * The X position of the particle.
	 */
	protected float x;
	/**
	 * The Y position of the particle.
	 */
	protected float y;
	/**
	 * The texture of the particle.
	 */
	protected int t;
	/**
	 * The time the particle has left on the screen.
	 */
	protected float time;
	/**
	 * The maximum time of this particle.
	 */
	private final float otime;
	
	/**
	 * Constructs a new TextureParticle.
	 * @param s The texture of the particle.
	 * @param x The X position of the particle.
	 * @param y The Y position of the particle.
	 * @param time The time the particle has left on the screen.
	 */
	public TextureParticle(String s, float x, float y, float time) {
		this.t = TextureMap.getTextureIndex(s);
		this.x = x;
		this.y = y;
		this.time = time;
		this.otime = time;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture() {
		return t;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getX() {
		return x;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getY() {
		return y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doBehaviour(World w, float wind, List<Particle> particles, float delta) {
		time -= delta;
		
		if (time <= 0) {
			particles.remove(this);
			return;
		}
		
		x += wind * delta;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getAlpha() {
		if (time > otime - 1f) {
			return 1 - (time - (otime - 1f));
		}
		return time / otime;
	}

}

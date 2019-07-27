package rpgonline.bullet;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import rpgonline.texture.TextureMap;

/**
 * A bullet with a single texture and constant velocity.
 * @author Tomas
 */
public class BasicBullet implements Bullet {
	/**
	 * The X velocity of the bullet.
	 */
	public float xv;
	/**
	 * The Y velocity of the bullet.
	 */
	public float yv;
	/**
	 * The X position of the bullet.
	 */
	public float x;
	/**
	 * The Y position of the bullet.
	 */
	public float y;
	/**
	 * The bullets texture.
	 */
	public int texture;
	
	/**
	 * Constructs a new {@code BasicBullet}.
	 * @param x The X position of the bullet.
	 * @param y The Y position of the bullet.
	 * @param xv The X velocity of the bullet.
	 * @param yv The Y velocity of the bullet.
	 * @param texture The bullet texture.
	 * 
	 * @see rpgonline.texture.TextureMap#getTextureIndex(String)
	 */
	public BasicBullet(float x, float y, float xv, float yv, String texture) {
		super();
		this.xv = xv;
		this.yv = yv;
		this.x = x;
		this.y = y;
		this.texture = TextureMap.getTextureIndex(texture);
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
	public int getTexture() {
		return texture;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, float delf, float px, float py, float xv, float yv,
			BulletState state, List<Bullet> bullets) {
		x += this.xv * delf;
		y += this.yv * delf;
		
		if (!state.getStateBounds(container).contains(x, y)) {
			bullets.remove(this);
		}
	}

}

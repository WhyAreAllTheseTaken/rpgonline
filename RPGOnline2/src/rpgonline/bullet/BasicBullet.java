package rpgonline.bullet;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import rpgonline.texture.TextureMap;

public class BasicBullet implements Bullet {
	public float xv;
	public float yv;
	public float x;
	public float y;
	public int texture;
	
	public BasicBullet(float x, float y, float xv, float yv, String texture) {
		super();
		this.xv = xv;
		this.yv = yv;
		this.x = x;
		this.y = y;
		this.texture = TextureMap.getTextureIndex(texture);
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public int getTexture() {
		return texture;
	}

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

package rpgonline.part;

import java.util.List;

import rpgonline.texture.TextureMap;
import rpgonline.world.World;

public class TextureParticle implements Particle {
	protected float x;
	protected float y;
	protected int t;
	protected float time;
	private final float otime;
	
	public TextureParticle(String s, float x, float y, float time) {
		this.t = TextureMap.getTextureIndex(s);
		this.x = x;
		this.y = y;
		this.time = time;
		this.otime = time;
	}
	
	@Override
	public int getTexture() {
		return t;
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
	public void doBehaviour(World w, float wind, List<Particle> particles, float delta) {
		time -= delta;
		
		if (time <= 0) {
			particles.remove(this);
			return;
		}
		
		x += wind * delta;
	}

	@Override
	public float getAlpha() {
		if (time > otime - 1f) {
			return 1 - (time - (otime - 1f));
		}
		return time / otime;
	}

}

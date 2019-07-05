package rpgonline.part;

import java.util.List;

import rpgonline.texture.TextureMap;
import rpgonline.world.World;

public class TextureParticle implements Particle {
	private float x;
	private float y;
	private int t;
	private float time;
	
	public TextureParticle(String s, float x, float y, float time) {
		this.t = TextureMap.getTextureIndex(s);
		this.x = x;
		this.y = y;
		this.time = time;
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

}

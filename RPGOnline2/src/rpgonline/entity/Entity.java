package rpgonline.entity;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import rpgonline.world.LightSource;
import rpgonline.world.World;

public abstract class Entity {
	
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isFlying() {
		// TODO Auto-generated method stub
		return false;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g, double x, double y, long z,
			World world, List<LightSource> lights, float sx, float sy) {
		
	}

}

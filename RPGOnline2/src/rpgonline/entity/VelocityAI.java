package rpgonline.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import rpgonline.tile.Tile;
import rpgonline.world.World;

public class VelocityAI implements EntityAI {
	public static double SPEED_MIN = 1e-5;

	@Override
	public void doAI(Entity e, World w, float wind, List<Entity> entities) {

	}

	public void doVelocity(Entity e, World w, List<Entity> entities) {
		tryMove(e.getX() + e.getDX(), e.getY() + e.getDY(), e, w, entities);

		e.setDX(e.getDX() / e.getDouble("weight_f"));
		e.setDY(e.getDY() / e.getDouble("weight_f"));

		if (FastMath.abs(e.getDX()) < SPEED_MIN) {
			e.setDX(0);
		}
		if (FastMath.abs(e.getDY()) < SPEED_MIN) {
			e.setDY(0);
		}
	}

	public void tryMove(double x, double y, Entity e, World w, List<Entity> entities) {
		if (!isCollision(x, y, e, w, entities)) {
			e.setX(x);
			e.setY(y);
		} else {
			doCollision(e, x, y, w, entities);
		}
	}

	public void doCollision(Entity e, double x, double y, World w, List<Entity> entities) {
		e.setDX(0);
		e.setDY(0);
	}

	public static boolean isCollision(double x, double y, Entity e, World w, List<Entity> entities) {
		long wx = FastMath.round(x);
		long wy = FastMath.round(y);
		
		if (!e.isSolid()) {
			return false;
		}

		List<Shape> hitboxes = new ArrayList<Shape>();
		for (int tx = (int) (Math.round(x) - 1); tx <= (int) (Math.round(x) + 1); tx++) {
			for (int ty = (int) (Math.round(y) - 1); ty <= (int) (Math.round(y) + 1); ty++) {
				Tile t = w.getTile(tx, ty, -1);
				
				if (t.isSolid(w.getTileState(tx, ty, -1))) {
					hitboxes.add(t.getHitBox().transform(Transform.createTranslateTransform(tx, ty)));
				}
			}
		}
		
		for (Entity e2 : entities) {
			if (e2 != e) {
				if(e2.isSolid()) {
					hitboxes.add(e2.getHitBox());
				}
			}
		}
		
		for (Shape s : hitboxes) {
			if (e.getHitBox().intersects(s)) {
				return true;
			}
		}
		
		if (!w.getTile(wx, wy, 0).isSolid(w.getTileState(wx, wy, 0))) {
			return true;
		}

		return false;
	}

	public static boolean onGround(Entity e, World w, List<Entity> entities) {
		long wx = FastMath.round(e.getX());
		long wy = FastMath.round(e.getY());

		return w.getTile(wx, wy, 0).isSolid(w.getTileState(wx, wy, 0));
	}

}

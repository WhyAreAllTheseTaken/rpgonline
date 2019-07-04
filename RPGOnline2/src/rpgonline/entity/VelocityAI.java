package rpgonline.entity;

import java.util.List;

import org.apache.commons.math3.util.FastMath;

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

	public boolean isCollision(double x, double y, Entity e, World w, List<Entity> entities) {
		long wx = FastMath.round(x);
		long wy = FastMath.round(y);

		if (w.getTile(wx, wy, -1).isSolid(w.getTileState(wx, wy, -1))) {
			return true;
		}

		if (!w.getTile(wx, wy, 0).isSolid(w.getTileState(wx, wy, 0))) {
			return true;
		}

		for (Entity e2 : entities) {
			if (e2 != e) {
				if (dist(e.getX(), e.getY(), e2.getX(), e2.getY()) < 0.25) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean onGround(Entity e, World w, List<Entity> entities) {
		long wx = FastMath.round(e.getX());
		long wy = FastMath.round(e.getY());

		return w.getTile(wx, wy, 0).isSolid(w.getTileState(wx, wy, 0));
	}

}

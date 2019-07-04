package rpgonline.entity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.FastMath;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import rpgonline.tile.Tile;
import rpgonline.world.World;

public interface EntityAI {
	public void doAI(Entity e, World w, float wind, List<Entity> entities);

	public default Path pathfind(Entity e, World w, double tx, double ty, int searchDistance, List<Entity> entities,
			boolean brave, Map<String, Tile> tileRegistry, EntityManager em) {
		//XXX Appolgies to any sane being that tries to read this.
		return new AStarPathFinder(
				constructMapFromWorld(e, (int) e.getX(), (int) e.getY(), w, searchDistance + 5, getValuedTiles(tileRegistry),
						getDislikedTiles(tileRegistry), getDangerousTiles(tileRegistry), brave, entities, getScaredEntities(em)),
				searchDistance, false).findPath(new Mover() {
				}, (int) e.getX(), (int) e.getY(), (int) tx, (int) ty);
	}
	
	public default Entity locateNearest(Entity e, List<Entity> entities, String entity_id, int searchDistance) {
		return locateNearest(e, entities, new Comparable<Entity>() {

			@Override
			public int compareTo(Entity o) {
				return o.getEntityID().compareTo(entity_id);
			}
		}, searchDistance);
	}
	
	public default Entity locateNearest(Entity e, List<Entity> entities, Comparable<Entity> c, int searchDistance) {
		Entity nearest = null;
		double ndist = Double.POSITIVE_INFINITY;
		
		for (Entity entity : entities) {
			if(c.compareTo(entity) == 0) {
				double dist = FastMath.hypot(entity.getX() - e.getX(), entity.getY() - e.getY());
				
				if (dist <= searchDistance) {
					if(dist < ndist) {
						nearest = entity;
						ndist = dist;
					}
				}
			}
		}
		
		return nearest;
	}
	
	public default double dist(double x1, double y1, double x2, double y2) {
		return FastMath.hypot(x1 - x2, y1 - y2);
	}

	public default double dist(Entity e, Entity e2) {
		return FastMath.hypot(e.getX() - e2.getX(), e.getY() - e2.getY());
	}
	
	public default List<Tile> getValuedTiles(Map<String, Tile> tileRegistry) {
		return Collections.emptyList();
	}

	public default List<Tile> getDislikedTiles(Map<String, Tile> tileRegistry) {
		return Collections.emptyList();
	}

	public default List<Tile> getDangerousTiles(Map<String, Tile> tileRegistry) {
		return Collections.emptyList();
	}

	public default List<String> getScaredEntities(EntityManager m) {
		return Collections.emptyList();
	}

	public static TileBasedMap constructMapFromWorld(Entity e, final int cx, final int cy, final World w,
			final int searchDistance, final List<Tile> valued, final List<Tile> disliked, final List<Tile> dangerous,
			final boolean brave, final List<Entity> entities, final List<String> scared_entities) {
		final int offsetX = cx - searchDistance;
		final int offsetY = cy - searchDistance;
		return new TileBasedMap() {
			@Override
			public int getWidthInTiles() {
				return searchDistance * 2;
			}

			@Override
			public int getHeightInTiles() {
				return searchDistance * 2;
			}

			@Override
			public void pathFinderVisited(int x, int y) {

			}

			@Override
			public boolean blocked(PathFindingContext context, int tx, int ty) {
				if(e.isFlying()) {
					return false;
				}
				return w.getTile(tx + offsetX, ty + offsetY, -1).isSolid(w.getTileState(tx + offsetX, ty + offsetY, -1))
						|| !w.getTile(tx + offsetX, ty + offsetY, 0)
								.isSolid(w.getTileState(tx + offsetX, ty + offsetY, 0));
			}

			@Override
			public float getCost(PathFindingContext context, int tx, int ty) {
				float ex = tx + offsetX;
				float ey = ty + offsetY;

				Tile t = w.getTile(tx + offsetX, ty + offsetY, -1);

				float c = 1;

				if(!e.isFlying()) {
					if (valued.contains(t)) {
						c *= 0.75f;
					}

					if (disliked.contains(t)) {
						c *= brave ? 2.5f : 5f;
					}

					if (dangerous.contains(t)) {
						c *= brave ? 50f : 100f;
					}
				}

				for (Entity e2 : entities) {
					if (e2.isSolid()) {
						if (FastMath.hypot(e2.getX() - ex, e2.getY() - ey) < 0.25) {
							c *= 2f;
						}
					}
					if (scared_entities.contains(e2.getEntityID())) {
						c *= FastMath.max(1 / FastMath.hypot(e2.getX() - ex, e2.getY() - ey), 1);
					}
				}

				return c;
			}
		};
	}
}

package io.github.tomaso2468.rpgonline.pathfinding;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

import io.github.tomaso2468.rpgonline.RPGConfig;
import io.github.tomaso2468.rpgonline.threading.ContinuousPool;

public final class PathFindingManager {
	/**
	 * Prevent instantiation
	 */
	private PathFindingManager() {

	}

	/**
	 * The thread pool to use for this path finding manager.
	 */
	private static final ContinuousPool pool;
	
	static {
		if (RPGConfig.getPathfindingThreads() > 0) {
			pool = new ContinuousPool(
					RPGConfig.getPathfindingThreads(),
					RPGConfig.getPathfindingSleepDelay(),
					RPGConfig.getPathfindingSleepTime());
		} else {
			pool = null;
		}
	}
	
	/**
	 * The ID of the last pathfinding operation.
	 */
	private static BigInteger lastID = BigInteger.valueOf(0);
	
	/**
	 * Gets the next available path ID.
	 * @return A string representing a decimal value.
	 */
	private static synchronized String getNextID() {
		lastID = lastID.add(BigInteger.valueOf(1));
		
		return lastID.toString();
	}
	
	/**
	 * Gets the number of pathfinding operations that have occurred.
	 * @return A string representing a decimal value.
	 */
	public static synchronized String getPathfindingOperations() {
		return lastID.toString();
	}
	
	/**
	 * Schedules a pathfind to the specified point.
	 * @param path The pathfinder to use.
	 * @param sx The x coordinate of the start location
	 * @param sy The y coordinate of the start location
	 * @param tx The x coordinate of the target location
	 * @param ty The y coordinate of the target location
	 * @return A future object representing the call.
	 */
	public static Future<Path> pathfind(PathFinder path, int sx, int sy, int tx, int ty) {
		getNextID();
		
		Future<Path> f = pool.submit(new Callable<Path>() {
			@Override
			public Path call() throws Exception {
				return path.findPath(new Mover() {}, sx, sy, tx, ty);
			}
		});
		
		return f;
	}
}

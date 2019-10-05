/*
BSD 3-Clause License

Copyright (c) 2019, Tomas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package io.github.tomaso2468.rpgonline.world2d.pathfinding;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

import io.github.tomaso2468.rpgonline.RPGConfig;
import io.github.tomaso2468.rpgonline.threading.ContinuousPool;

/**
 * A class for managing pathfinding.
 * @author Tomaso2468
 *
 */
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
	public static synchronized long getPathfindingOperations() {
		return Long.parseLong(lastID.toString());
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

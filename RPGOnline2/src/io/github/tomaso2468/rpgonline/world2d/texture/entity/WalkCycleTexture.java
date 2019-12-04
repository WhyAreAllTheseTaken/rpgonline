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
package io.github.tomaso2468.rpgonline.world2d.texture.entity;

import io.github.tomaso2468.rpgonline.Game;
import io.github.tomaso2468.rpgonline.world2d.World;
import io.github.tomaso2468.rpgonline.world2d.entity.Entity;

/**
 * A texture for an entity walk cycle.
 * @author Tomaso2468
 *
 */
public class WalkCycleTexture implements EntityTexture {
	/**
	 * North textures.
	 */
	private final int[] north;
	/**
	 * East textures.
	 */
	private final int[] east;
	/**
	 * South textures.
	 */
	private final int[] south;
	/**
	 * West textures.
	 */
	private final int[] west;
	/**
	 * The speed of the walk cycle.
	 */
	private final double speed;
	/**
	 * Length of the walk cycle in frames.
	 */
	private final int len;
	/**
	 * The X offset for this texture.
	 */
	private final float x;
	/**
	 * The Y offset for this texture.
	 */
	private final float y;
	
	/**
	 * Constructs a new WalkCycleTexture.
	 * @param base The base texture ID to use.
	 * @param north The offset for north.
	 * @param east The offset for east.
	 * @param south The offset for south.
	 * @param west The offset for west.
	 * @param len The length of each walk cycle.
	 * @param speed The speed of the walk cycle.
	 * @param x The X offset for this texture.
	 * @param y The Y offset for this texture.
	 */
	public WalkCycleTexture(Game game, String base, int north, int east, int south, int west, int len, double speed, float x, float y) {
		this.north = new int[len];
		this.east = new int[len];
		this.south = new int[len];
		this.west = new int[len];
		this.speed = speed;
		this.len = len;
		
		for (int i = 0; i < len; i++) {
			this.north[i] = game.getTextures().getTextureIndex(base + "." + (north + i));
			this.east[i] = game.getTextures().getTextureIndex(base + "." + (east + i));
			this.south[i] = game.getTextures().getTextureIndex(base + "." + (south + i));
			this.west[i] = game.getTextures().getTextureIndex(base + "." + (west + i));
		}
		
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructs a new WalkCycleTexture.
	 * @param base The base texture ID to use.
	 * @param north The offset for north.
	 * @param east The offset for east.
	 * @param south The offset for south.
	 * @param west The offset for west.
	 * @param len The length of each walk cycle.
	 * @param speed The speed of the walk cycle.
	 */
	public WalkCycleTexture(Game game, String base, int north, int east, int south, int west, int len, double speed) {
		this(game, base, north, east, south, west, len, speed, 0, 0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getTexture(double x, double y, double z, World w, Entity e, float wind) {
		int[] data;
		int index;
		switch(e.getDirection()) {
		case EAST:
			data = east;
			index = (int) Math.floor(Math.abs(x * speed % len));
			break;
		case NORTH:
			data = north;
			index = (int) Math.floor(Math.abs(y * speed % len));
			break;
		case SOUTH:
			data = south;
			index = (int) Math.floor(Math.abs(y * speed % len));
			break;
		case WEST:
			index = (int) Math.floor(Math.abs(x * speed % len));
			data = west;
			break;
		default:
			throw new IllegalArgumentException("Unknown direction: " + e.getDirection());
		}
		
		return data[index];
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

}

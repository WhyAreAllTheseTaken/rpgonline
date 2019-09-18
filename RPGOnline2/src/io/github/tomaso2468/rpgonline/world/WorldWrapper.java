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
package io.github.tomaso2468.rpgonline.world;

import java.io.IOException;
import java.util.List;

import org.newdawn.slick.Color;

import io.github.tomaso2468.rpgonline.entity.Entity;
import io.github.tomaso2468.rpgonline.tile.Tile;

/**
 * A class to allow overriding of one method of a world without subclassing.
 * @author Tomas
 *
 */
public class WorldWrapper implements World {
	/**
	 * The world to wrap.
	 */
	private final World world;

	/**
	 * Constructs a new WorldWrapper.
	 * @param world The world to wrap.
	 */
	public WorldWrapper(World world) {
		this.world = world;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tile getTile(long x, long y, long z) {
		return world.getTile(x, y, z);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTileState(long x, long y, long z) {
		return world.getTileState(x, y, z);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTile(long x, long y, long z, Tile tile, String state) {
		world.setTile(x, y, z, tile, state);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMinZ() {
		return world.getMinZ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMaxZ() {
		return world.getMaxZ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMinX() {
		return world.getMinX();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMaxX() {
		return world.getMaxX();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMinY() {
		return world.getMinY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getMaxY() {
		return world.getMaxY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLight(LightSource light) {
		world.addLight(light);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLight(LightSource light) {
		world.removeLight(light);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LightSource> getLights() {
		return world.getLights();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color getLightColor() {
		return world.getLightColor();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doUpdateClient() {
		world.doUpdateClient();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doUpdateServer() {
		world.doUpdateServer();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save() throws IOException {
		world.save();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTile(long x, long y, long z, Tile tile) {
		world.setTile(x, y, z, tile);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAreaID(long x, long y, long z, String id) {
		world.setAreaID(x, y, z, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAreaID(long x, long y, long z) {
		return world.getAreaID(x, y, z);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBiomeID(long x, long y, long z, int id) {
		world.setBiomeID(x, y, z, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBiomeID(long x, long y, long z) {
		return world.getBiomeID(x, y, z);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Entity> getEntities() {
		return world.getEntities();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setState(long x, long y, long z, String state) {
		world.setState(x, y, z, state);
	}
}

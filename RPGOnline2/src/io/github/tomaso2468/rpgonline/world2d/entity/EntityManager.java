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
package io.github.tomaso2468.rpgonline.world2d.entity;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import io.github.tomaso2468.rpgonline.world2d.texture.entity.EntityTexture;

/**
 * A class used for managing entity IDs, types, textures and AIs.
 * @author Tomas
 */
public class EntityManager {
	/**
	 * The last used entity ID.
	 */
	private BigInteger lastID = BigInteger.valueOf(System.currentTimeMillis());
	/**
	 * The map of all entity AIs.
	 */
	private Map<String, EntityAI> ais = new HashMap<>();
	/**
	 * The map of all entity textures.
	 */
	private Map<String, EntityTexture> textures = new HashMap<>();
	
	/**
	 * Gets the next available entity ID.
	 * @return
	 */
	public String getNextID() {
		lastID = lastID.add(BigInteger.valueOf(1));
		return lastID.toString(16);
	}
	
	/**
	 * Reloads an entity's ID.
	 * @param id The ID to reload.
	 * @return A new ID.
	 */
	public String getNewID(String id) {
		BigInteger v = new BigInteger(id, 16);
		
		if (v.compareTo(lastID) <= 0) {
			return getNextID();
		} else {
			lastID = v;
		}
		
		return id;
	}

	/**
	 * Gets an entity type's texture.
	 * @param entity_id The entity type to get the texture of.
	 * @return An entity texture or null if none exists.
	 */
	public EntityTexture getEntityTexture(String entity_id) {
		return textures.get(entity_id);
	}
	
	/**
	 * Gets an entity type's AI.
	 * @param entity_id The entity type to get the AI of.
	 * @return An entity AI or null if none exists.
	 */
	public EntityAI getAI(String entity_id) {
		return ais.get(entity_id);
	}
	
	/**
	 * Registers an entity type with this entity manager.
	 * @param entity_id An entity ID.
	 * @param t An entity texture.
	 * @param ai An entity AI.
	 */
	public void register(String entity_id, EntityTexture t, EntityAI ai) {
		textures.put(entity_id, t);
		ais.put(entity_id, ai);
	}
}

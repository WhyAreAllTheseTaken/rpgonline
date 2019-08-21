package rpgonline.entity;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import rpgonline.texture.entity.EntityTexture;

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

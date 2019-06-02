package rpgonline.entity;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import rpgonline.texture.entity.EntityTexture;

public class EntityManager {
	private BigInteger nextID = BigInteger.valueOf(System.currentTimeMillis() / 50);
	private Map<String, EntityAI> ais = new HashMap<>();
	private Map<String, EntityTexture> textures = new HashMap<>();
	
	public String getNextID() {
		nextID = nextID.add(BigInteger.valueOf(1));
		return nextID.toString(16);
	}
	
	public String getNewID(String id) {
		BigInteger v = new BigInteger(id, 16);
		
		if(v.compareTo(nextID) <= 0) {
			return getNextID();
		} else {
			nextID = v;
		}
		
		return id;
	}

	public EntityTexture getEntityTexture(String entity_id) {
		return textures.get(entity_id);
	}
	
	public EntityAI getAI(String entity_id) {
		return ais.get(entity_id);
	}
}

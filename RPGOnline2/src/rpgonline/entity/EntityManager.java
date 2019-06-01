package rpgonline.entity;

import java.math.BigInteger;

import rpgonline.texture.entity.EntityTexture;

public class EntityManager {
	private BigInteger nextID = BigInteger.valueOf(System.currentTimeMillis() / 50);
	
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
		// TODO Auto-generated method stub
		return null;
	}
}

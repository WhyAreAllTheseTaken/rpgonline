package rpgonline.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.util.Log;

import rpgonline.abt.Tag;
import rpgonline.abt.TagBoolean;
import rpgonline.abt.TagByteArray;
import rpgonline.abt.TagDouble;
import rpgonline.abt.TagFloat;
import rpgonline.abt.TagGroup;
import rpgonline.abt.TagInt;
import rpgonline.abt.TagLong;
import rpgonline.abt.TagString;
import rpgonline.net.ServerManager;
import rpgonline.net.packet.UpdatePacket;
import rpgonline.net.packet.UpdatePacket.UBoolean;
import rpgonline.net.packet.UpdatePacket.UDouble;
import rpgonline.net.packet.UpdatePacket.UFloat;
import rpgonline.net.packet.UpdatePacket.UInt;
import rpgonline.net.packet.UpdatePacket.ULong;
import rpgonline.net.packet.UpdatePacket.UString;
import rpgonline.net.packet.UpdatePacket.UTag;
import rpgonline.texture.entity.EntityTexture;

/**
 * A class for tile independent game objects.
 * 
 * @author Tomas
 */
public class Entity {
	private final Map<String, Object> objects = new HashMap<String, Object>();
	private final Map<String, Integer> ints = new HashMap<String, Integer>();
	private final Map<String, Long> longs = new HashMap<String, Long>();
	private final Map<String, Float> floats = new HashMap<String, Float>();
	private final Map<String, Double> doubles = new HashMap<String, Double>();
	private final Map<String, String> strings = new HashMap<String, String>();
	private final Map<String, Boolean> bools = new HashMap<String, Boolean>();
	private final Map<String, TagGroup> tags = new HashMap<String, TagGroup>();

	private EntityTexture t;
	private boolean packet;
	
	public Entity(EntityManager m, String entity_id, boolean packet) {
		setString("id", m.getNextID());
		setString("entity_id", entity_id);
		setBoolean("solid", true);
		
		t = m.getEntityTexture(entity_id);
	}
	
	public Entity(EntityManager m, TagGroup g, boolean packet) {
		for(Tag t : g.getTags()) {
			if (t instanceof TagDouble) {
				doubles.put(t.getName(), ((TagDouble) t).getData());
				continue;
			}
			if (t instanceof TagString) {
				strings.put(t.getName(), ((TagString) t).getData());
				continue;
			}
			if (t instanceof TagFloat) {
				floats.put(t.getName(), ((TagFloat) t).getData());
				continue;
			}
			if (t instanceof TagGroup) {
				tags.put(t.getName(), ((TagGroup) t));
				continue;
			}
			if (t instanceof TagBoolean) {
				bools.put(t.getName(), ((TagBoolean) t).getData());
				continue;
			}
			if (t instanceof TagInt) {
				ints.put(t.getName(), ((TagInt) t).getData());
				continue;
			}
			if (t instanceof TagLong) {
				longs.put(t.getName(), ((TagLong) t).getData());
				continue;
			}
			if (t instanceof TagByteArray) {
				try {
					ByteArrayInputStream in = new ByteArrayInputStream(((TagByteArray) t).getData());
					ObjectInputStream ois = new ObjectInputStream(in);
					
					Object o = ois.readObject();
					
					ois.close();
					
					objects.put(t.getName(), o);
				} catch (IOException | ClassNotFoundException e) {
					Log.error("Error reading object from entity data.", e);
				}
				continue;
			}
		}
		
		t = m.getEntityTexture(getString("entity_id"));
	}

	/**
	 * A reusable array for lighting.
	 */
	public static final float[] colors = new float[3];

	/**
	 * Gets the {@code x} position of this tile.
	 * 
	 * @return A double value.
	 */
	public double getX() {
		return getDouble("x");
	}

	/**
	 * Gets the {@code y} position of this tile.
	 * 
	 * @return A double value.
	 */
	public double getY() {
		return getDouble("y");
	}

	public void setX(double x) {
		setDouble("x", x);
	}

	public void setY(double y) {
		setDouble("y", y);
	}

	public boolean isFlying() {
		return false;
	}

	public final void setString(String name, String value) {
		if (strings.get(name) != null && value != null) {
			if (strings.get(name).equals(value)) {
				return;
			}
		}
		if (value == null) {
			value = "null";
		}
		if (packet) ServerManager.getServer().updateEntity(new UString(getID(), name, value));
		strings.put(name, value);
	}

	public final String getString(String name) {
		String s = strings.get(name);
		if (s == null) {
			s = "null";
		}
		if (s.equals("null")) {
			return null;
		} else {
			return s;
		}
	}

	public final void setInt(String name, int value) {
		if (getInt(name) == value) {
			return;
		}
		if (packet) ServerManager.getServer().updateEntity(new UInt(getID(), name, value));
		ints.put(name, value);
	}

	public final int getInt(String name) {
		Integer i = ints.get(name);
		if (i == null) {
			return 0;
		} else {
			return i;
		}
	}

	public void setLong(String name, long value) {
		if (getLong(name) == value) {
			return;
		}
		if (packet) ServerManager.getServer().updateEntity(new ULong(getID(), name, value));
		longs.put(name, value);
	}

	public final long getLong(String name) {
		Long i = longs.get(name);
		if (i == null) {
			return 0;
		} else {
			return i;
		}
	}

	public final void setFloat(String name, float value) {
		if (getFloat(name) == value) {
			return;
		}
		if (packet) ServerManager.getServer().updateEntity(new UFloat(getID(), name, value));
		floats.put(name, value);
	}

	public final float getFloat(String name) {
		Float i = floats.get(name);
		if (i == null) {
			return 0;
		} else {
			return i;
		}
	}

	public final void setBoolean(String name, boolean value) {
		if (getBoolean(name) == value) {
			return;
		}
		if (packet) ServerManager.getServer().updateEntity(new UBoolean(getID(), name, value));
		bools.put(name, value);
	}

	public final boolean getBoolean(String name) {
		Boolean i = bools.get(name);
		if (i == null) {
			return false;
		} else {
			return i;
		}
	}

	public final void setDouble(String name, double value) {
		if (getDouble(name) == value) {
			return;
		}
		if (packet) ServerManager.getServer().updateEntity(new UDouble(getID(), name, value));
		doubles.put(name, value);
	}

	public final double getDouble(String name) {
		Double i = doubles.get(name);
		if (i == null) {
			return 0;
		} else {
			return i;
		}
	}

	@Deprecated
	public final void setObject(String name, Object value) {
		if (getObject(name) == value) {
			return;
		}
		if (packet) ServerManager.getServer().updateEntity(new UpdatePacket.UObject(getID(), name, value));
		objects.put(name, value);
	}

	@Deprecated
	public final Object getObject(String name) {
		return objects.get(name);
	}

	@Deprecated
	public final void pushObject(String name) {
		if (packet) ServerManager.getServer().updateEntity(new UpdatePacket.UObject(getID(), name, getObject(name)));
	}
	
	public final void setTag(String name, TagGroup value) {
		if (getTag(name) == value) {
			return;
		}
		if (packet) ServerManager.getServer().updateEntity(new UTag(getID(), name, value));
		tags.put(name, value);
	}

	public final TagGroup getTag(String name) {
		return tags.get(name);
	}

	public final void pushtag(String name) {
		if (packet) ServerManager.getServer().updateEntity(new UTag(getID(), name, getTag(name)));
	}

	public final String getID() {
		return getString("id");
	}
	
	public final void reloadID(EntityManager m) {
		setString("id", m.getNewID(getID()));
	}

	public EntityTexture getTexture() {
		return t;
	}
	
	public TagGroup toABT(String name) {
		TagGroup g = new TagGroup(name);
		
		for(Entry<String, Double> e : doubles.entrySet()) {
			g.add(new TagDouble(e.getKey(), e.getValue()));
		}
		for(Entry<String, String> e : strings.entrySet()) {
			g.add(new TagString(e.getKey(), e.getValue()));
		}
		for(Entry<String, Float> e : floats.entrySet()) {
			g.add(new TagFloat(e.getKey(), e.getValue()));
		}
		for(Entry<String, TagGroup> e : tags.entrySet()) {
			g.add(e.getValue());
		}
		for(Entry<String, Long> e : longs.entrySet()) {
			g.add(new TagLong(e.getKey(), e.getValue()));
		}
		for(Entry<String, Integer> e : ints.entrySet()) {
			g.add(new TagInt(e.getKey(), e.getValue()));
		}
		for(Entry<String, Boolean> e : bools.entrySet()) {
			g.add(new TagBoolean(e.getKey(), e.getValue()));
		}
		for(Entry<String, Object> e : objects.entrySet()) {
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(out);
				
				oos.writeObject(e.getValue());
				
				oos.flush();
				oos.close();
				
				g.add(new TagByteArray(e.getKey(), out.toByteArray()));
			} catch (IOException e1) {
				Log.error("Error writing object to entity data.", e1);
			}
		}
		
		return g;
	}

	public boolean isPacket() {
		return packet;
	}

	public void setPacket(boolean packet) {
		this.packet = packet;
	}

	public boolean isSolid() {
		return getBoolean("solid");
	}

	public String getEntityID() {
		return getString("entity_id");
	}
}

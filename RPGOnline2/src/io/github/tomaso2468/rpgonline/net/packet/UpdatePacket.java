package io.github.tomaso2468.rpgonline.net.packet;

import java.io.Serializable;
import java.util.List;

import org.newdawn.slick.util.Log;

import io.github.tomaso2468.rpgonline.abt.TagGroup;
import io.github.tomaso2468.rpgonline.entity.Entity;

/**
 * Packet used for sending entity updates.
 * @author Tomas
 *
 */
public class UpdatePacket implements Serializable, NetPacket {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5002558159873982231L;
	protected final String id;
	protected final String key;

	public UpdatePacket(String id, String key) {
		this.id = id;
		this.key = key;
	}

	public final String getID() {
		return id;
	}

	public final String getKey() {
		return key;
	}

	public void apply(List<Entity> entities) {

	}

	public final static Entity find(String id, List<Entity> entities) {
		for (Entity e : entities) {
			if (e.getID().equals(id)) {
				return e;
			}
		}
		Log.error("Could not find entity with ID: " + id);
		return null;
	}

	public static final class UString extends UpdatePacket {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3412222174024913364L;
		private final String value;

		public UString(String id, String key, String value) {
			super(id, key);
			this.value = value;
		}

		@Override
		public void apply(List<Entity> entities) {
			Entity e = find(id, entities);

			if (e != null) {
				e.setString(key, value);
			}
		}
	}

	public static final class ULong extends UpdatePacket {
		/**
		 * 
		 */
		private static final long serialVersionUID = 911455609401431766L;
		private final long value;

		public ULong(String id, String key, long value) {
			super(id, key);
			this.value = value;
		}

		@Override
		public void apply(List<Entity> entities) {
			Entity e = find(id, entities);

			if (e != null) {
				e.setLong(key, value);
			}
		}
	}

	public static final class UInt extends UpdatePacket {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7821323750052297530L;
		private final int value;

		public UInt(String id, String key, int value) {
			super(id, key);
			this.value = value;
		}

		@Override
		public void apply(List<Entity> entities) {
			Entity e = find(id, entities);

			if (e != null) {
				e.setInt(key, value);
			}
		}
	}

	public static final class UFloat extends UpdatePacket {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3064533092636622464L;
		private final float value;

		public UFloat(String id, String key, float value) {
			super(id, key);
			this.value = value;
		}

		@Override
		public void apply(List<Entity> entities) {
			Entity e = find(id, entities);

			if (e != null) {
				e.setFloat(key, value);
			}
		}
	}

	public static final class UDouble extends UpdatePacket {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7627383259073996968L;
		private final double value;

		public UDouble(String id, String key, double value) {
			super(id, key);
			this.value = value;
		}

		@Override
		public void apply(List<Entity> entities) {
			Entity e = find(id, entities);

			if (e != null) {
				e.setDouble(key, value);
			}
		}
	}

	public static final class UBoolean extends UpdatePacket {
		/**
		 * 
		 */
		private static final long serialVersionUID = -9146361567186310116L;
		private final boolean value;

		public UBoolean(String id, String key, boolean value) {
			super(id, key);
			this.value = value;
		}

		@Override
		public void apply(List<Entity> entities) {
			Entity e = find(id, entities);

			if (e != null) {
				e.setBoolean(key, value);
			}
		}
	}

	public static final class UTag extends UpdatePacket {
		/**
		 * 
		 */
		private static final long serialVersionUID = -9146361567186310116L;
		private final TagGroup value;

		public UTag(String id, String key, TagGroup value) {
			super(id, key);
			this.value = value;
		}

		@Override
		public void apply(List<Entity> entities) {
			Entity e = find(id, entities);

			if (e != null) {
				e.setTag(key, value);
			}
		}
	}
}

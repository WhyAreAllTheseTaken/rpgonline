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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import io.github.tomaso2468.rpgonline.Direction;
import io.github.tomaso2468.abt.*;
import io.github.tomaso2468.rpgonline.net.ServerManager;
import io.github.tomaso2468.rpgonline.net.packet.UpdatePacket.UBoolean;
import io.github.tomaso2468.rpgonline.net.packet.UpdatePacket.UDouble;
import io.github.tomaso2468.rpgonline.net.packet.UpdatePacket.UFloat;
import io.github.tomaso2468.rpgonline.net.packet.UpdatePacket.UInt;
import io.github.tomaso2468.rpgonline.net.packet.UpdatePacket.ULong;
import io.github.tomaso2468.rpgonline.net.packet.UpdatePacket.UString;
import io.github.tomaso2468.rpgonline.net.packet.UpdatePacket.UTag;
import io.github.tomaso2468.rpgonline.world2d.net.Server2D;
import io.github.tomaso2468.rpgonline.world2d.texture.entity.EntityTexture;

/**
 * <p>
 * A class for tile independent game objects. If entities are stored or sent
 * over the network it is best not to override methods in this class and instead
 * set values.
 * </p>
 * 
 * @author Tomas
 */
public class Entity {
	/**
	 * The version number used to identify this format of entity storage.
	 * 
	 * Changes are listed below.
	 * <table>
	 * <tr>
	 * <th>Version Number</th>
	 * <th>Library Version</th>
	 * <th>Date</th>
	 * <th>Commit</th>
	 * <th>Details</th>
	 * </tr>
	 * 
	 * <tr>
	 * <td>0</td>
	 * <td>0.3.6</td>
	 * <td>2019-03-02</td>
	 * <td>4d3b8c9eacd80006976d85c36bc64a6c9c764b37 (old repository)</td>
	 * <td>Entity data stored using java object serialisation. Entity was an
	 * interface.</td>
	 * </tr>
	 * 
	 * <tr>
	 * <td>1</td>
	 * <td>0.3.12-d0</td>
	 * <td>2019-04-13</td>
	 * <td>36988c98b4389ff5ff508e49d65f43c80b162f28 (old repository)</td>
	 * <td>Entities now store their variables in a map so updates can be
	 * detected.</td>
	 * </tr>
	 * 
	 * <tr>
	 * <td>2</td>
	 * <td>0.3.12</td>
	 * <td>2019-04-19</td>
	 * <td>5cd7e61a747fc1b85b6eb095998e0e05c9e37e5e (old repository)</td>
	 * <td>Strings cannot be null.</td>
	 * </tr>
	 * 
	 * <tr>
	 * <td>3</td>
	 * <td>0.7.0-d0</td>
	 * <td>2019-05-31</td>
	 * <td>40d60a650e3efc623eb203a331643499dd1426a1</td>
	 * <td>Entities are now managed through the EntityManager (including IDs)</td>
	 * </tr>
	 * 
	 * <tr>
	 * <td>4</td>
	 * <td>0.7.0-d0</td>
	 * <td>2019-06-01</td>
	 * <td>4c9805d64416e272f1044fabff70b51281993372</td>
	 * <td>Entity textures are now stored separately in the EntityManager. ABT is
	 * now used instead of serialisation. Object storage is deprecated.</td>
	 * </tr>
	 * 
	 * <tr>
	 * <td>5</td>
	 * <td>0.7.0-d0</td>
	 * <td>2019-06-02</td>
	 * <td>2ac5d0ba085ee42fd97ee0fa66517a084397c35b</td>
	 * <td>Entity AI is now stored in the EntityManager. Added entity type tag.</td>
	 * </tr>
	 * 
	 * <tr>
	 * <td>6</td>
	 * <td>0.7.0-d1</td>
	 * <td>2019-06-02</td>
	 * <td>38740de498da8dbd6d9769f24cd1745654101088</td>
	 * <td>Entity types do not need classes anymore.</td>
	 * </tr>
	 * 
	 * <tr>
	 * <td>7</td>
	 * <td>0.7.0-d17</td>
	 * <td>2019-07-04</td>
	 * <td>afb27b4ed4bd6f311a4a88fc7ca668a460fe4aed</td>
	 * <td>Velocity is now built in to the entity class.</td>
	 * </tr>
	 * 
	 * <tr>
	 * <td>8</td>
	 * <td>0.7.0-d18</td>
	 * <td>2019-07-06</td>
	 * <td>6aeef5e5796d8e33d0818936808a03aee67ac6d7</td>
	 * <td>Hitboxes are now built in.</td>
	 * </tr>
	 * 
	 * <tr>
	 * <td>9</td>
	 * <td>0.7.0-d18</td>
	 * <td>2019-07-10</td>
	 * <td>180e98fd63044dfcff4afabb6adc3c01b0b26f18</td>
	 * <td>Direction is now built in.</td>
	 * </tr>
	 * 
	 * <tr>
	 * <td>10</td>
	 * <td>0.7.0-d18</td>
	 * <td>2019-08-20</td>
	 * <td>85149f64ef9f25baeac760cd02f2ef2ff3866e46</td>
	 * <td>Byte arrays are now stored as tag groups containing byte tags..</td>
	 * </tr>
	 * 
	 * </table>
	 */
	public static final int VERSION = 10;
	/**
	 * A map of int values stored in this entity.
	 */
	private final Map<String, Integer> ints = new HashMap<String, Integer>();
	/**
	 * A map of long values stored in this entity.
	 */
	private final Map<String, Long> longs = new HashMap<String, Long>();
	/**
	 * A map of float values stored in this entity.
	 */
	private final Map<String, Float> floats = new HashMap<String, Float>();
	/**
	 * A map of double values stored in this entity.
	 */
	private final Map<String, Double> doubles = new HashMap<String, Double>();
	/**
	 * A map of string values stored in this entity.
	 */
	private final Map<String, String> strings = new HashMap<String, String>();
	/**
	 * A map of boolean values stored in this entity.
	 */
	private final Map<String, Boolean> bools = new HashMap<String, Boolean>();
	/**
	 * A map of tag values stored in this entity.
	 */
	private final Map<String, TagGroup> tags = new HashMap<String, TagGroup>();

	/**
	 * This entity's texture.
	 */
	private EntityTexture t;
	/**
	 * Determines if this entity should push changes to the server.
	 */
	private boolean packet;
	/**
	 * The entity manager this entity is registered to.
	 */
	private EntityManager m;
	/**
	 * The hitbox cache for this entity.
	 */
	private Rectangle hitbox;

	/**
	 * Constructs a new entity. (This method should be run on the server).
	 * 
	 * @param m         The entity manager for this entity.
	 * @param entity_id The entity_type_id for this entity.
	 * @param packet    Determines if this entity should push changes to the server.
	 *                  {@code true} to enabled, {@code false otherwise}.
	 */
	public Entity(EntityManager m, String entity_id, boolean packet) {
		this.packet = false;
		setString("id", m.getNextID());
		setString("entity_id", entity_id);
		setBoolean("solid", true);
		setBoolean("flying", false);
		setDouble("hitbox_a", 0);

		setX(0);
		setY(0);
		setDX(0);
		setDY(0);

		t = m.getEntityTexture(entity_id);

		setHitBox(new Rectangle(0, 0, 0, 0));

		m.getAI(entity_id).init(this);

		this.packet = packet;
	}

	/**
	 * Constructs an entity from a tag group.
	 * 
	 * @param m      The entity manager for this entity.
	 * @param g      The data to use to construct this entity.
	 * @param packet Determines if this entity should push changes to the server.
	 *               {@code true} to enabled, {@code false otherwise}.
	 */
	public Entity(EntityManager m, TagGroup g, boolean packet) {
		this.packet = false;
		for (Tag t : g.getTags()) {
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
		}

		t = m.getEntityTexture(getString("entity_id"));

		setHitBox(new Rectangle((float) getDouble("hitbox_x"), (float) getDouble("hitbox_y"),
				(float) getDouble("hitbox_w"), (float) getDouble("hitbox_h")));

		this.packet = packet;
	}

	/**
	 * A reusable array for lighting.
	 */
	public static final float[] colors = new float[3];

	/**
	 * Gets the X position of this entity.
	 * 
	 * @return A double value.
	 */
	public double getX() {
		return getDouble("x");
	}

	/**
	 * Gets the Y position of this entity.
	 * 
	 * @return A double value.
	 */
	public double getY() {
		return getDouble("y");
	}

	/**
	 * Sets the X position of this entity.
	 * 
	 * @param x A double value.
	 */
	public void setX(double x) {
		setDouble("x", x);
	}

	/**
	 * Sets the Y position of this entity.
	 * 
	 * @param y A double value.
	 */
	public void setY(double y) {
		setDouble("y", y);
	}

	/**
	 * Gets the X velocity of this entity.
	 * 
	 * @return A double value.
	 */
	public double getDX() {
		return getDouble("dx");
	}

	/**
	 * Gets the X velocity of this entity.
	 * 
	 * @return A signed double value.
	 */
	public double getDY() {
		return getDouble("dy");
	}

	/**
	 * Sets the X velocity of this entity.
	 * 
	 * @param x A signed double value.
	 */
	public void setDX(double x) {
		setDouble("dx", x);
	}

	/**
	 * Gets the Y velocity of this entity.
	 * 
	 * @param y A signed double value.
	 */
	public void setDY(double y) {
		setDouble("dy", y);
	}

	/**
	 * Determines if the entity is flying above other entities.
	 * 
	 * @return {@code true} if the entity is flying, {@code false} otherwise.
	 */
	public boolean isFlying() {
		return getBoolean("flying");
	}

	/**
	 * Sets the flying state of an entity.
	 * 
	 * @param flying {@code true} if the entity is flying, {@code false} otherwise.
	 */
	public void setFlying(boolean flying) {
		setBoolean("flying", flying);
	}

	/**
	 * Checks if a variable with the specified name exists.
	 * @param name The name of the variable.
	 * @return {@code true} if the variable exists, {@code false} otherwise.
	 */
	public final boolean isVariable(String name) {
		return doubles.containsKey(name) || floats.containsKey(name) || ints.containsKey(name)
				|| longs.containsKey(name) || strings.containsKey(name) || bools.containsKey(name)
				|| tags.containsKey(name);
	}

	/**
	 * Sets a string value stored in this entity.
	 * 
	 * @param name  The name of the variable to set.
	 * @param value The value to set the variable to.
	 */
	public final void setString(String name, String value) {
		if (strings.get(name) != null && value != null) {
			if (strings.get(name).equals(value)) {
				return;
			}
		}
		if (value == null) {
			value = "null";
		}
		if (packet)
			((Server2D) ServerManager.getServer()).updateEntity(new UString(getID(), name, value));
		strings.put(name, value);
	}

	/**
	 * Gets a string value stored in this entity.
	 * 
	 * @param name The name of the variable to get.
	 * @return The value of the variable or null if the variable does not exist.
	 */
	public final String getString(String name) {
		String s = strings.get(name);
		return s;
	}

	/**
	 * Sets an int value stored in this entity.
	 * 
	 * @param name  The name of the variable to set.
	 * @param value The value to set the variable to.
	 */
	public final void setInt(String name, int value) {
		if (getInt(name) == value) {
			return;
		}
		if (packet)
			((Server2D) ServerManager.getServer()).updateEntity(new UInt(getID(), name, value));
		ints.put(name, value);
	}

	/**
	 * Gets an int value stored in this entity.
	 * 
	 * @param name The name of the variable to get.
	 * @return The value of the variable or 0 if the variable does not exist.
	 */
	public final int getInt(String name) {
		Integer i = ints.get(name);
		if (i == null) {
			return 0;
		} else {
			return i;
		}
	}

	/**
	 * Sets a long value stored in this entity.
	 * 
	 * @param name  The name of the variable to set.
	 * @param value The value to set the variable to.
	 */
	public void setLong(String name, long value) {
		if (getLong(name) == value) {
			return;
		}
		if (packet)
			((Server2D) ServerManager.getServer()).updateEntity(new ULong(getID(), name, value));
		longs.put(name, value);
	}

	/**
	 * Gets a long value stored in this entity.
	 * 
	 * @param name The name of the variable to get.
	 * @return The value of the variable or 0 if the variable does not exist.
	 */
	public final long getLong(String name) {
		Long i = longs.get(name);
		if (i == null) {
			return 0;
		} else {
			return i;
		}
	}

	/**
	 * Sets a float value stored in this entity.
	 * 
	 * @param name  The name of the variable to set.
	 * @param value The value to set the variable to.
	 */
	public final void setFloat(String name, float value) {
		if (getFloat(name) == value) {
			return;
		}
		if (packet)
			((Server2D) ServerManager.getServer()).updateEntity(new UFloat(getID(), name, value));
		floats.put(name, value);
	}

	/**
	 * Gets a float value stored in this entity.
	 * 
	 * @param name The name of the variable to get.
	 * @return The value of the variable or NaN if the variable does not exist.
	 */
	public final float getFloat(String name) {
		Float i = floats.get(name);
		if (i == null) {
			return 0;
		} else {
			return i;
		}
	}

	/**
	 * Sets a boolean value stored in this entity.
	 * 
	 * @param name  The name of the variable to set.
	 * @param value The value to set the variable to.
	 */
	public final void setBoolean(String name, boolean value) {
		if (getBoolean(name) == value) {
			return;
		}
		if (packet)
			((Server2D) ServerManager.getServer()).updateEntity(new UBoolean(getID(), name, value));
		bools.put(name, value);
	}

	/**
	 * Gets a boolean value stored in this entity.
	 * 
	 * @param name The name of the variable to get.
	 * @return The value of the variable or false if the variable does not exist.
	 */
	public final boolean getBoolean(String name) {
		Boolean i = bools.get(name);
		if (i == null) {
			return false;
		} else {
			return i;
		}
	}

	/**
	 * Sets a double value stored in this entity.
	 * 
	 * @param name  The name of the variable to set.
	 * @param value The value to set the variable to.
	 */
	public final void setDouble(String name, double value) {
		if (getDouble(name) == value) {
			return;
		}
		if (packet)
			((Server2D) ServerManager.getServer()).updateEntity(new UDouble(getID(), name, value));
		doubles.put(name, value);
	}

	/**
	 * Gets a double value stored in this entity.
	 * 
	 * @param name The name of the variable to get.
	 * @return The value of the variable or NaN if the variable does not exist.
	 */
	public final double getDouble(String name) {
		Double i = doubles.get(name);
		if (i == null) {
			return 0;
		} else {
			return i;
		}
	}

	/**
	 * Sets a tag value stored in this entity.
	 * 
	 * @param name  The name of the variable to set.
	 * @param value The value to set the variable to.
	 */
	public final void setTag(String name, TagGroup value) {
		if (getTag(name) == value) {
			return;
		}
		if (packet)
			((Server2D) ServerManager.getServer()).updateEntity(new UTag(getID(), name, value));
		tags.put(name, value);
	}

	/**
	 * Gets a tag value stored in this entity.
	 * 
	 * @param name The name of the variable to get.
	 * @return The value of the variable or null if the variable does not exist.
	 */
	public final TagGroup getTag(String name) {
		return tags.get(name);
	}

	/**
	 * Indicates an update has occurred in the tag and that the update should be
	 * sent to the client.
	 * 
	 * @param name The name of the variable to push.
	 */
	public final void pushtag(String name) {
		if (packet)
			((Server2D) ServerManager.getServer()).updateEntity(new UTag(getID(), name, getTag(name)));
	}

	/**
	 * Gets the ID of this entity. <strong>This should be reloaded with reloadID if
	 * the entity was loaded from disk but not if the entity was loaded from the
	 * network.</strong>
	 * 
	 * @return A valid entity ID.
	 * 
	 * @see #reloadID(EntityManager)
	 */
	public final String getID() {
		return getString("id");
	}

	/**
	 * Reloads the ID of this entity. <strong>This should be used if the entity was
	 * loaded from disk but not if the entity was loaded from the network.</strong>
	 */
	public final void reloadID() {
		strings.put("id", m.getNewID(getID()));
	}

	/**
	 * Gets this entity's texture. This method does not make a call to entity
	 * manager.
	 * 
	 * @return An EntityTexture instance or null if not texture exists.
	 */
	public EntityTexture getTexture() {
		return t;
	}

	/**
	 * <p>
	 * Converts all of this entity's data to ABT.
	 * </p>
	 * <p>
	 * Texture data, packet settings and entity managers are not stored. Tags are
	 * not separated by type and are stored in one group. Strings are stored using
	 * the shorter 16bit system. Tag groups are cloned then added to the root and
	 * objects are written as tag groups containing many byte tags. Additionally, a
	 * tag holding entity version data is included called {@code rpgonline.version}.
	 * </p>
	 * 
	 * @param name
	 * @return
	 */
	public TagGroup toABT(String name) {
		TagGroup g = new TagGroup(name);

		for (Entry<String, Double> e : doubles.entrySet()) {
			g.add(new TagDouble(e.getKey(), e.getValue()));
		}
		for (Entry<String, String> e : strings.entrySet()) {
			g.add(new TagStringShort(e.getKey(), e.getValue()));
		}
		for (Entry<String, Float> e : floats.entrySet()) {
			g.add(new TagFloat(e.getKey(), e.getValue()));
		}
		for (Entry<String, TagGroup> e : tags.entrySet()) {
			g.add(e.getValue());
		}
		for (Entry<String, Long> e : longs.entrySet()) {
			g.add(new TagLong(e.getKey(), e.getValue()));
		}
		for (Entry<String, Integer> e : ints.entrySet()) {
			g.add(new TagInt(e.getKey(), e.getValue()));
		}
		for (Entry<String, Boolean> e : bools.entrySet()) {
			g.add(new TagBoolean(e.getKey(), e.getValue()));
		}
		for (Entry<String, TagGroup> e : tags.entrySet()) {
			g.add(e.getValue().clone());
		}

		return g;
	}

	/**
	 * <p>
	 * Determines if this entity sends data to the client.
	 * </p>
	 * 
	 * @return {@code true} if update packets are sent to the client, {@code false}
	 *         otherwise.
	 */
	public boolean isPacket() {
		return packet;
	}

	/**
	 * Sets if this entity sends data to the client.
	 * 
	 * @param packet
	 */
	public void setPacket(boolean packet) {
		this.packet = packet;
	}

	/**
	 * <p>
	 * Determines if the entity should be involved in collision calculations.
	 * </p>
	 * <p>
	 * For small projectiles, it is best to disable this to remove them from entity
	 * collisions.
	 * </p>
	 * 
	 * @return {@code true} if this entity has collision, {@code false} otherwise.
	 */
	public boolean isSolid() {
		return getBoolean("solid");
	}

	/**
	 * <p>
	 * Sets if the entity should be involved in collision calculations.
	 * </p>
	 * <p>
	 * For small projectiles, it is best to disable this to remove them from entity
	 * collisions.
	 * </p>
	 * 
	 * @param solid {@code true} if this entity has collision, {@code false}
	 *              otherwise.
	 */
	public void setSolid(boolean solid) {
		setBoolean("solid", solid);
	}

	/**
	 * Gets the ID of this entity.
	 * 
	 * @return A entity ID.
	 */
	public String getEntityID() {
		return getString("entity_id");
	}

	/**
	 * Gets the AI of this entity.
	 * 
	 * @return A entity AI or null if this entity has no AI.
	 */
	public EntityAI getAI() {
		return m.getAI(getEntityID());
	}

	/**
	 * Sets the hitbox of this entity.
	 * 
	 * @param r A rectangle.
	 */
	public void setHitBox(Rectangle r) {
		setDouble("hitbox_x", r.getX());
		setDouble("hitbox_y", r.getY());
		setDouble("hitbox_w", r.getWidth());
		setDouble("hitbox_h", r.getHeight());

		this.hitbox = r;
	}

	/**
	 * Sets the angle to rotate this entity's hitbox by.
	 * 
	 * @param a A float angle in radians.
	 */
	public void setHitBoxAngle(float a) {
		setDouble("hitbox_a", a);
	}

	/**
	 * Gets the angle to rotate this entity's hitbox by.
	 * 
	 * @return A float angle in radians.
	 */
	public float getHitBoxAngle() {
		return (float) getDouble("hitbox_a");
	}

	/**
	 * Gets the transformed hitbox of this entity.
	 * 
	 * @return A shape.
	 */
	public Shape getHitBox() {
		return hitbox.transform(Transform.createRotateTransform(getHitBoxAngle()))
				.transform(Transform.createTranslateTransform((float) getX(), (float) getY()));
	}

	/**
	 * Gets the direction this entity is facing (this does not effect hitboxes).
	 * 
	 * @return A direction constant.
	 * 
	 * @see #getHitBoxAngle()
	 */
	public Direction getDirection() {
		return Direction.values()[getInt("direction")];
	}

	/**
	 * Sets the direction this entity is facing (this does not effect hitboxes).
	 * 
	 * @param d A direction constant.
	 * 
	 * @see #setHitBoxAngle(float)
	 */
	public void setDirection(Direction d) {
		setInt("direction", d.ordinal());
	}
}

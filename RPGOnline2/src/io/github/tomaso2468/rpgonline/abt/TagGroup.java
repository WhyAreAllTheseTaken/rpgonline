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
package io.github.tomaso2468.rpgonline.abt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * <p>A group of tags.</p>
 * <p>Internally, this class uses array list.</p>
 * @author Tomas
 * @see java.util.Collection
 * @see io.github.tomaso2468.rpgonline.abt.Tag
 * @see java.util.List
 * @see java.util.ArrayList
 */
public class TagGroup extends Tag implements Iterable<Tag> {
	/**
	 * ID for serilization.
	 */
	private static final long serialVersionUID = -4699095497765857990L;
	/**
	 * Internal list of tags.
	 */
	protected final List<Tag> tags = new ArrayList<Tag>();
	
	/**
	 * Constructs a new Tag Group.
	 * @param name The name of the tag.
	 */
	public TagGroup(String name) {
		super(name, 0x01);
	}
	
	/**
	 * Constructs a new Tag Group with byte data.
	 * @param name The name of the tag.
	 * @param data A byte array.
	 */
	public TagGroup(String name, byte[] data) {
		super(name, 0x01);
		for (int i = 0; i < data.length; i++) {
			add(new TagByte(i + "", data[i]));
		}
	}
	
	/**
	 * Gets all tags within this tag.
	 * @return A list of tags.
	 */
	public List<Tag> getTags() {
		return tags;
	}

	/**
	 * The size in elements of this tag.
	 * @return An int that is greater than or equal to 0.
	 */
	public int size() {
		return getTags().size();
	}

	/**
	 * Checks if this group is empty.
	 * @return {@code true} if this group is empty, {@code false} otherwise.
	 * @see java.util.Collection
	 * @see java.util.List
	 */
	public boolean isEmpty() {
		return getTags().isEmpty();
	}

	/**
	 * Checks if this group contains the specified tag.
	 * @param o The tag to check for.
	 * @return {@code true} if this group contains the tag, {@code false} otherwise.
	 * @see java.util.Collection
	 * @see java.util.List
	 */
	public boolean contains(Tag o) {
		return getTags().contains(o);
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterator<Tag> iterator() {
		return getTags().iterator();
	}

	/**
	 * Adds a tag to this group.
	 * @param e The tag to add.
	 * @return Returns {@code true}.
	 * @see java.util.Collection
	 * @see java.util.List
	 */
	public boolean add(Tag e) {
		return getTags().add(e);
	}

	/**
	 * Removes a tag from this group.
	 * @param e The tag to remove.
	 * @return Returns {@code true} if the tag as part of this group.
	 * @see java.util.Collection
	 * @see java.util.List
	 */
	public boolean remove(Object o) {
		return getTags().remove(o);
	}

	/**
     * Returns <tt>true</tt> if this group contains all of the elements of the
     * specified collection.
     *
     * @param  c collection to be checked for containment in this list
     * @return <tt>true</tt> if this group contains all of the elements of the
     *         specified collection
     * @throws ClassCastException if the types of one or more elements
     *         in the specified collection are incompatible with the {@code Tag} class.
     * @see #contains(Tag)
     */
	public boolean containsAll(Collection<?> c) {
		return getTags().containsAll(c);
	}

	/**
     * Appends all of the tags in the specified collection to the end of
     * this list. The behaviour of this
     * operation is undefined if the specified collection is modified while
     * the operation is in progress.  (Note that this will occur if the
     * specified collection is this group, and it's nonempty.)
     *
     * @param c collection containing tag to be added to this group
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *         is not supported by this group
     * @throws ClassCastException if the class of an tag of the specified
     *         collection prevents it from being added to this group
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this list does not permit null
     *         elements, or if the specified collection is null
     * @throws IllegalArgumentException if some property of an element of the
     *         specified collection prevents it from being added to this list
     * @see #add(Object)
     */
	public boolean addAll(Collection<? extends Tag> c) {
		return getTags().addAll(c);
	}

	/**
     * Removes from this list all of its elements that are contained in the
     * specified collection (optional operation).
     *
     * @param c collection containing elements to be removed from this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of an element of this list
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
	public boolean removeAll(Collection<?> c) {
		return getTags().removeAll(c);
	}
	
	 /**
     * Retains only the elements in this list that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this list all of its elements that are not contained in the
     * specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
     *         is not supported by this list
     * @throws ClassCastException if the class of an element of this list
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     *         (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
	public boolean retainAll(Collection<?> c) {
		return getTags().retainAll(c);
	}

	/**
     * Removes all of the elements from this list (optional operation).
     * The list will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *         is not supported by this list
     */
	public void clear() {
		getTags().clear();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{\n");

		appendStr(sb, "name", getName());
		appendSep(sb);
		appendNum(sb, "type", getType());
		appendSep(sb);
		
		String[] data = new String[size()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = tags.get(i).toString();
		}
		
		appendArray(sb, "data", data);
		
		sb.append("\n}");

		return sb.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Tag getTag(String name) {
		String[] name2 = name.split("/");
		if(name2[0].equals("this")) {
			StringBuilder sb = new StringBuilder();
			
			for (int i = 1; i < name2.length; i++) {
				sb.append(name2[i]);
				
				if(i != name2.length - 1) {
					sb.append("/");
				}
			}
			
			return this.getTag(sb.toString());
		}
		for(Tag t : tags) {
			if(t.getName().equals(name2[0])) {
				if (t instanceof TagGroup) {
					if (name2.length > 1) {
						StringBuilder sb = new StringBuilder();
						
						for (int i = 1; i < name2.length; i++) {
							sb.append(name2[i]);
							
							if(i != name2.length - 1) {
								sb.append("/");
							}
						}
						
						return ((TagGroup) t).getTag(sb.toString());
					} else {
						return t;
					}
				} else {
					return t;
				}
			}
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagGroup clone() {
		TagGroup tg = new TagGroup(name);
		
		for(Tag t : tags) {
			tg.add(t.clone());
		}
		
		return tg;
	}

	/**
	 * A method that (if valid) takes a tag group and generates an array from all tags with named indexes. This method requires that the tags be indexed properly.
	 * @return A byte array.
	 * @throws IOException If an error occurs converting to byte data.
	 */
	public byte[] asByteArray() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		@SuppressWarnings("resource")
		ABTDataOutputStream dos = new ABTDataOutputStream(out);
		
		for (int i = 0; i < size(); i++) {
			Tag t = getTag(i + "");
			
			if (t == null) {
				throw new IndexOutOfBoundsException(i + " is not avaliable as an indexed tag.");
			}
			
			if (t instanceof TagByte) {
				dos.write(((TagByte) t).getData());
			}
			if (t instanceof TagShort) {
				dos.writeShort(((TagShort) t).getData());
			}
			if (t instanceof TagInt) {
				dos.writeInt(((TagInt) t).getData());
			}
			if (t instanceof TagLong) {
				dos.writeLong(((TagLong) t).getData());
			}
			if (t instanceof TagFloat) {
				dos.writeFloat(((TagFloat) t).getData());
			}
			if (t instanceof TagDouble) {
				dos.writeDouble(((TagDouble) t).getData());
			}
			if (t instanceof TagBoolean) {
				dos.writeBoolean(((TagBoolean) t).getData());
			}
			if (t instanceof TagChar) {
				dos.writeChar(((TagChar) t).getData());
			}
			if (t instanceof TagString) {
				dos.writeUTFLong(((TagString) t).getData());
			}
			if (t instanceof TagGroup) {
				dos.writeLong(((TagGroup) t).size());
				dos.write(((TagGroup) t).asByteArray());
			}
		}
		
		dos.flush();
		dos.close();
		
		return out.toByteArray();
	}
	
}

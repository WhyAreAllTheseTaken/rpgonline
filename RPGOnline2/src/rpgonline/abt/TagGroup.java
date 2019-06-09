package rpgonline.abt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A group of tags.
 * @author Tomas
 *
 */
public class TagGroup extends Tag implements Iterable<Tag> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4699095497765857990L;
	/**
	 * Internal list of tags.
	 */
	protected final List<Tag> tags = new ArrayList<Tag>();
	
	/**
	 * Constructs a new Tag Group.
	 * @param name
	 */
	public TagGroup(String name) {
		super(name, 0x01);
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
	 * @return
	 */
	public boolean isEmpty() {
		return getTags().isEmpty();
	}

	public boolean contains(Object o) {
		return getTags().contains(o);
	}

	public Iterator<Tag> iterator() {
		return getTags().iterator();
	}

	public boolean add(Tag e) {
		return getTags().add(e);
	}

	public boolean remove(Object o) {
		return getTags().remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return getTags().containsAll(c);
	}

	public boolean addAll(Collection<? extends Tag> c) {
		return getTags().addAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return getTags().removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return getTags().retainAll(c);
	}

	public void clear() {
		getTags().clear();
	}
	
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
	
	@Override
	public TagGroup clone() {
		TagGroup tg = new TagGroup(name);
		
		for(Tag t : tags) {
			tg.add(t.clone());
		}
		
		return tg;
	}
	
}

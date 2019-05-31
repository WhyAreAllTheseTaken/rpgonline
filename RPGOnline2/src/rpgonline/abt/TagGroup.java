package rpgonline.abt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TagGroup extends Tag implements Iterable<Tag> {
	protected final List<Tag> tags = new ArrayList<Tag>();
	
	public TagGroup(String name) {
		super(name, 0x01);
	}
	
	public List<Tag> getTags() {
		return tags;
	}

	public int size() {
		return tags.size();
	}

	public boolean isEmpty() {
		return tags.isEmpty();
	}

	public boolean contains(Object o) {
		return tags.contains(o);
	}

	public Iterator<Tag> iterator() {
		return tags.iterator();
	}

	public boolean add(Tag e) {
		return tags.add(e);
	}

	public boolean remove(Object o) {
		return tags.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return tags.containsAll(c);
	}

	public boolean addAll(Collection<? extends Tag> c) {
		return tags.addAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return tags.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return tags.retainAll(c);
	}

	public void clear() {
		tags.clear();
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
	
}

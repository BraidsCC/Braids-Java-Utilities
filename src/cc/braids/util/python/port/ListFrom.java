package cc.braids.util.python.port;

import java.util.*;

public class ListFrom<T> extends ArrayList<T> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -9170338640347994085L;

	@SafeVarargs
    public ListFrom(T... list) {
		super(list.length);
		
		for (T item : list) {
			add(item);
		}
    }

	public ListFrom(Collection<T> c) {
		addAll(c);
    }

	public ListFrom(Iterable<T> ls) {
		super();

		for (T item : ls) {
			add(item);
		}
	}

}

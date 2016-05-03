package braids.util.python.port;

import java.util.HashSet;
import java.util.List;

public class SetFrom<T> extends HashSet<T> { 
	/**
	 * 
	 */
    private static final long serialVersionUID = -3911792518418954136L;

	public SetFrom(List<T> list) {
		super(list.size());
		
		addAll(list);
	}

    @SafeVarargs
    public SetFrom(T... list) {
		super(list.length);
		
		for (T item : list) {
			add(item);
		}
    }
}

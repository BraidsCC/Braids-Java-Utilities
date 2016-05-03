package braids.util.python.port;

import java.util.HashMap;


public class MapFrom<K,V> extends HashMap<K,V> {
    private static final long serialVersionUID = 6868832777085007816L;

    @SuppressWarnings("unchecked")
    public MapFrom(Object... objects) {
    	if (objects.length % 2 != 0) {
    		throw new RuntimeException("number of parameters must be even");
    	}
    	
    	int ix = 0;
    	while (ix < objects.length) {
    		put((K) objects[ix], (V) objects[ix + 1]);
    		ix += 2;
    	}
    }
}

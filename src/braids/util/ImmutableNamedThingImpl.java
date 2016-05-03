package braids.util;

public class ImmutableNamedThingImpl implements ImmutableNamedThing {

    public ImmutableNamedThingImpl(String name) {
        __name = name;
    }

    /* (non-Javadoc)
	 * @see braids.util.ImmutableNamedThing#getName()
	 */
    @Override
	public String getName() {
        return __name;
    }
    
    private String __name;
}

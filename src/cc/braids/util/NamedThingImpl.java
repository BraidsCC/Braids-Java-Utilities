package cc.braids.util;

public class NamedThingImpl implements NamedThing {
    public NamedThingImpl() {
        this("");
    }

    public NamedThingImpl(String name) {
        __name = name;
    }

    /* (non-Javadoc)
	 * @see cc.braids.util.NamedThing#setName(java.lang.String)
	 */
    @Override
	public void setName(String name) {
        __name = name;
    }
        
    /* (non-Javadoc)
	 * @see cc.braids.util.NamedThing#getName()
	 */
    @Override
	public String getName() {
        return __name;
    }
    
    private String __name;
}

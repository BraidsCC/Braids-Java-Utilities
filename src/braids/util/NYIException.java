package braids.util;

/**
 * This exception indicates that a portion of code has not yet been
 * implemented.  
 * 
 * This class has no association with Nazarene Youth, International.
 */
public class NYIException extends RuntimeException {
	/**
	 * Serializable says we need this.
	 */
	private static final long serialVersionUID = 7175218448501111889L;

	public NYIException() {
		super("Not yet implemented");
	}

	public NYIException(String string) {
		super("Not yet implemented: " + string);
    }
}

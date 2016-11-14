package cc.braids.util;

/**
 * A Klutz is like a Runnable, but is far more error-prone; its run method
 * is allowed to throw any exception.
 * 
 * @author Braids Constance
 */
public interface Klutz {

	public void run() throws Exception;

}

package cc.braids.util.junit;

import static cc.braids.util.UFunctions.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import cc.braids.util.UFunctions;
import junit.framework.TestCase;

public class BTestCase extends TestCase {

    /**
    * Make sure that the given thunk (function) raises an exception of
    * type exType (or a subtype thereof).
    * 
    * @param <RT> the return type of thunk
    * 
    * @param <CT> the class type of the exception
    * 
    * @param exType a type object; e.g. ClassCastException
    * 
    * @param thunk a method that takes no arguments
    * 
    * @param messagePrefix  the string to add to the beginning of the error message, if any
    */
    public <RT,CT> void assertException(Class<CT> exType,
    		Supplier<RT> thunk, 
    		String messagePrefix)
    {
    	RT actual;
    	
        try {
            actual = thunk.get();
        }
        catch (Exception exn) {

        	if (!exType.isAssignableFrom(exn.getClass())) {
        		fail(messagePrefix + 
        				"expected an exception with (super)type " + 
        				exType.getName() + ", but I got one of " +
        				exn.getClass().getName() + " instead.");
        	}
        	else {
        		/*: debug output
	        	System.out.println(message + 
	        			"successfully caught exception of type " + 
	        			exType.getName() + "(more specifically, " + etc);
	        	*/
        	}
        	
        	return;
        }
        
        fail("I expected thunk to raise an exception with (super)type " + 
        		exType.getName() + ", but it did not raise any exceptions; " +
        		"instead, it returned " + repr((RT) actual) + "."); 
    }
    

    /* commented out
     * Fails with helpful information if the two dictionaries are not
     * identical.
     * 
     * expd - the expected dictionary
     * actd - the actual dictionary
     *
     */
    /** commented out
    public <K,V> void assertDictEquals(Map<K,V> expd, Map<K,V> actd) {

        diffMessage = diffDictShallow(expd, actd);
        
        if (diffMessage) {
            fail(diffMessage);
        }
    }
	*/


    /**
     * Compares the sequences and fails with a very detailed messsage
     * if they do not pass equals.
     * 
     * @param expSeq  the expected sequence
     * 
     * @param actSeq  the actual sequence
     * 
     * @param <T> the types of the lists' elements
     */
    public <T> void assertEquals(List<T> expSeq, List<T> actSeq) {
        combineMessagesAndFailIfNonEmpty(UFunctions.diff(expSeq, actSeq));        
    }


	public void combineMessagesAndFailIfNonEmpty(List<String> failMessages) {
        if (failMessages != null && failMessages.size() > 0) {
        	String message = String.join("; ", failMessages) + ".";
            fail(message);
        }
    }


    /**
     * Same as calling failUnlessIn("", object, collection).
     * 
     * @see #failUnlessIn(String, Object, Collection)
    public <T> void failUnlessIn(T needle, Collection<T> haystack) {
    	failUnlessIn("", needle, haystack);
    }
     */
    
	/**
	 * Emit a failure unless needle is in haystack.
	 * 
	 * For classes that define their own equals methods, be sure to use this
	 * annotation on the methods' signatures:
	 * 
	 * 
	 * <pre>
	 *   <code>
	 *    {@literal @}Override
	 *     public boolean equals(Object obj)
	 *   </code>
	 * </pre>
	 * 
     * @param <T> the types of the elements in haystack
	 * @param prefix
	 *            If there is a failure, add this prefix to the failure message.
	 * @param needle
	 *            must be in haystack
	 * @param haystack
	 *            must contain needle
	 */
    public <T> void failUnlessIn(String prefix, T needle, Collection<T> haystack)
    {
        if (haystack.contains(needle)) {
            return;
        }

        /* I was desperate because I had forgotten to add at-Override 
         * annotations to my equals methods.  Ignore this...
         * 
         * commented out
    	if (in(needle, haystack)) {
    		return;
    	}
         */
        
        fail(String.format("%s%s not found in %s", prefix, repr((T) needle), 
        		repr((Collection<T>) haystack)));
    }

    
    /**
     * Emit a failure if bull is in chinaShop.
     * 
	 * For classes that define their own equals methods, be sure to use this
	 * annotation on the methods' signatures:
     * 
     * <pre>
     *   <code>
     *    {@literal @}Override
     *     public boolean equals(Object obj)
     *   </code>
     * </pre>
     * 
     * This is easy to forget.
     * 
     * @param <T> the types of the elements in chinaShop
     * 
     * @param messagePrefix prepended to the failure message, if any
     * 
     * @param bull  must not be in chinaShop
     * 
     * @param chinaShop  must not contain bull
     */
    public <T> void failIfIn(String messagePrefix, T bull, Collection<T> chinaShop) {
        if (!chinaShop.contains(bull)) {
            return;
        }
        
        fail(String.format("%s%s is in %s", messagePrefix, repr((T) bull), 
        		repr((Collection<T>) chinaShop)));
    }

    
    /*- commented out -*
    public void failUnlessSetEqualToDictContents(foo expectedSet, foo actualDict) {
        actualSet = setOfEltsInValuesOfDict(actualDict);
        failUnlessEqual(expectedSet, actualSet);
    }

    public void failUnlessSetLessOrEqualToDictContents(foo expectedSet, foo actualDict) {
        actualSet = setOfEltsInValuesOfDict(actualDict);
        missingSet = expectedSet - actualSet;

        if (missingSet) {
            fail("Could not find %s in actual set %s" %
                      (missingSet, actualSet));
        }
    }

    public static <K,V> void setOfEltsInValuesOfDict(Map<K,V> actualDict) {
        Set<V>actualSet = new HashSet<>();
        
        for (K key : actualDict.keySet()) {
            for (V hydrogen : actualDict.get(key)) {
                actualSet.add(hydrogen);
            }
        }
        
        return actualSet;
    }
    */
}

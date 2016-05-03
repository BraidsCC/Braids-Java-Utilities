package braids.util;

import static braids.util.python.port.PyFunctions.*;

import java.util.*;

import braids.util.python.port.SetFrom;

/**
 * This class defines some useful static functions that are too specialized
 * and too small to fit anywhere else.
 */
public abstract class UFunctions {
	private UFunctions() {
		throw new IllegalStateException("do not instantiate");
	}
	
	public static void echo_n(String string) {
		System.out.print(string);
		System.out.flush();
	}

	public static void echo() {
		echo("");
	}

	public static void echo(String str) {
		System.out.println(str);
	}

	
	public static boolean isEmpty(String str) {
		if (str == null || str.length() == 0)  return true;
		else  return false;
	}

	public static String repr(Object obj) {
		if (obj == null) {
			return "null";
		}
		try {
			return repr((HasReprMethod) obj);
		}
		catch (ClassCastException justMoveOn) {			
		}
		
		try {
			return repr((String) obj);
		}
		catch (ClassCastException justMoveOn) {			
		}

		return "<" + obj.getClass().getName() + "(" + repr(obj.toString())
			        + ")>";
	}
	
	public static String repr(HasReprMethod obj) {
		if (obj == null) {
			return "null";
		}
		return obj.__repr__();
	}

	public static <K,V> String repr(Map<K,V> map) {
		String result;
		
		if (map == null) {
			result = "null";
		}
		else {
			result = "{";
			
			for (K key : map.keySet()) {
				result += repr((K) key);
				result += ": ";
				result += repr((V) map.get(key));
				result += ", ";
			}
			result += "}";
		}
	
		return result;
	}
	
	public static <T> String repr(Collection<T> stamps) {		
		String result;
		
		if (stamps == null) {
			result = "null";
		}
		else {
			result = "[";
			
			for (T item : stamps) {
				result += repr((T) item);
				result += ", ";
			}
			result += "]";
		}
		
		return result;
	}
	
	public static String repr(String str) {
		
		if (str == null) {
			return "null";
		}
		

		// Buffer size allows for all chars to be non-Latin-1 (represented 
		// by \u1234 sequences) plus an allotment for quotes.
		StringBuffer buf = new StringBuffer(str.length()*6 + 2);
		
		buf.append("\"");
		
		for (int ix = 0; ix < str.length(); ix++) {
			char ch = str.charAt(ix);
			
			if (ch == '"') {
				buf.append("\\\"");
			}
			else if (ch == '\n') {
				buf.append("\\n");
			}
			else if (ch == '\t') {
				buf.append("\\t");
			}
			else if (ch == '\\') {
				buf.append("\\\\");
			}
			else if ((32 <= ch && ch <= 126) ||
				(160 <= ch && ch <= 255)) 
			{
				// It's an ordinary printable Latin character.
				buf.append(ch);
			}
			else if (ch <= 0xf) {
				buf.append("\\u000");
				buf.append(Integer.toHexString(ch));
			}
			else if (ch <= 0xff) {
				buf.append("\\u00");
				buf.append(Integer.toHexString(ch));
			}
			else if (ch <= 0xfff) {
				buf.append("\\u0");
				buf.append(Integer.toHexString(ch));
			}
			else {
				buf.append("\\u");
				buf.append(Integer.toHexString(ch));
			}
		}
		
		buf.append("\"");
		return buf.toString();
	}

	/**
	 * Provides helpful information if two maps (dictionaries) are not
	 * identical.
	 * 
	 * @param expd
	 *            the expected or "better" dictionary
	 * @param actd
	 *            the actual or "inferior" dictionary
	 *
	 * @param nameOfSecondDictionary
	 *            how to refer to the second dictionary object in the result.
	 *            This should be capitalized and singular. Example: "Latter"
	 *
	 * @return a string describing how the second argument differs from the
	 *         first, or null if they are the same. It may have a trailing space
	 *         character.
	 */
	public static <K,V> String diffDictShallow(Map<K,V> expd, Map<K,V> actd, 
			String nameOfSecondDictionary) 
	{
		// TODO run and/or write unit tests for this; I changed it 2015-08-20.

        StringBuffer diffMessages = new StringBuffer();

        // Start with the keys.
        //

        String nameOfSecondSet = nameOfSecondDictionary + " map's key-set";
        Set<K> exdKeySet = expd.keySet();
        Set<K> acdKeySet = actd.keySet();

        String setMessages = String.join("  ", diff(exdKeySet, acdKeySet, nameOfSecondSet));

        if (!"".equals(setMessages)) {
        	diffMessages.append(setMessages);
        }

        // Now check the values for the keys both have in common.
        //
        
        Set<K> sameKeys = intersection(exdKeySet, acdKeySet);
        for (K key : sameKeys) {
        	
            V expVal = expd.get(key);
			V actVal = actd.get(key);
			if ((expVal == null && actVal != null) ||
			    (expVal != null && actVal == null) ||
			    (!expVal.equals(actVal))) 
			{
				diffMessages.append("Under key " + repr((K) key) + ", "
				        + repr((V) expVal) + " does not equal "
				        + repr((V) actVal) + ". ");
            }
        }

        String result = diffMessages.toString();
        
        if (result == "") {
            result = null;
        }
        
        return result;
	}

	public static <K> List<String> diff(Set<K> firstSet, Set<K> secondSet,
            String nameOfSecondSet)
    {
	    List<String> result = new ArrayList<>();

	    Set<K> absentActualElts = subtract(firstSet, secondSet);
        Set<K> extraActualElts = subtract(secondSet, firstSet);
        
        for (K key : absentActualElts) {
            result.add(nameOfSecondSet + 
             " lacks " + repr((K) key) + ".");
        }
        
        for (K key : extraActualElts) {
        	result.add(nameOfSecondSet +
             " has extra key " + repr((K) key) + ".");
        }
	    return result;
    }

	/**
	 * Two-parameter convenience method.
	 * 
	 * @see #diff(List, List, String)
	 */
	public static <T> List<String> diff(List<T> firstList, List<T> secondList) {
		return diff(firstList, secondList, "Second list");
	}

    /**
	 * Provides helpful information if the two lists are not identical.
	 * 
	 * @param firstList
	 *            is usually consider the "better" or "expected" one
	 * 
	 * @param secondList
	 *            is usually consider the "inferior" or "actual" one; most
	 *            messages reference it
	 * 
	 * @param secondListName
	 *            the name given to the second list in the messages
	 * 
	 * @return a list of strings indicating the differences, guaranteed to never
	 *         be null.  If this list's size is zero, then the two lists are equal.
	 */
	public static <T> List<String> diff(List<T> firstList, List<T> secondList, String secondListName) {
	    ArrayList<String> result = new ArrayList<>();
	
	    // If the lists are already lists or tuples, we want to keep them
	    // that way for efficiency.  We're not sorting them, so there's no
	    // need to make an initial copy.
	
	    List<T> firstIndexable = firstList;
	    List<T> secondIndexable = secondList;
	
	    // We try to provide the most helpful differences first.  As the tests
	    // progress, they get more esoteric.
	
	
		// Start by looking for absentees. (We do need to copy their elements
		// into these sets.)
	    //
	    Set<T> firstSet = new SetFrom<>(firstIndexable);
	    Set<T> secondSet = new SetFrom<>(secondIndexable);
	    
	    Set<T> absentSecondItems = subtract(firstSet, secondSet);
	    Set<T> extraSecondItems = subtract(secondSet, firstSet);
	    
		for (T item : absentSecondItems) {
	        result.add(secondListName + " lacks item " + repr((T) item));
	    }
	    
	    for (T item : extraSecondItems) {
	        result.add(secondListName  + " has extra item " + repr((T) item));
	    }
	
	    // Now check for equality of items at specific indices. 
	    for (int ix = 0; ix < firstIndexable.size(); ix++) {
	        if (secondIndexable.size() <= ix) {
	            result.add(secondListName + " is shorter");
	            break;
	        }
	        
	        if (firstIndexable.get(ix) != secondIndexable.get(ix)) {
	            result.add(String.format("At index %d, %s != %s",
	                                ix, repr(firstIndexable.get(ix)), 
	                                 repr(secondIndexable.get(ix))));
	            
	        }
	    }
	
	
	    // This last one, testing for extra items at the end of the second
	    // list, is weird.  But if none of the other conditions fail, it is
	    // very helpful!  If the others succeed, well, at least it's the last
	    // message.
	
	    if (secondIndexable.size() > firstIndexable.size()) {
	    	StringBuffer message = new StringBuffer();
	        message.append(secondListName + " is longer:  extra items = {");
	
	        for (int ix = firstIndexable.size(); ix < secondIndexable.size(); ix++) {
	        	message.append(repr((T) secondIndexable.get(ix)));
	        	message.append(", ");
	        }
	        message.append("}");
	        
	        result.add(message.toString());
	    }
	    return result;
	}
	
	public static <T> List<T> shuffledListFrom(Collection<T> stamps) {
		List<T> result = new ArrayList<>(stamps.size());
		result.addAll(stamps);
		Collections.shuffle(result);
		return result;
	}
}

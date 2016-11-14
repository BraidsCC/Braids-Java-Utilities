package cc.braids.util.python.port;

import static cc.braids.util.UFunctions.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

public abstract class PyFunctions {

	/**
	 * Use Java reflection to get a method with the given name.  If more than
	 * one method has that name, then one is chosen arbitrarily
	 * 
	 * @param obj  the object to search
	 * @param name  the name of the method or member to find; must not be null
	 * @return  the method
	 * 
	 * @throws RuntimeException if the given member could not be found
	 */
	public static Method getattrMethod(Object obj, String name) {
		if (obj == null) {
			throw new NullPointerException("obj must not be null");
		}
		else if (name == null) {
			throw new NullPointerException("name must not be null");
		}

		Class<? extends Object> c = obj.getClass();
		Method[] methods = c.getMethods();
		
		// Find the first method with the given name, Python style.
		for (Method meth : methods) {
			if (meth.getName().equals(name)) {
				return meth;
			}
		}

		throw new AttributeError(repr(c.getName()) + 
				" object has no attribute " + repr(name));
	}

	
	public static <T> int len(Collection<T> c) {
		return c.size();
	}


	/**
	 * Replacement for Python's in operator. 
	 * 
	 * For classes that define their own equals methods, be sure to use this
	 * annotation on the methods' signatures:
	 * 
	 * @Override public boolean equals(Object obj)
	 * 
	 * This is easy to forget.
	 * 
	 * @param needle
	 *            the object to find
	 * @param haystack
	 *            the collection in which to find it
	 * 
	 * @return true if present
	 */
	public static <T> boolean in(T needle, Collection<T> haystack) {
		return haystack.contains(needle);
	}

	
	/**
	 * Compute the intersection of two Set instances.
	 * 
	 * @param ocean  a set
	 * @param sand  a set
	 * 
	 * @return a new Set<T> which contains shallowly-copied items from ocean 
	 * and/or sand; this may be empty, but shall never be null
	 */
	public static <T> Set<T> intersection(Set<T> ocean, Set<T> sand) {
		Set<T> result = new HashSet<>();
		
		Set<T> bigger;
		Set<T> smaller;
		if (sand.size() < ocean.size()) {
			smaller = sand;
			bigger = ocean;
		}
		else {
			smaller = ocean;
			bigger = sand;
		}
		
		// Iterate over the smaller set to reduce the number of searches.
		for (T item : smaller) {
			if (bigger.contains(item)) {
				result.add(item);
			}
		}
		
		return result;
	}

	/**
	 * Create a new set which represents the items that are present in ALL the 
	 * given sets.
	 * 
	 * @param sets  an array of sets (varargs style)
	 * @return  a new set that has only the items present in every single element of sets
	 */
	@SafeVarargs
    public static <T> Set<T> intersection(Set<T>... sets) {
	
		// If there are no sets, then the intersection is the empty set.
		if (sets.length == 0) {
			return new HashSet<>(0); 
		}
		
		// Find the smallest set and start with that.  
		// This minimizes the number of set-searches.
		//
		Integer minSize = null;
		int smallestIx = 0;
	
		// Start using the first set's size.
		minSize = sets[0].size();
		smallestIx = 0;
	
		// Compare the first set's size to all the other sets.
		for (int ix = 1; ix < sets.length; ix++) {
			int size = sets[ix].size();
			
			if (size < minSize) {
				minSize = size;
				smallestIx = ix;
			}
		}
	
		// minSize is the length of the smallest set.
		// smallestIx is the index of the set that is (one of) the smallest.  
		
		// Start with a copy of the smallest set.
		Set<T> result = new HashSet<>(minSize);
		result.addAll(sets[smallestIx]);
		
		// Scan all other sets and remove items from the result that are 
		// absent.
		//
		for (int ix = 1; ix < sets.length; ix++) {
			
			// Don't scan items in the smallest set; we already added its
			// items earlier.
			//
			if (smallestIx == ix) {
				continue;
			}
	
			for (T item : result) {
				if (!sets[ix].contains(item)) {
					result.remove(item);
				}
			}
		}
		
		return result;
	}


	/**
	 * Create a new list by applying the function to each item of the input list.
	 * 
	 * @param f  the function to apply
	 * @param ls  the input list
	 * @return  a "list" in Iterable form
	 */
	public static <T,R extends Object> Iterable<R> map(Function<T,R> f, Iterable<T> ls) {
		// Allocate array list of exact size
		List<R> result = new ArrayList<>();
		
		// Apply the function to each item in ls and store in result
		for (T item : ls) {
			result.add(f.apply(item));
		}
		
		return result;
	}


	/**
	 * Create a new list by applying the function to each item of the input 
	 * list and then casting the result to a (possibly) different type.
	 * 
	 * @param f  the function to apply
	 * @param ls  the input list
	 * @param cast  the class to which we cast each item in the result
	 * 
	 * @return  a "list" in Iterable form
	 */
	@SuppressWarnings("unchecked")
    public static <T,R,C> Iterable<C> map(Function<T,R> f, Iterable<T> ls, Class<C> cast) {
		// Allocate array list of exact size
		List<C> result = new ArrayList<>();
		
		// Apply the function to each item in ls and store in result
		for (T item : ls) {
			result.add((C) f.apply(item));
		}
		
		return result;
	}
	
	public static <T extends Comparable<T>> T max(Collection<T> c) {
		T result = null;
		
		for (T item : c) {
			if (result == null) {
				result = item;
			}
			else {
				if (result.compareTo(item) < 0) {
					result = item;
				}
			}
		}
		
		return result;
	}
	
	@SafeVarargs
    public static <T> Set<T> set(T... things) {
		Set<T> result = new HashSet<>(things.length);

		for (T obj : things) {
			result.add(obj);
		}
		
		return result;
	}

	public static <T> Set<T> set(Iterable<T> things) {
		Set<T> result = new HashSet<>();

		for (T obj : things) {
			result.add(obj);
		}
		
		return result;
	}


	public static String str(Object obj) {
		return obj.toString();
	}

	/**
	 * Subtract one Set from another.
	 * 
	 * @param minuend the (usually larger) set to subtract from
	 * @param subtrahend  the set of items to omit from the result
	 * 
	 * @return a shallow copy of minuend that lacks all of the items in the 
	 * subtrahend
	 */
	public static <T> Set<T> subtract(Set<T> minuend, Set<T> subtrahend)
	{
		HashSet<T> result = new HashSet<>(minuend.size());
		
		for (T item : minuend) {
			if (!subtrahend.contains(item)) {
				result.add(item);
			}
		}
		
		return result;
	}

	@SafeVarargs
    public static <T> Set<T> union(Set<T>... sets) {
		
		int maxSize = 0;
		for (Set<T> eachSet : sets) {
			maxSize += eachSet.size();
		}
		
		Set<T> result = new HashSet<>(maxSize);
		
		for (Set<T> eachSet : sets) {
			result.addAll(eachSet);
		}
		
		return result;
	}
	
}

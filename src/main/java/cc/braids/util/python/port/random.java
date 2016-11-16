package cc.braids.util.python.port;

import java.util.List;
import java.util.Random;


public abstract class random {
	
	private static Random rand = new Random();
	
	
	/**
	 * Choose a pseudo-random item from a list.
	 * 
	 * @param <T> the types of ls' elements
	 * 
	 * @param ls  the list from which to choose
	 * 
	 * @return  one of the elements of ls at random
	 */
	public static <T> T choice(List<T> ls) {
		int ix = randint(0, ls.size() - 1);
		
		return ls.get(ix);
	}

	
	/**
	 * Generates a pseudorandom number from lowestPossible to highestPossible.
	 * @param lowestPossible  the lowest number that we could return
	 * @param highestPossible  the highest number that we could return
	 * 
	 * @return  a pseudorandom number that is greater than or equal to 
	 *  lowestPossible, and less than or equal to highestPossible
	 */
	public static int randint(int lowestPossible, int highestPossible) {
		int spread = highestPossible - lowestPossible;
		
		int result = rand.nextInt(spread + 1) + lowestPossible;
		
		assert(result >= lowestPossible);
		assert(result <= highestPossible);
		
		return result;
	}
}

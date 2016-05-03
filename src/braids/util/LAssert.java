package braids.util;

import static org.junit.Assert.*;

/**
 * Defines some useful assertions for JUnit tests.
 */
public class LAssert {

	public static void throwsException(
			Class<? extends Throwable> class1,
			Klutz klutz)
	{
			throwsException(null, class1, klutz);
	}

	
	public static void throwsException(
			String messagePrefix,
			Class<? extends Throwable> class1,
			Klutz klutz) 
	{
		try {
			klutz.run();
		}
		catch (Throwable exn) {
			if (!class1.isAssignableFrom(exn.getClass())) {
				String message = getPrefixedMessage(messagePrefix, 
				"expected type " + 
				class1.getName() + 
				", but got " +
				exn.getClass().getName());
		
				fail(message);
			}
			
			return;
		}
		
		String message = getPrefixedMessage(messagePrefix, 
			"did not throw an exception");
		
		fail(message);
	}


	protected static String getPrefixedMessage(String messagePrefix,
			String rawMessage) 
	{
		String message;
		
		if (messagePrefix != null && messagePrefix != "") {
			message = messagePrefix + ": " + rawMessage;
		}
		else {
			message = rawMessage;
		}
		
		return message;
	}

}

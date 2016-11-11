package cc.braids.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class aids with simple validations of IP (v4) addresses.
 */
public class IPv4 {
	public final static Pattern addressPattern = Pattern.compile(
	"^([0-9]+)\\.([0-9]+)\\.([0-9]+)\\.([0-9]+)$");


	public static boolean isValidAddress(String addr) {
		Matcher matcher = addressPattern.matcher(addr);
		
		if (!matcher.matches())  return false;
		
		for (int groupIx = 1; groupIx <= 4; groupIx++) {
			int octet;
			
			try {
				String octetString = matcher.group(groupIx);
				octet = Integer.parseInt(octetString);
			} catch (NumberFormatException exn) {
				octet = -1;
			}
			
			if (octet < 0 || octet > 255) {
				return false;
			}
		}
	
		return true;
	}


}

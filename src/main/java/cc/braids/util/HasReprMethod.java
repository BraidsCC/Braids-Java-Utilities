package cc.braids.util;


/**
 * This is a simple mix-in interface that marks a class as having a
 * __repr__ method, a la Python programming language.
 * 
 * __repr__ shall return a String that represents an unambiguous means  
 * of identifying this object, e.g., for a debugger.  It may return
 * a complete constructor call.  For example:
 * 
 * <pre>
 *   <code>
 *    {@literal @}Override
 *     public String __repr__() {
 *         return "ClassName(" + repr(field1) + "," + repr(field2) + ")";
 *     } 
 *   </code>
 * </pre>
 * 
 * Omit the new operator.
 */
public interface HasReprMethod {
	public String __repr__();
}

package cc.braids.util.python.port;

import static cc.braids.util.UFunctions.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cc.braids.util.HasReprMethod;
import cc.braids.util.NYIException;
import cc.braids.util.junit.BTestCase;

/**
 * JUnit test for Functions.repr.
 */
@SuppressWarnings("unused")
public class ReprTest extends BTestCase implements HasReprMethod {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRepr_String() {
		String input = 
			"backslash: \\ "+
			"tab: \t "+
			"newline: \n "+
			"quote: \" "+
			"ctrl+a: \u0001 "+
			"DEL: \u007f "+
			"Unicode 12-bit: \u0cab " +
			"Unicode 16-bit: \ubeef";

		String expected = 
			"\"backslash: \\\\ "+
			"tab: \\t "+
			"newline: \\n "+
			"quote: \\\" "+
			"ctrl+a: \\u0001 "+
			"DEL: \\u007f "+
			"Unicode 12-bit: \\u0cab " +
			"Unicode 16-bit: \\ubeef\"";
		
		assertEquals(expected, repr(input));
	}
	
	@Test
	public void testRepr_listOf_String_Integer_StringBuffer() {
		StringBuffer buf = new StringBuffer();
		buf.append("buffer");
		
		List<Object> input = Arrays.asList("foo", new Integer(42), buf);
		
		String expected = "[\"foo\", <java.lang.Integer(\"42\")>, <java.lang.StringBuffer(\"buffer\")>, ]";
		String actual = repr(input);
		
		assertEquals(expected, actual);
	}

	@Test
	public void testRepr_listOf_String_Integer_StringBuffer_HasReprMethod() {
		StringBuffer buf = new StringBuffer();
		buf.append("buffer");
		
		List<Object> input = Arrays.asList("foo", new Integer(42), buf, this);
		
		String expected = "[\"foo\", <java.lang.Integer(\"42\")>, <java.lang.StringBuffer(\"buffer\")>, (a ReprTest instance), ]";
		String actual = repr(input);
		
		assertEquals(expected, actual);
	}
	@Override
    public String __repr__() {
		return "(a ReprTest instance)";
    }
}

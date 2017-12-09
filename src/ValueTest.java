//Tashfiq Akhand 101004428


import junit.framework.TestCase;
import junit.framework.*;

import static org.junit.Assert.*;

import java.util.ArrayList;


import org.junit.Test;
import org.junit.*;

public class ValueTest extends TestCase {
	private Value v;
	private double d;	
	
	//@Before
	public void setUp() throws Exception{
		v = new Value(2);
	}
	
	//@After
	public void tearDown() throws Exception{
		v = null;
	}
	
	public void TestGettersAndSettersValue() {
		v.setValue("3");
		Object a = 3;
		Object b = v.getValue();
		
		assertEquals(a,b);
	}
	//@Test
	public void TestGettersAndSettersType() {
		v.setType("String");
		String a = "String";
		String b = v.getType();
		
		assertEquals(a,b);
	}
	
	public void TestAddNumber() {
		double a = 20;
		v.add(a);
		double b = 22;
		assertEquals(v,b);
	}
	
	public void TestAddString() {
		String a = "Project";
		v = new Value("Group ");
		v.add(a);
		String b = "Group Project";
		assertEquals(v, b);
	}
	
	public void TestDifferenceNumber() {
		Value v2 = new Value(5);
		double a = v.getDifference(v2);
		assertTrue(a==3);
	}
	public void TestDifferenceBool() {
		Value v2 = new Value(true);
		v = new Value(false);
		double a = v.getDifference(v2);
		assertTrue(a==1);
	}
	public void TestDifferenceString() {
		Value v2 = new Value("hi");
		v = new Value("hi");
		double a = v.getDifference(v2);
		assertTrue(a==0);
	}
	
	public void TestMultiplyNumber() {
		double a = 7;
		v.multiply(a);
		double b = 14;
		assertEquals(v, 14);
	}
	
	public static void main(String args[]) {
		junit.textui.TestRunner.run(ValueTest.class);
	}

}

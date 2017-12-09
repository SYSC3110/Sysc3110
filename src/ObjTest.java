//Tashfiq Akhand 101004428


import junit.framework.TestCase;
import junit.framework.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

public class ObjTest extends TestCase{
	private Obj o;
	private Value v;
	
	private ArrayList<Value> values;
    
	
	@Before
	public void setUp() throws Exception{
		o = new Obj(5, values);
	}
	
	@After
	public void tearDown() throws Exception{		
		o = null;;
	}
	
	@Test 
	public void TestAddValueAndLastValue() {
		v = new Value("mileage", "number", "20000");
		o.addValue(v);
		values.add(o.getLastValue());
		Value v2 = new Value("mileage","number", "20000");
		assertEquals(values.get(values.size()-1), v2);
	}
	
	@Test 
	public void testNumber() {
		int a = o.getNumber();
		Obj o2 = new Obj(1, values);
		o2.setNumber(5);
		int b = o2.getNumber();
		assertEquals(a,b);
	}
	
	
	public static void main(String args[]) {
		junit.textui.TestRunner.run(ObjTest.class);
	}
	
	
}
